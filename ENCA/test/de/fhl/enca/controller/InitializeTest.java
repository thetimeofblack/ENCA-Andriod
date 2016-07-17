package de.fhl.enca.controller;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.User;

/**
 * Class InitializeTest tests the initialization functionalities including Tags, CA and relation init.
 * @author Haoze Zhang
 * @version 15-07-2016
 */
public class InitializeTest {
	
	/**
	 * Init the memory content.
	 */
	@Before
	public void setup() {
		User.initialize();
		Initialize.initialize();
	}
	
	/**
	 * Test whether CAs are read into memory.
	 */
	@Test
	public void testCAsInit() {
		assertNotEquals(CleaningAgent.getCleaningAgentsAll().size(), 0);
	}
	
	/**
	 * Test whether Tags are read into memory.
	 */
	@Test
	public void testTagsInit() {
		assertNotEquals(Tag.getTagsAll().size(), 0);
	}
	
	/**
	 * Test if relations are correctly linked.
	 */
	@Test
	public void testRelationsInit() {
		boolean result = true;
		for (CleaningAgent ca: CleaningAgent.getCleaningAgentsAll()) {
			Set<Tag> s = ca.getTags();
			if (s.size() > 1) {
				for (Tag t1: s) {
				for (Tag t2: s) {
					if (t2 != t1) {
						result = result & t1.getTagsRelated().contains(t2);
					}
				}
				}
			}
		}
		assertEquals(result, true);
	}
}
