package com.enca.controller;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.enca.bl.CleaningAgent;
import com.enca.bl.InternationalString;
import com.enca.bl.LanguageType;
import com.enca.bl.Memo;
import com.enca.bl.Tag;
import com.enca.bl.TagType;
import com.enca.dao.DatabaseVisitor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 85102 on 6/18/2016.
 */
public class DataInitialize {

    public static void initialize() {
        initCleaningAgents();
        initTags();
        initRelations();
    }

    public static void initializeConcurrently() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initialize();
            }
        });
    }

    /**
     * Initialize all cleaning agents and store them into the memory
     */
    private static void initCleaningAgents() {
        Cursor cursor = DatabaseVisitor.visitCleaningAgentsAll();
        try {
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        CleaningAgentBuilder builder = new CleaningAgentBuilder();
                        builder.setID(cursor.getInt(0));
                        builder.setName(iStringGenerator(cursor, 1));
                        builder.setDescription(iStringGenerator(cursor, 4));
                        builder.setInstruction(iStringGenerator(cursor, 7));
                        builder.setApplicationTime(cursor.getLong(10));
                        builder.setFrequency(cursor.getLong(11));
                        builder.setType(cursor.getString(12));
                        builder.setRate(cursor.getInt(13));
                        builder.setMainLanguage(cursor.getInt(14));
//                        builder.setImage(cursor.getBlob(15));
                        builder.getResult();
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize all tags and store them into the memory
     */
    private static void initTags() {
        Cursor cursor = DatabaseVisitor.visitTagsAll();
        try{
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        new Tag(cursor.getInt(0), iStringGenerator(cursor, 1), TagType.getTagType(cursor.getString(4)));
                        cursor.moveToNext();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the relations between cleaning agents and tags
     * and the relations between tags
     */
    private static void initRelations() {
		/* Key: a cleaning agent, value: the set of related tags of the cleaning agent */
        Map<CleaningAgent, Set<Tag>> ctMap = new HashMap<>();
		/* Key: a tag, value: the set of related cleaning agents of the tag */
        Map<Tag, Set<CleaningAgent>> tcMap = new HashMap<>();
        initTCRelations(ctMap, tcMap);
        initTTRelations(ctMap);
    }

    /**
     * Initialize the relations between cleaning agents and tags
     */
    private static void initTCRelations(Map<CleaningAgent, Set<Tag>> ctMap, Map<Tag, Set<CleaningAgent>> tcMap) {
        Cursor cursor = DatabaseVisitor.visitRelations();
        try {
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        CleaningAgent cleaningAgent = CleaningAgent.getCleaningAgent(cursor.getInt(0));
                        Tag tag = Tag.getTag(cursor.getInt(1));
				/* Add the cleaning agent and its tags set to ctMap */
                        if (!ctMap.containsKey(cleaningAgent)) {
                            ctMap.put(cleaningAgent, cleaningAgent.getTags());
                        }
				/* Add the tag and its cleaning agents set to tcMap */
                        if (!tcMap.containsKey(tag)) {
                            tcMap.put(tag, tag.getCleaningAgents());
                        }
				/* Add the tag into the tags set of the cleaning agent */
                        ctMap.get(cleaningAgent).add(tag);
				/* Add the cleaning agent into the cleaning agents set of the tag */
                        tcMap.get(tag).add(cleaningAgent);
                        cursor.moveToNext();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize the relations between tags
     */
    private static void initTTRelations(Map<CleaningAgent, Set<Tag>> ctMap) {
		/* Go through every single tags set */
        for (Set<Tag> group : ctMap.values()) {
			/* The following two layers of loop is to build the relation of every two tags in a tags set */
            for (Tag tag1 : group) {
                for (Tag tag2 : group) {
					/* Ensure the two tags are not with the same tagType*/
                    if (tag1.getTagType() != tag2.getTagType()) {
                        tag1.getTagsRelated().add(tag2);
                    }
                }
            }
        }
    }

    /**
     * Generate InternationalString according to ResultSet object and column number,
     * allows reusing replicate codes.
     */

    private static InternationalString iStringGenerator(Cursor cursor, int i){
        InternationalString iString = new InternationalString();
        try {
            iString.setString(LanguageType.ENGLISH, cursor.getString(i));
            iString.setString(LanguageType.GERMAN, cursor.getString(i+2));
            iString.setString(LanguageType.CHINESE, cursor.getString(i+1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iString;
    }

    /**
     * Initialize memos.
     */
    private static void initMemos() {
        Cursor cursor = DatabaseVisitor.visitMemos();
        try {
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        new Memo(cursor.getInt(0), CleaningAgent.getCleaningAgent(cursor.getInt(2)), cursor.getString(3), new Date());
                        cursor.moveToNext();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
