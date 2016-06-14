package model;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.Tag;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class TagBean {

	private final int tagID;
	private final SimpleStringProperty name;

	public static ObservableList<TagBean> generateList(Set<Tag> source) {
		ObservableList<TagBean> list = FXCollections.observableArrayList();
		for (Tag tag : source) {
			list.add(new TagBean(tag));
		}
		return list;
	}

	public static Set<Tag> convert(Set<TagBean> source) {
		Set<Tag> set = new HashSet<Tag>();
		for (TagBean tagBean : source) {
			set.add(Tag.getTag(tagBean.getTagID()));
		}
		return set;
	}

	public TagBean(Tag tag) {
		this.tagID = tag.getTagID();
		this.name = new SimpleStringProperty(tag.getName().getString());
	}

	public int getTagID() {
		return tagID;
	}

	public String getName() {
		return name.get();
	}

	@Override
	public String toString() {
		return name.get();
	}
}