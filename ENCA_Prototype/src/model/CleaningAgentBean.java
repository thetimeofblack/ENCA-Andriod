package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public final class CleaningAgentBean {

	private final int id;
	private final SimpleStringProperty name;
	private final FlowPane tags;

	private static Map<Integer, Map<LanguageType, CleaningAgentBean>> map = new HashMap<>();
	static {
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentsAll()) {
			addCleaningAgentBean(cleaningAgent);
		}
	}

	public static ObservableList<CleaningAgentBean> generateList(Set<CleaningAgent> source, LanguageType type) {
		ObservableList<CleaningAgentBean> list = FXCollections.observableArrayList();
		for (CleaningAgent cleaningAgent : source) {
			if (list.size() <= 100) {
				list.add(map.get(cleaningAgent.getCleaningAgentID()).get(type));
			} else {
				break;
			}
		}
		return list;
	}

	public static void addCleaningAgentBean(CleaningAgent cleaningAgent) {
		Map<LanguageType, CleaningAgentBean> tempMap = new HashMap<>();
		for (LanguageType languageType : LanguageType.values()) {
			tempMap.put(languageType, new CleaningAgentBean(cleaningAgent, languageType));
		}
		map.put(cleaningAgent.getCleaningAgentID(), tempMap);
	}

	public static void removeCleaningAgentBean(CleaningAgent cleaningAgent) {
		map.remove(cleaningAgent.getCleaningAgentID());
	}

	public CleaningAgentBean(CleaningAgent cleaningAgent, LanguageType type) {
		this.id = cleaningAgent.getCleaningAgentID();
		this.name = new SimpleStringProperty(cleaningAgent.getName().getString(type));
		tags = new FlowPane(5, 5);
		tags.setPrefHeight(0);
		tags.setPrefWrapLength(700);
		for (Tag tag : cleaningAgent.getTags()) {
			Label label = new Label(tag.getName().getString(type));
			label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			label.setPadding(new Insets(0, 4, 0, 4));
			tags.getChildren().add(label);
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name.get();
	}

	public FlowPane getTags() {
		return tags;
	}
}