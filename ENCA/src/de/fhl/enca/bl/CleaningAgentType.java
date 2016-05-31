package de.fhl.enca.bl;

public enum CleaningAgentType {
	SYSTEM("System"), USER("User");

	private String name;

	private CleaningAgentType(String name) {
		this.name = name;
	}

	public static CleaningAgentType getCleaningAgentType(String name) {
		for (CleaningAgentType cType : CleaningAgentType.values()) {
			if (cType.name.equals(name)) {
				return cType;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name;
	}
}