package nl.dmatin.raboassignment.client;

import lombok.extern.slf4j.Slf4j;
import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyIdDTO;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
@Slf4j
public class PowerOfAttorneyClientImpl implements PowerOfAttorneyClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PowerOfAttorneyApiConfig apiConfig;

	@Override
	@HystrixCommand(fallbackMethod = "getPowerOfAttorneyIdsFallback", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
	})
	public List<ClientApiPowerOfAttorneyIdDTO> getAllPowerOfAttorneyIds() {
		String url = UriComponentsBuilder.fromUriString(apiConfig.getUri())//
			.path(apiConfig.getPoaService())//
			.toUriString();
		ResponseEntity<ClientApiPowerOfAttorneyIdDTO[]> responseEntity =  restTemplate.getForEntity(url, ClientApiPowerOfAttorneyIdDTO[].class);
		ClientApiPowerOfAttorneyIdDTO[] objects = responseEntity.getBody();
		return Arrays.asList(objects);
	}

	@Override
	@HystrixCommand(fallbackMethod = "getPowerOfAttorneyFallback", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
	})
	public ClientApiPowerOfAttorneyDTO getPowerOfAttorney(String poaId) {
			String url = UriComponentsBuilder.fromUriString(apiConfig.getUri())//
				.path(apiConfig.getPoaService() +"/")//
				.path(poaId)//
				.toUriString();
			return restTemplate.getForObject(url, ClientApiPowerOfAttorneyDTO.class);
	}

	@Override
	@HystrixCommand(fallbackMethod = "getDebitCardFallback", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
	})
	public ClientApiDebitCardDTO getDebitCard(String debitCardId) {
		String url = UriComponentsBuilder.fromUriString(apiConfig.getUri())//
			.path(apiConfig.getDebitCardService())//
			.path(debitCardId)//
			.toUriString();
		return restTemplate.getForObject(url, ClientApiDebitCardDTO.class);
	}

	@Override
	@HystrixCommand(fallbackMethod = "getCreditCardFallback", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
	})
	public ClientApiCreditCardDTO getCreditCard(String creditCardId) {
		String url = UriComponentsBuilder.fromUriString(apiConfig.getUri())//
			.path(apiConfig.getCreditCardService())//
			.path(creditCardId)//
			.toUriString();
		return restTemplate.getForObject(url, ClientApiCreditCardDTO.class);
	}

	@Override
	@HystrixCommand(fallbackMethod = "getAccountDetailsFallback", commandProperties = {
		@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
	})
	public ClientApiAccountDTO getAccountDetails(String accountId) {
		String url = UriComponentsBuilder.fromUriString(apiConfig.getUri())//
			.path(apiConfig.getAccountService())//
			.path(accountId)//
			.toUriString();
		return restTemplate.getForObject(url, ClientApiAccountDTO.class);
	}

	public List<ClientApiPowerOfAttorneyIdDTO> getPowerOfAttorneyIdsFallback(){
		log.warn("Using fallback for getPowerOfAttorneyIdsFallback");
		return null;
	}
	public ClientApiPowerOfAttorneyDTO getPowerOfAttorneyFallback(String poaId){
		log.warn("Using fallback for getPowerOfAttorneyFallback(" + poaId + ")");
		return null;
	}

	public ClientApiDebitCardDTO getDebitCardFallback(String poaId){
		log.warn("Using fallback for getDebitCardFallback(" + poaId + ")");
		return null;
	}

	public ClientApiCreditCardDTO getCreditCardFallback(String poaId){
		log.warn("Using fallback for getCreditCardFallback(" + poaId + ")");
		return null;
	}

	public ClientApiAccountDTO getAccountDetailsFallback(String poaId){
		log.warn("Using fallback for getAccountDetailsFallback(" + poaId + ")");
		return null;
	}
}
