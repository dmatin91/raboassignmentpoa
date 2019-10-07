package nl.dmatin.raboassignment.service;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import lombok.extern.slf4j.Slf4j;
import nl.dmatin.raboassignment.client.PowerOfAttorneyClient;
import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyIdDTO;
import nl.dmatin.raboassignment.exception.CustomException;
import nl.dmatin.raboassignment.model.Card;
import nl.dmatin.raboassignment.model.CardType;
import nl.dmatin.raboassignment.model.dto.AccountDTO;
import nl.dmatin.raboassignment.model.dto.DebitCardDTO;
import nl.dmatin.raboassignment.model.dto.PowerOfAttorneyDTO;
import nl.dmatin.raboassignment.model.user.User;
import nl.dmatin.raboassignment.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PowerOfAttorneyClient powerOfAttorneyClient;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private CardService cardService;

	@Autowired
	private AccountService accountService;

	@Override
	public List<PowerOfAttorneyDTO> getUserOverview(String username) {
		User loggedUser = userRepository.findByUsername(username);
		List<ClientApiPowerOfAttorneyDTO> userPowerOfAttorneysFromClient = new LinkedList<>();
		List<PowerOfAttorneyDTO> userPowerOfAttorneys = new LinkedList<>();
		if (loggedUser.getPowerOfAttorneys().size() > 0) {
			loggedUser.getPowerOfAttorneys().forEach(poaId -> {
				ClientApiPowerOfAttorneyDTO clientApiPowerOfAttorneyDTO = powerOfAttorneyClient.getPowerOfAttorney(poaId);
				clientApiPowerOfAttorneyDTO.setId(poaId);
				userPowerOfAttorneysFromClient.add(clientApiPowerOfAttorneyDTO);
			});
		}
		if (userPowerOfAttorneysFromClient.size() > 0) {
			userPowerOfAttorneysFromClient.forEach(clientPoa -> userPowerOfAttorneys.add(populateRemainingClientData(clientPoa)));
		}

		return userPowerOfAttorneys;

	}

	@Override
	public PowerOfAttorneyDTO getSinglePowerOfAttorneyOverview(String poaId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User loggedUser = userRepository.findByUsername(currentPrincipalName);
		if(loggedUser.getPowerOfAttorneys().contains(poaId)){
			ClientApiPowerOfAttorneyDTO clientApiPowerOfAttorneyDTO = powerOfAttorneyClient.getPowerOfAttorney(poaId);
			clientApiPowerOfAttorneyDTO.setId(poaId);
			return populateRemainingClientData(clientApiPowerOfAttorneyDTO);
		}else{
			throw new CustomException("Not authorized for the resource", HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public User findMyPowerOfAttorneys() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User loggedUser = userRepository.findByUsername(currentPrincipalName);
		List<String> myPowerOfAttorneys = new LinkedList<>();
		List<ClientApiPowerOfAttorneyIdDTO> clientApiPowerOfAttorneyIdDTO = powerOfAttorneyClient.getAllPowerOfAttorneyIds();
		List<CompletableFuture<ClientApiPowerOfAttorneyDTO>> allPowerOfAttorneys = new LinkedList<>();
		clientApiPowerOfAttorneyIdDTO.forEach(clientApiPowerOfAttorneyIdDTO1 -> allPowerOfAttorneys
			.add(supplyAsync(() -> powerOfAttorneyClient.getPowerOfAttorney(clientApiPowerOfAttorneyIdDTO1.getId()))));
		allOf(allPowerOfAttorneys.toArray(new CompletableFuture[allPowerOfAttorneys.size()])).join();
		allPowerOfAttorneys.forEach(completableFuture -> {
			try {
				if (StringUtils.equalsIgnoreCase(completableFuture.get().getGrantor(), currentPrincipalName) || StringUtils
					.equalsIgnoreCase(completableFuture.get().getGrantee(), currentPrincipalName)) {
					myPowerOfAttorneys.add(clientApiPowerOfAttorneyIdDTO.get(Integer.parseInt(completableFuture.get().getId())).getId());
				}
			}
			catch (InterruptedException | ExecutionException e) {
				log.error(e.getMessage());
				throw new CustomException("POA REST API is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);
			}
		});
		if (myPowerOfAttorneys.size() > 0) {
			loggedUser.setPowerOfAttorneys(myPowerOfAttorneys);
		}

		userRepository.save(loggedUser);
		return loggedUser;

	}

	private PowerOfAttorneyDTO populateRemainingClientData(ClientApiPowerOfAttorneyDTO clientPoa) {
		List<CompletableFuture> accountAndCardFutures = new LinkedList<>();
		accountAndCardFutures
			.add(supplyAsync(() -> powerOfAttorneyClient.getAccountDetails(accountService.getAccountNumberFromSimpleAccount(clientPoa.getAccount()))));
		if (clientPoa.getCards() != null) {
			clientPoa.getCards().forEach(c -> accountAndCardFutures.add(CardType.CREDIT_CARD.equals(c.getType()) ?
				supplyAsync(() -> powerOfAttorneyClient.getCreditCard(c.getId())) :
				supplyAsync(() -> powerOfAttorneyClient.getDebitCard(c.getId()))));
		}
		allOf(accountAndCardFutures.toArray(new CompletableFuture[accountAndCardFutures.size()])).join();

		PowerOfAttorneyDTO powerOfAttorneyDTO = getPowerOfAttorneyDTOFromCallback(clientPoa, accountAndCardFutures);
		return powerOfAttorneyDTO;
	}

	private PowerOfAttorneyDTO getPowerOfAttorneyDTOFromCallback(ClientApiPowerOfAttorneyDTO clientPoa, List<CompletableFuture> accountAndCardFutures) {
		PowerOfAttorneyDTO clientPowerOfAttorney = conversionService.convert(clientPoa, PowerOfAttorneyDTO.class);
		List<Card> userCards = new LinkedList<>();
		accountAndCardFutures.forEach(completableFuture -> {
			try {
				if (completableFuture.get() instanceof ClientApiAccountDTO) {
					addClientAccountToPoaResponse(clientPoa, clientPowerOfAttorney, completableFuture);
				} else if (completableFuture.get() instanceof ClientApiDebitCardDTO) {
					addClientDebitCardToPoaResponse(clientPoa, userCards, completableFuture);
				} else if (completableFuture.get() instanceof ClientApiCreditCardDTO) {
					addClientCreditCardToPoaResponse(clientPoa, userCards, completableFuture);
				}
			}
			catch (InterruptedException | ExecutionException e) {
				log.error(e.getMessage());
				throw new CustomException("POA REST API is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);
			}
		});
		if (userCards.size() > 0) {
			clientPowerOfAttorney.setCards(userCards);
		}

		return clientPowerOfAttorney;
	}

	private void addClientCreditCardToPoaResponse(ClientApiPowerOfAttorneyDTO clientPoa, List<Card> userCards, CompletableFuture completableFuture)
		throws InterruptedException, ExecutionException {
		ClientApiCreditCardDTO clientApiCreditCardDTO = (ClientApiCreditCardDTO) completableFuture.get();
		if (cardService.cardAuthorizationCheck(clientApiCreditCardDTO, clientPoa.getAuthorizations()) && cardService
			.cardValidityCheck(clientApiCreditCardDTO, clientPoa.getGrantor())) {
			userCards.add(conversionService.convert(clientApiCreditCardDTO, DebitCardDTO.class));
		}
	}

	private void addClientDebitCardToPoaResponse(ClientApiPowerOfAttorneyDTO clientPoa, List<Card> userCards, CompletableFuture completableFuture)
		throws InterruptedException, ExecutionException {
		ClientApiDebitCardDTO clientApiDebitCardDTO = (ClientApiDebitCardDTO) completableFuture.get();
		if (cardService.cardAuthorizationCheck(clientApiDebitCardDTO, clientPoa.getAuthorizations()) && cardService
			.cardValidityCheck(clientApiDebitCardDTO, clientPoa.getGrantor())) {
			userCards.add(conversionService.convert(clientApiDebitCardDTO, DebitCardDTO.class));
		}
	}

	private void addClientAccountToPoaResponse(ClientApiPowerOfAttorneyDTO clientPoa, PowerOfAttorneyDTO clientPowerOfAttorney,
		CompletableFuture completableFuture) throws InterruptedException, ExecutionException {
		ClientApiAccountDTO clientApiAccountDTO = (ClientApiAccountDTO) completableFuture.get();
		if (accountService.userAccountValidityCheck(clientApiAccountDTO, clientPoa)) {
			clientPowerOfAttorney.setAccount(conversionService.convert(clientApiAccountDTO, AccountDTO.class));
			clientPowerOfAttorney.getAccount().setNumber(clientPoa.getAccount());
		}
	}
}
