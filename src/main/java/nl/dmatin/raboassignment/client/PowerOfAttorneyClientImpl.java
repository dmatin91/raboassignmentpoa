package nl.dmatin.raboassignment.client;

import lombok.extern.slf4j.Slf4j;
import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PowerOfAttorneyClientImpl implements PowerOfAttorneyClient {
	@Override
	public ClientApiPowerOfAttorneyDTO getPowerOfAttorney(String poaId) {
		return null;
	}

	@Override
	public ClientApiDebitCardDTO getDebitCard(String debitCardId) {
		return null;
	}

	@Override
	public ClientApiCreditCardDTO getCreditCard(String creditCardId) {
		return null;
	}

	@Override
	public ClientApiAccountDTO getAccountDetails(String accountId) {
		return null;
	}
}
