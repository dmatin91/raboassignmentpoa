package nl.dmatin.raboassignment.client.dto;

import nl.dmatin.raboassignment.model.poa.CardStatus;

public interface CardDTO {
	CardStatus getStatus();
	String getCardHolder();
}
