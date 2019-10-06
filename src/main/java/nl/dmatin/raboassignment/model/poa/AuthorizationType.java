package nl.dmatin.raboassignment.model.poa;

public enum AuthorizationType {
	DEBIT_CARD,
	CREDIT_CARD,
	VIEW,
	PAYMENT;

	public String getAuthorizationType() {
		return name();
	}
}
