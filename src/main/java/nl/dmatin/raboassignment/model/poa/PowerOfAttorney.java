package nl.dmatin.raboassignment.model.poa;

import lombok.Getter;
import lombok.Setter;
import nl.dmatin.raboassignment.model.user.User;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "powerOfAttorney")
@Getter
@Setter
public class PowerOfAttorney {
	@Id
	String id;
	@DBRef
	private User grantor;
	@DBRef
	private User grantee;
	@DBRef
	private BankAccount account;
	private PoaDirection direction;
	private Set<AuthorizationType> authorizations;
	@DBRef
	private List<CreditCard> creditCards;
	@DBRef
	private List<DebitCard> debitCards;
}
