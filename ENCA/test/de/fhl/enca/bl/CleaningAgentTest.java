package de.fhl.enca.bl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class CleaningAgentTest {

	CleaningAgent mCleaningAgent;
	
	@Before
	public void setUp() throws Exception {
		mCleaningAgent=new CleaningAgent(0);
		
		InternationalString name=new InternationalString();
		name.setString(LanguageType.GERMAN, "frosch Glasreiniger");
		mCleaningAgent.setName(name);
		
		InternationalString description=new InternationalString();
		description.setString(LanguageType.GERMAN, "SODIUM LAURETH SULFATE");
		mCleaningAgent.setDescription(description);
		
		InternationalString instruction=new InternationalString();
		instruction.setString(LanguageType.GERMAN, "Der Frosch Neutral Reiniger ist ein natürlicher und sehr ergiebiger Allzweckreiniger für die schonende Reinigung von Böden, Holz-, Küchen- und Badmöbeln sowie Kacheln und Sanitäreinrichtungen.");
		mCleaningAgent.setInstruction(instruction);
		
		mCleaningAgent.setApplicationTime(1);
		
		mCleaningAgent.setApplicationTime(2);
		
		mCleaningAgent.setBelongsToSystem(true);
		
		mCleaningAgent.setRate(10);
		
		mCleaningAgent.setMainLanguage(LanguageType.GERMAN);
		
		mCleaningAgent.setMemo("test Cleaning Agent");
	}

	@Test
	public void testAddCleaningAgent() {
		mCleaningAgent.addCleaningAgent(mCleaningAgent);
		assertEquals(mCleaningAgent.getCleaningAgentID(),0);
	}

	@Test
	public void testRefreshCleaningAgentWithMemo() {
		mCleaningAgent.refreshCleaningAgentWithMemo(mCleaningAgent);
		assertEquals(mCleaningAgent.getCleaningAgentsWithMemo().contains(mCleaningAgent),true);
	}

	@Test
	public void testRemoveCleaningAgent() {
		testAddCleaningAgent();
		mCleaningAgent.removeCleaningAgent(mCleaningAgent);
		assertEquals(mCleaningAgent.getCleaningAgent(0),null);
	}

	@Test
	public void testAddTag() {
		InternationalString internationalString = new InternationalString();
		internationalString.setString(LanguageType.GERMAN, "Küche");
		Tag tag=new Tag(0,internationalString,TagType.ROOM,false);
		mCleaningAgent.addTag(tag);
		assertEquals(mCleaningAgent.getTags().contains(tag),true);
	}
	
	@Test
	public void testAddTagsAll() {
		InternationalString internationalString = new InternationalString();
		internationalString.setString(LanguageType.GERMAN, "Küche");
		Tag tag=new Tag(0,internationalString,TagType.ROOM,false);
		Set<Tag> s=new HashSet<Tag>();
		s.add(tag);
		mCleaningAgent.addTagsAll(s);
		assertEquals(mCleaningAgent.getTags().contains(tag),true);
	}
	
	@Test
	public void testSearch() {
		testAddTag();
		assertEquals(mCleaningAgent.search("Küche"),3);
	}

	@Test
	public void testRemoveTag() {
		InternationalString internationalString = new InternationalString();
		internationalString.setString(LanguageType.GERMAN, "Küche");
		Tag tag=new Tag(0,internationalString,TagType.ROOM,false);
		
		testAddTag();
		mCleaningAgent.removeTag(tag);
		
		assertEquals(mCleaningAgent.getTags().contains(tag),false);
	}

	@Test
	public void testRemoveTagsAll() {
		InternationalString internationalString = new InternationalString();
		internationalString.setString(LanguageType.GERMAN, "Küche");
		Tag tag=new Tag(0,internationalString,TagType.ROOM,false);
		Set<Tag> s=new HashSet<Tag>();
		s.add(tag);
		
		testAddTagsAll();
		mCleaningAgent.removeTagsAll(s);
		
		assertEquals(mCleaningAgent.getTags().contains(tag),false);
	}

	@Test
	public void testBelongsToSystem() {
		assertEquals(mCleaningAgent.BelongsToSystem(),true);
	}

}
