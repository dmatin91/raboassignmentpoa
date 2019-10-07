package nl.dmatin.raboassignment.client.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.poa.CardStatus;

import java.math.BigDecimal;

@Data
public class ClientApiCreditCardDTO implements CardDTO {
	private String id;
	private CardStatus status;
	private String cardNumber;
	private String sequenceNumber;
	private String cardHolder;
	private BigDecimal monthlyLimit;
}
