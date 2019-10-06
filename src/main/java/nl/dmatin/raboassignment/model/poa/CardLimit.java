package nl.dmatin.raboassignment.model.poa;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardLimit {
	private BigDecimal limit;
	private LimitPeriodUnitType periodUnit;
}
