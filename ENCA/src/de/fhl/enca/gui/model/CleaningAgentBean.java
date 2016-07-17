package de.fhl.enca.gui.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.gui.utility.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.FlowPane;

/**
 * Representing object shown in the TableView control.</br>
 * Derived from a cleaning agent object.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class CleaningAgentBean {

	/**
	 * Stores the string expression of a language.
	 */
	private static Map<LanguageType, String> nationMap = new HashMap<>();
	static {
		nationMap.put(LanguageType.ENGLISH, " US");
		nationMap.put(LanguageType.GERMAN, " DE");
		nationMap.put(LanguageType.CHINESE, " CN");
	}

	/**
	 * ID of the cleaning agent.
	 */
	private final int id;

	private final String nation;
	/**
	 * Name of the cleaning agent.
	 */
	private final SimpleStringProperty name;

	/**
	 * The FlowPane shown in the TableView.
	 */
	private final FlowPane tags;

	/**
	 * Store all CleaningAgentBean.
	 */
	private static Map<Integer, Map<LanguageType, CleaningAgentBean>> map = new HashMap<>();

	/**
	 * Initialize all CleaningAgentBean.
	 */
	static {
		refreshCleaningAgentBeans();
	}

	/**
	 * Generate an ObservableList for TableView control.
	 * @param source cleaning agents as source
	 * @param type the LanguageType
	 * @return the ObservableList generated
	 */
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

	/**
	 * Add a cleaning agent to the map.
	 * @param cleaningAgent the cleaning agent to be added
	 */
	public static void addCleaningAgentBean(CleaningAgent cleaningAgent) {
		Map<LanguageType, CleaningAgentBean> tempMap = new HashMap<>();
		for (LanguageType languageType : LanguageType.values()) {
			tempMap.put(languageType, new CleaningAgentBean(cleaningAgent, languageType));
		}
		map.put(cleaningAgent.getCleaningAgentID(), tempMap);
	}

	/**
	 * Remove a cleaning agent from the map.
	 * @param cleaningAgent the cleaning agent to be removed
	 */
	public static void removeCleaningAgentBean(CleaningAgent cleaningAgent) {
		map.remove(cleaningAgent.getCleaningAgentID());
	}

	/**
	 * Refresh all the cleaning agent beans.
	 * Called when a cleaning agent or a tag is altered.
	 */
	public static void refreshCleaningAgentBeans() {
		map.clear();
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentsAll()) {
			addCleaningAgentBean(cleaningAgent);
		}
	}

	/**
	 * Constructor.</br>
	 * Will generate the FlowPane containing those Label containing those related tags of the cleaning agent.
	 * @param cleaningAgent the cleaning agent basing on
	 * @param type the LanguageType
	 */
	public CleaningAgentBean(CleaningAgent cleaningAgent, LanguageType type) {
		this.id = cleaningAgent.getCleaningAgentID();
		this.nation = nationMap.get(cleaningAgent.getMainLanguage());
		this.name = new SimpleStringProperty(cleaningAgent.getName().getString(type));
		tags = new FlowPane(5, 5);
		tags.setPrefHeight(0);
		tags.setPrefWrapLength(300);
		for (Tag tag : cleaningAgent.getTags()) {
			tags.getChildren().add(Utility.getTagLabel(tag, type));
		}
	}

	public int getId() {
		return id;
	}

	public String getNation() {
		return nation;
	}

	public String getName() {
		return name.get();
	}

	public FlowPane getTags() {
		return tags;
	}
}