package nl.dmatin.raboassignment.model.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.Card;
import nl.dmatin.raboassignment.model.poa.PoaDirection;

import java.util.Set;

@Data
public class PowerOfAttorneyDTO {
	private String id;
	private String grantor;
	private String grantee;
	private AccountDTO account;
	private PoaDirection direction;
	private Set<Card> cards;
}
