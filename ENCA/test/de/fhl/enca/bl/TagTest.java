package de.fhl.enca.bl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Class TagTest tests Object representing tag.
 * @author Zeling.Wu
 * @version 15.07.2016
 */
public class TagTest {

	Tag tag;
	CleaningAgent cleaningAgent;
	@Before
	public void setUp() throws Exception {
		InternationalString internationalString;
		internationalString=new InternationalString();
		internationalString.setString(LanguageType.CHINESE, "清洁剂Cleaners");
		internationalString.setString(LanguageType.ENGLISH, "aaacleanersaaa");
		tag = new Tag(Tag.getMaxID(),internationalString,TagType.getTagType(1),false);
		
		cleaningAgent=new CleaningAgent(0);
		
		InternationalString name=new InternationalString();
		name.setString(LanguageType.GERMAN, "frosch Glasreiniger");
		cleaningAgent.setName(name);
		
		InternationalString description=new InternationalString();
		description.setString(LanguageType.GERMAN, "SODIUM LAURETH SULFATE");
		cleaningAgent.setDescription(description);
		
		InternationalString instruction=new InternationalString();
		instruction.setString(LanguageType.GERMAN, "Der Frosch Neutral Reiniger ist ein natürlicher und sehr ergiebiger Allzweckreiniger für die schonende Reinigung von Böden, Holz-, Küchen- und Badmöbeln sowie Kacheln und Sanitäreinrichtungen.");
		cleaningAgent.setInstruction(instruction);
		
		cleaningAgent.setApplicationTime(1);
		
		cleaningAgent.setApplicationTime(2);
		
		cleaningAgent.setBelongsToSystem(true);
		
		cleaningAgent.setRate(10);
		
		cleaningAgent.setMainLanguage(LanguageType.GERMAN);
		
		cleaningAgent.setMemo("test Cleaning Agent");
	}

	@Test
	public void testAddTag() {
		Tag.addTag(tag);
		assertEquals(Tag.getTag(tag.getTagID()),tag);
		Tag.removeTag(tag);
	}

	@Test
	public void testRemoveTag(){
		Tag.addTag(tag);
		Tag.removeTag(tag);
		assertNull(Tag.getTag(tag.getTagID()));
	}
	
	@Test
	public void testSearch(){
		Tag.addTag(tag);
		assertEquals(tag.search("Cleaners"),2);
	}
	
	@Test
	public void testAddCleaningAgent(){
		tag.addCleaningAgent(cleaningAgent);
		assertNotNull(tag.getCleaningAgents());
		tag.removeCleaningAgent(cleaningAgent);
	}
	
	@Test
	public void testRemoveCleaningAgent(){
		assertEquals(tag.getCleaningAgents().size(),0);
	}
	
	@Test
	public void testAddTagRelated() {
		InternationalString internationalString;
		internationalString=new InternationalString();
		internationalString.setString(LanguageType.CHINESE, "洗碗机");
		internationalString.setString(LanguageType.ENGLISH, "washingmachineaaa");
		Tag tempTag = new Tag(Tag.getMaxID(),internationalString,TagType.getTagType(1),false);
		
		tag.addTagRelated(tempTag);
		assertEquals(tag.getTagsRelated().size(),1);
		tag.removeTagRelated(tempTag);
	}
	
	@Test
	public void testRemoveTagRelated()
	{
		InternationalString internationalString;
		internationalString=new InternationalString();
		internationalString.setString(LanguageType.CHINESE, "洗碗机");
		internationalString.setString(LanguageType.ENGLISH, "washingmachineaaa");
		Tag tempTag = new Tag(Tag.getMaxID(),internationalString,TagType.getTagType(1),false);
		
		tag.addTagRelated(tempTag);
		tag.removeTagRelated(tempTag);
		assertEquals(tag.getTagsRelated().size(),0);
	}
}
