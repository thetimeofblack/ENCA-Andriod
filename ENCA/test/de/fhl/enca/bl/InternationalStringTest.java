package de.fhl.enca.bl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of class InternationalString
 * 
 * @author Wu Zeling, Haoze Zhang
 * @version 05072016
 */
public class InternationalStringTest {

	InternationalString mInternationalString;
	@Before
	public void setUp() throws Exception {
		mInternationalString=new InternationalString();
		mInternationalString.setString(LanguageType.CHINESE, "清洁剂Cleaners");
		mInternationalString.setString(LanguageType.ENGLISH, "aaacleanersaaa");
	}
	
	/**
	 * Test if it returns right relevance
	 */
	@Test
	public void testRelevance() {
		assertEquals(mInternationalString.search("Cleaners"), 2);
	}
	
	/**
	 * Make InternationalString would not return a null of an empty content.
	 */
	@Test
	public void testNullField() {
		assertNotEquals(mInternationalString.getString(LanguageType.GERMAN), null);
	}

}
