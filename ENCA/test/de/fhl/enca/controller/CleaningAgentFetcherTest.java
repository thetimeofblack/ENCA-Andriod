package de.fhl.enca.controller;

import static org.junit.Assert.*;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.User;

/**
 * This class tests Fetcher Contain methods of fetching cleaning agents.
 * @author Wu Zeling
 * @version 15072016
 */
public class CleaningAgentFetcherTest {

	CleaningAgentFetcher mCleaningAgentFetcher = new CleaningAgentFetcher();
	CleaningAgent mTestCleaningAgent;
	Tag mTestTag;
	
	/**
	 * a test which need initializing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		User.initialize();
		Initialize.initialize();
		
		mTestTag = Tag.getTag(1);
		Set<Tag> s=new HashSet<Tag>();
		s.add(mTestTag);
		Object[] temp=CleaningAgentFetcher.fetchCleaningAgentsOfTags(s).toArray();
		mTestCleaningAgent=(CleaningAgent)temp[1];
	}
	
	/**
	 * test Fetch CleaningAgent's Tags
	 */
	@Test
	public void testFetchCleaningAgentsOfTags() {
		mTestCleaningAgent.getTags().contains(mTestTag);
		assertEquals(mTestCleaningAgent.getTags().contains(mTestTag),true);
	}
	
	/**
	 * test Fetching certain image
	 */
	@Test
	public void testFetchImageOfCleaningAgent()
	{	
		assertEquals(CleaningAgentFetcher.fetchImageOfCleaningAgent(mTestCleaningAgent).getClass(),Image.class);
	}

	/**
	 * test getting result by creating temporary data
	 */
	@Test
	public void testFetchResult() {
		Set<CleaningAgent> cleaningAgentSet = new HashSet<CleaningAgent>();
		try{
			for(int i=0;i<5;i++)
			{
				CleaningAgent cleaningAgent=new CleaningAgent(i);
				
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
				
				cleaningAgentSet.add(cleaningAgent);
			}
		}catch(Exception e)
		{}
		
		assertEquals(CleaningAgentFetcher.fetchResult(cleaningAgentSet,"frosch"),cleaningAgentSet);
	}

	
}
