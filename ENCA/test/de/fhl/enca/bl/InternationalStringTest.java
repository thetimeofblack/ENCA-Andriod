package de.fhl.enca.bl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InternationalStringTest {

	InternationalString mInternationalString;
	@Before
	public void setUp() throws Exception {
		mInternationalString=new InternationalString();
	}

	@Test
	public void testSetGetString() {
		mInternationalString.setString(LanguageType.CHINESE, "清洁剂");
		mInternationalString.setString(LanguageType.ENGLISH, "Cleaners");
		mInternationalString.setString(LanguageType.GERMAN, "Cleaners");
		assertEquals(mInternationalString.getString(LanguageType.CHINESE),"清洁剂");
		assertEquals(mInternationalString.getString(LanguageType.ENGLISH),"Cleaners");
		assertEquals(mInternationalString.getString(LanguageType.GERMAN),"Cleaners");
	}
	
	@Test
	public void testSearch() {
		testSetGetString();
		assertEquals(mInternationalString.search("Cleaners"),2);
	}
}
