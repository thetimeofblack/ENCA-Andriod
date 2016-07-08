package com.enca.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.enca.bl.CleaningAgent;
import com.enca.bl.Tag;
import com.enca.dao.DatabaseVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class CleaningAgentFetcher
 * This class contains methods of operating cleaning agents
 * which are stored in memory in map CleaningAgent.cleaningAgentAll.
 *
 * @author Zhaowen.Gong
 * @version 31.05.2016
 */
public final class CleaningAgentFetcher {

    /**
     * Fetch cleaning agents related to all of the given tags
     *
     * @param tags the given set of tags
     * @return set of cleaning agents which are related to all of the given tags
     */
    public static Set<CleaningAgent> fetchCleaningAgentsOfTags(Set<Tag> tags) {
        Set<CleaningAgent> result = null;
        for (Tag tag : tags) {
            if (result == null) {
                /* Put all of the related cleaning agents of the first tag into the result */
                result = tag.getCleaningAgents();
            } else {
				/* Go through the result, only save those cleaning agents which are related to current tag */
                Set<CleaningAgent> tempSet = new HashSet<>();
                for (CleaningAgent cleaningAgent : result) {
                    if (cleaningAgent.getTags().contains(tag)) {
                        tempSet.add(cleaningAgent);
                    }
                }
                result = tempSet;
            }
        }
        return result;
    }

    /**
     * Search cleaning agents with the given keyword,
     * the result would be sorted according to the relevance.
     *
     * @param source  given cleaning agents
     * @param keyword keyword for searching
     */
    public static Set<CleaningAgent> fetchResult(Set<CleaningAgent> source, String keyword) {
        Map<CleaningAgent, Integer> tempMap = new HashMap<>();
        String[] subKeywords = keyword.split("\\p{Blank}|-");
        for (CleaningAgent cleaningAgent : source) {
            int relevance = 0;
            for (String subKeyword : subKeywords) {
                relevance += cleaningAgent.search(subKeyword.toLowerCase());
            }
            if (relevance > 0) {
                tempMap.put(cleaningAgent, relevance);
            }
        }
        List<Map.Entry<CleaningAgent, Integer>> sortingList = new ArrayList<>(tempMap.entrySet());
        Collections.sort(sortingList, new Comparator<Map.Entry<CleaningAgent, Integer>>() {
            @Override
            public int compare(Map.Entry<CleaningAgent, Integer> o1, Map.Entry<CleaningAgent, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        Set<CleaningAgent> tempSet = new LinkedHashSet<>();
        for (Map.Entry<CleaningAgent, Integer> entry : sortingList) {
            tempSet.add(entry.getKey());
        }
        return tempSet;
    }

    /**
     * Get the image of certain cleaning agent
     * @param cleaningAgentID Corresponding cleaningAgentID
     */
    public static Bitmap fetchImageOfCleaningAgent(int cleaningAgentID) {
        byte[] image = DatabaseVisitor.visitImage(cleaningAgentID);
        Bitmap bmp = new BitmapFactory().decodeByteArray(image, 0, image.length);
        return bmp;
    }

}