package com.enca.bl;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Enum CleaningAgentType
 * Cleaning Agents are classified in to two distinctive categories which are: SYSTEM 
 * and USER. CleaningAgentType enum provides consistent and type-safe marking 
 * of cleaning agent type. It is used globally in this application.
 */
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