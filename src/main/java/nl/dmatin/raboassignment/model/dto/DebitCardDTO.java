package nl.dmatin.raboassignment.model.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.Card;
import nl.dmatin.raboassignment.model.poa.CardLimit;

@Data
public class DebitCardDTO implements Card {
	private String number;
	private String cardNumber;
	private String sequenceNumber;
	private String cardHolder;
	private CardLimit atmLimit;
	private CardLimit posLimit;
	private Boolean contactless;
}
