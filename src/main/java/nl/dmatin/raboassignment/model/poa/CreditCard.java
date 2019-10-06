package nl.dmatin.raboassignment.model.poa;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "creditCard")
@Getter
@Setter
public class CreditCard {
	@Id
	String id;
	private CardStatus status;
	private String cardNumber;
	private String sequenceNumber;
	private String cardHolder;
	private BigDecimal monthlyLimit;
}
