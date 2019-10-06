package nl.dmatin.raboassignment.model.poa;

public enum LimitPeriodUnitType {
	PER_WEEK, PER_MONTH, PER_DAY, PER_YEAR;

	public String getLimitPeriodUnitType() {
		return name();
	}
}
