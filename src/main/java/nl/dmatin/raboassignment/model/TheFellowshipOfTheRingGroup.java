package nl.dmatin.raboassignment.model;

public enum TheFellowshipOfTheRingGroup {
	BOROMIR("Boromir"),
	FRODO_BAGGINS("Frodo Baggins"),
	ARAGORN("Aragorn");

	public final String label;

	private TheFellowshipOfTheRingGroup(String label) {
		this.label = label;
	}

	public static boolean contains(String test) {

		for (TheFellowshipOfTheRingGroup f : TheFellowshipOfTheRingGroup.values()) {
			if (f.label.equals(test)) {
				return true;
			}
		}

		return false;
	}
}
