package nl.dmatin.raboassignment.service;

import lombok.extern.slf4j.Slf4j;
import nl.dmatin.raboassignment.client.dto.ClientApiAccountDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiPowerOfAttorneyDTO;
import nl.dmatin.raboassignment.model.poa.AuthorizationType;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Override
	public Boolean userAccountValidityCheck(ClientApiAccountDTO clientAccount, ClientApiPowerOfAttorneyDTO clientPoa) {
		if (clientPoa == null || clientAccount == null) {
			return Boolean.FALSE;
		}
		if(!clientPoa.getAuthorizations().contains(AuthorizationType.VIEW)){
			return Boolean.FALSE;
		}
		if (!StringUtils.equalsIgnoreCase(clientAccount.getOwner(), clientPoa.getGrantor())) {
			return Boolean.FALSE;
		}
		if (clientAccount.getEnded() != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateOfTheClientAccountEnding = sdf.parse(clientAccount.getEnded());
				Date todayDate = new Date();
				if (dateOfTheClientAccountEnding.before(todayDate)) {
					return Boolean.FALSE;
				}
			}
			catch (Exception e) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;

	}

	//TODO Implement IBAN checker - currently we are assuming that its NL and its RABObank - NL23RABO
	@Override
	public String getAccountNumberFromSimpleAccount(String clientAccount) {
		return clientAccount.substring(8);
	}
}
