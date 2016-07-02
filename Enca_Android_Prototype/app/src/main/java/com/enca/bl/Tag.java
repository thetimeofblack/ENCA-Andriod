package com.enca.bl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Object representing tag.
 *
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class Tag {

	/* static member */
    /**
     * Store the references of all tags
     */
    private static Map<Integer, Tag> tagsAll = new HashMap<>();

    /* non-static member */
    private int tagID;
    private InternationalString name;
    private TagType tagType;

    private Set<CleaningAgent> cleaningAgents = new HashSet<>();
    private Set<Tag> tagsRelated = new HashSet<>();

    /* static method */
    public static Tag getTag(int ID) {
        return tagsAll.get(ID);
    }

    public static void addTag(Tag tag) {
        tagsAll.put(tag.getTagID(), tag);
    }

    public static Set<Tag> getTagsAll() {
        Set<Tag> set = new HashSet<>();
        for (Tag tag : tagsAll.values()) {
            set.add(tag);
        }
        return set;
    }

    /* non-static method */
    public Tag(int tagID, InternationalString name, TagType tagType) {
        this.tagID = tagID;
        this.name = name;
        this.tagType = tagType;
        /* directly put this tag into tagsAll */
        tagsAll.put(tagID, this);
    }

    @Override
    public String toString() {
        return "Tag[" + tagID + " - " + name.getInterfaceString() + "]\n";
    }

    /* getters and setters */
    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public InternationalString getName() {
        return name;
    }

    public void setName(InternationalString name) {
        this.name = name;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public Set<CleaningAgent> getCleaningAgents() {
        return cleaningAgents;
    }

    public Set<Tag> getTagsRelated() {
        return tagsRelated;
    }

    public int search(String keyword) {
        return name.search(keyword);
    }


}