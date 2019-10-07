package nl.dmatin.raboassignment.service;

import lombok.extern.slf4j.Slf4j;
import nl.dmatin.raboassignment.client.dto.CardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiCreditCardDTO;
import nl.dmatin.raboassignment.client.dto.ClientApiDebitCardDTO;
import nl.dmatin.raboassignment.model.TheFellowshipOfTheRingGroup;
import nl.dmatin.raboassignment.model.poa.AuthorizationType;
import nl.dmatin.raboassignment.model.poa.CardStatus;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

	@Override
	public Boolean cardValidityCheck(CardDTO card, String granterName) {
		if (card == null) {
			return Boolean.FALSE;
		}
		if (card.getStatus().equals(CardStatus.ACTIVE)) {
			return Boolean.FALSE;
		}
		if (StringUtils.equalsIgnoreCase(granterName, "The Fellowship of the Ring")) {
			return TheFellowshipOfTheRingGroup.contains(card.getCardHolder());
		}

		if (!StringUtils.equalsIgnoreCase(card.getCardHolder(), granterName)) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	@Override
	public Boolean cardAuthorizationCheck(CardDTO card, Set<AuthorizationType> authorizationTypes) {
		if (card instanceof ClientApiCreditCardDTO) {
			return authorizationTypes.contains(AuthorizationType.CREDIT_CARD);
		} else if (card instanceof ClientApiDebitCardDTO) {
			return authorizationTypes.contains(AuthorizationType.DEBIT_CARD);
		}
		return Boolean.FALSE;
	}
}
