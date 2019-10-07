package nl.dmatin.raboassignment.model.poa;

public enum CardStatus {
	ACTIVE,BLOCKED;

	public String getCardStatus() {
		return name();
	}
	public static boolean contains(String test) {

		for (CardStatus f : CardStatus.values()) {
			if (f.name().equals(test)) {
				return true;
			}
		}

		return false;
	}
}
