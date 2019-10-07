package nl.dmatin.raboassignment.model.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.Card;
import nl.dmatin.raboassignment.model.CardType;

import java.math.BigDecimal;

@Data
public class CreditCardDTO implements Card {
	private String number;
	private String cardNumber;
	private String sequenceNumber;
	private String cardHolder;
	private BigDecimal monthlyLimit;
	private CardType type;
}
