package nl.dmatin.raboassignment.client;

import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyIdDTO;

import java.util.List;

public interface PowerOfAttorneyClient {
	List<ClientApiPowerOfAttorneyIdDTO> getAllPowerOfAttorneyIds();

	ClientApiPowerOfAttorneyDTO getPowerOfAttorney(String poaId);

	ClientApiDebitCardDTO getDebitCard(String debitCardId);

	ClientApiCreditCardDTO getCreditCard(String creditCardId);

	ClientApiAccountDTO getAccountDetails(String accountId);
}
