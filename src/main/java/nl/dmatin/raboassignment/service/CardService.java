package nl.dmatin.raboassignment.service;

import nl.dmatin.raboassignment.client.dto.CardDTO;
import nl.dmatin.raboassignment.model.poa.AuthorizationType;

import java.util.Set;

public interface CardService {
	Boolean cardValidityCheck(CardDTO card, String granterName);
	Boolean cardAuthorizationCheck(CardDTO card, Set<AuthorizationType> authorizationTypes);
}
