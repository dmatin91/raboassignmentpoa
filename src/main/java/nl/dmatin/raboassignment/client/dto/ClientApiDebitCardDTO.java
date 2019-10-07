package nl.dmatin.raboassignment.client.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.poa.CardLimit;
import nl.dmatin.raboassignment.model.poa.CardStatus;

@Data
public class ClientApiDebitCardDTO implements CardDTO {
	private String id;
	private CardStatus status;
	private String cardNumber;
	private String sequenceNumber;
	private String cardHolder;
	private CardLimit atmLimit;
	private CardLimit posLimit;
	private Boolean contactless;
}
