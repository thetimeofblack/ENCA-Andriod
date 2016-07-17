package de.fhl.enca.controller;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;

/**
 * Class TagFetcherTest tests fetching tags from database.
 * @author Haoze Zhang
 * @version 15-07-2016
 * 
 */
public class TagFetcherTest {
	
	/**
	 * Initialize database and relation
	 */
	@BeforeClass
	public static void setup() {
		User.initialize();
		Initialize.initialize();
	}

	/**
	 * Test if picked
	 */
	@Test
	public void testTypePick() {
		assertNotNull(TagFetcher.fetchTagsAllOfCertainType(TagType.ITEM));
	}
	
	/**
	 * Test if fetched related
	 */
	@Test
	public void testRelated() {
		assertNotNull(TagFetcher.fetchTagsRelated(Tag.getTagsAll()));
	}
	
	/**
	 * Test if sorted in order
	 */
	@Test
	public void testSorted() {
		Set<Tag> s = TagFetcher.fetchSortedTags(Tag.getTagsAll());
		Object[] ts = s.toArray();
		assertTrue(((Tag)ts[0]).getTagType().getId() <= ((Tag)ts[1]).getTagType().getId());
	}

}
