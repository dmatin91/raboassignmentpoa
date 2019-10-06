package nl.dmatin.raboassignment.client.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.CardType;

@Data
public class ClientApiPoaCardDTO {
	private String id;
	private CardType type;
}
