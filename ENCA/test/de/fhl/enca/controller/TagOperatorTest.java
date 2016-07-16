package de.fhl.enca.controller;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;

/**
 * Class TagOperatorTest tests operations of tag.
 * @author Haoze Zhang
 * @version 15-07-2016
 *
 */
public class TagOperatorTest {

	static Tag tag;
	
	/**
	 * Create a test instance of tag
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		InternationalString internationalString;
		internationalString=new InternationalString();
		internationalString.setString(LanguageType.CHINESE, "清洁剂Cleaners");
		internationalString.setString(LanguageType.ENGLISH, "aaacleanersaaa");
		tag = new Tag(Tag.getMaxID(),internationalString,TagType.getTagType(1),false);
	}
		
	/**
	 * Test if created
	 */
	@Test
	public void testCreate() {
		TagOperator.createTag(tag);
		assertNotNull(Tag.getTag(tag.getTagID()));
	}
	
	/**
	 * Test modification
	 */
	@Test
	public void testModify() {
		tag.getName().setString(LanguageType.GERMAN, "Fachhochschule");
		TagOperator.modifyTag(tag);
		assertTrue(tag.getName().getString(LanguageType.GERMAN).equals("Fachhochschule"));
	}
	
	/**
	 * Test and remove the test tag
	 */
	@Test
	public void testRemove() {
		TagOperator.removeTag(tag);
		assertFalse(Tag.getTagsAll().contains(tag));
	}

}
