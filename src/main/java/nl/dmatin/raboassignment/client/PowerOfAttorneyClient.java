package nl.dmatin.raboassignment.client;

import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;

public interface PowerOfAttorneyClient {
	ClientApiPowerOfAttorneyDTO getPowerOfAttorney(String poaId);
	ClientApiDebitCardDTO getDebitCard(String debitCardId);
	ClientApiCreditCardDTO getCreditCard(String creditCardId);
	ClientApiAccountDTO getAccountDetails(String accountId);
}
