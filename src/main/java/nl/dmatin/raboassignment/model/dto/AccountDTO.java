package nl.dmatin.raboassignment.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountDTO {
	private String number;
	private String owner;
	private BigDecimal balance;
	private String created;
}
