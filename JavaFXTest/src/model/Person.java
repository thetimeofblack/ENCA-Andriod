package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class Person {

	private final SimpleStringProperty name;
	private final SimpleIntegerProperty score;

	public Person(String name, int score) {
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleIntegerProperty(score);
	}

	public String getName() {
		return name.get();
	}

	public Integer getScore() {
		return score.get();
	}
}