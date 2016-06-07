package model;

import java.util.Set;
import de.fhl.enca.bl.Tag;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class TagBean {

	private final SimpleIntegerProperty tagID;
	private final SimpleStringProperty name;

	public static ObservableList<TagBean> generateList(Set<Tag> source) {
		ObservableList<TagBean> list = FXCollections.observableArrayList();
		for (Tag tag : source) {
			list.add(new TagBean(tag));
		}
		return list;
	}

	public TagBean(Tag tag) {
		this.tagID = new SimpleIntegerProperty(tag.getTagID());
		this.name = new SimpleStringProperty(tag.getName().getString());
	}

	public int getTagID() {
		return tagID.get();
	}

	public String getName() {
		return name.get();
	}

	@Override
	public String toString() {
		return name.get();
	}
}