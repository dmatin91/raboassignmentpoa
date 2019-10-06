package nl.dmatin.raboassignment.model.poa;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "debitCard")
@Getter
@Setter
public class DebitCard {
	@Id
	String id;
	private CardStatus status;
	private String cardNumber;
	private String sequenceNumber;
	private String cardHolder;
	private CardLimit atmLimit;
	private CardLimit posLimit;
	private Boolean contactless;
}
