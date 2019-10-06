package nl.dmatin.raboassignment.client.dto;

import lombok.Data;
import nl.dmatin.raboassignment.model.poa.AuthorizationType;
import nl.dmatin.raboassignment.model.poa.PoaDirection;

import java.util.List;
import java.util.Set;

@Data
public class ClientApiPowerOfAttorneyDTO {
	private Long id;
	private String grantor;
	private String grantee;
	private String account;
	private PoaDirection direction;
	private Set<AuthorizationType> authorizations;
	private List<ClientApiPoaCardDTO> cards;
}
