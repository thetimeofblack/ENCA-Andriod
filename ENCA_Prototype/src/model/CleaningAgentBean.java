package model;

import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public final class CleaningAgentBean {

	private final int id;
	private final SimpleStringProperty name;
	private final HBox tags;

	public static ObservableList<CleaningAgentBean> generateList(Set<CleaningAgent> source, LanguageType type) {
		ObservableList<CleaningAgentBean> list = FXCollections.observableArrayList();
		for (CleaningAgent cleaningAgent : source) {
			list.add(new CleaningAgentBean(cleaningAgent, type));
		}
		return list;
	}

	public CleaningAgentBean(CleaningAgent cleaningAgent, LanguageType type) {
		this.id = cleaningAgent.getCleaningAgentID();
		this.name = new SimpleStringProperty(cleaningAgent.getName().getString(type));
		tags = new HBox(5);
		List<Tag> tagList = new ArrayList<>(cleaningAgent.getTags());
		tagList.sort((Tag o1, Tag o2) -> o1.getTagType().getId() - o2.getTagType().getId());
		for (Tag tag : tagList) {
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

	public HBox getTags() {
		return tags;
	}
}