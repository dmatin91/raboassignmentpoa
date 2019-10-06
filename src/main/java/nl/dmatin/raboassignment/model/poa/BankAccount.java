package nl.dmatin.raboassignment.model.poa;

import lombok.Getter;
import lombok.Setter;
import nl.dmatin.raboassignment.model.user.User;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bankAccount")
@Getter
@Setter
public class BankAccount {
	@Id
	String id;
	@DBRef
	private User owner;
	private BigDecimal balance;
	private String created;
	private String ended;

}
