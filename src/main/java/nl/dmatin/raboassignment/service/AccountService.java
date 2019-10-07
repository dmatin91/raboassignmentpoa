package nl.dmatin.raboassignment.service;

import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;

public interface AccountService {
	Boolean userAccountValidityCheck(ClientApiAccountDTO clientAccount, ClientApiPowerOfAttorneyDTO clientPoa);
	String getAccountNumberFromSimpleAccount(String clientAccount);
}
