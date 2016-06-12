package model;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public final class CleaningAgentBean {

	private final SimpleStringProperty name;
	private final Set<Button> tags;

	public static ObservableList<CleaningAgentBean> generateList(Set<CleaningAgent> source, LanguageType type) {
		ObservableList<CleaningAgentBean> list = FXCollections.observableArrayList();
		for (CleaningAgent cleaningAgent : source) {
			list.add(new CleaningAgentBean(cleaningAgent, type));
		}
		return list;
	}

	public CleaningAgentBean(CleaningAgent cleaningAgent, LanguageType type) {
		this.name = new SimpleStringProperty(cleaningAgent.getName().getString(type));
		this.tags = new HashSet<>();
		for (int id : cleaningAgent.getTags()) {
			tags.add(new Button(Tag.getTag(id).getName().getString(type))); //set button color here in the future
		}
	}

	public String getName() {
		return name.get();
	}

	public Set<Button> getTags() {
		return tags;
	}
}