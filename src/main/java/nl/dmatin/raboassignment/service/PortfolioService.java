package nl.dmatin.raboassignment.service;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import lombok.extern.slf4j.Slf4j;
import nl.dmatin.raboassignment.client.PowerOfAttorneyClient;
import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.exception.CustomException;
import nl.dmatin.raboassignment.model.Card;
import nl.dmatin.raboassignment.model.dto.AccountDTO;
import nl.dmatin.raboassignment.model.dto.CreditCardDTO;
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

@Service
@Slf4j
public class PortfolioService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PowerOfAttorneyClient powerOfAttorneyClient;

	@Autowired
	private ConversionService conversionService;

	public List<PowerOfAttorneyDTO> getUserOverview(String username) {
		User loggedUser = userRepository.findByUsername(username);
		List<ClientApiPowerOfAttorneyDTO> userPowerOfAttorneysFromClient = new LinkedList<>();
		List<PowerOfAttorneyDTO> userPowerOfAttorneys = new LinkedList<>();
		if (loggedUser.getPowerOfAttorneys().size() > 0) {
			for (String poaId : loggedUser.getPowerOfAttorneys()) {
				userPowerOfAttorneysFromClient.add(powerOfAttorneyClient.getPowerOfAttorney(poaId));
			}
		}
		if (userPowerOfAttorneysFromClient.size() > 0) {
			for (ClientApiPowerOfAttorneyDTO clientPoa : userPowerOfAttorneysFromClient) {
				userPowerOfAttorneys.add(populateRemainingClientData(clientPoa));
			}
		}

		return userPowerOfAttorneys;

	}

	public void findMyPowerOfAttorneys() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
	}

	private PowerOfAttorneyDTO populateRemainingClientData(ClientApiPowerOfAttorneyDTO clientPoa) {
		CompletableFuture<ClientApiAccountDTO> accountFuture = supplyAsync(() -> powerOfAttorneyClient.getAccountDetails("123456789"));//clientPoa.getAccount()
		CompletableFuture<ClientApiDebitCardDTO> debitCardFuture = supplyAsync(() -> powerOfAttorneyClient.getDebitCard("1111"));
		CompletableFuture<ClientApiCreditCardDTO> creditCardFuture = supplyAsync(() -> powerOfAttorneyClient.getCreditCard("3333"));
		allOf(accountFuture, debitCardFuture, creditCardFuture).join();
		try {
			PowerOfAttorneyDTO clientPowerOfAttorney = conversionService.convert(clientPoa, PowerOfAttorneyDTO.class);
			clientPowerOfAttorney.setAccount(conversionService.convert(accountFuture.get(), AccountDTO.class));
			clientPowerOfAttorney.getAccount().setNumber(clientPoa.getAccount());
			List<Card> userCards = new LinkedList<>();
			userCards.add(conversionService.convert(debitCardFuture.get(), DebitCardDTO.class));
			userCards.add(conversionService.convert(creditCardFuture.get(), CreditCardDTO.class));
			clientPowerOfAttorney.setCards(userCards);
			return clientPowerOfAttorney;
		}
		catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
			throw new CustomException("POA REST API is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
}
