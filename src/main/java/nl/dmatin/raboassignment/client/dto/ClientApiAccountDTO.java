package nl.dmatin.raboassignment.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientApiAccountDTO {
	private String owner;
	private BigDecimal balance;
	private String created;
}
