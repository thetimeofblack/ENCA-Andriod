package de.fhl.enca.controller;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;

/**
 * Class CleaningAgentOperatorTest tests operations on CAs. 
 * @author Haoze Zhang
 * @version 15-07-2016
 */
public class CleaningAgentOperatorTest {

	final static int ID = 20000;
	static CleaningAgent mCleaningAgent;
	
	/**
	 * Initialize database and create a test instance of CA
	 */
	@BeforeClass
	public static void setup() {
		Initialize.initialize();
		
		mCleaningAgent=new CleaningAgent(ID);
		
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
		
		CleaningAgentOperator.createCleaningAgent(mCleaningAgent);
	}
	
	/**
	 * Test if created
	 */
	@Test
	public void testCreate() {
		assertNotEquals(CleaningAgent.getCleaningAgent(ID), null);
	}

	/**
	 * Test if able to save
	 */
	@Test
	public void testSaveMemo() {
		CleaningAgent ca = CleaningAgent.getCleaningAgent(ID);
		CleaningAgentOperator.saveMemo(ca, "Test memo");
		assertEquals(ca.getMemo().equals("Test memo"), true);
		assertEquals(CleaningAgent.getCleaningAgentsWithMemo().contains(ca), true);
		CleaningAgentOperator.saveMemo(ca, null);
		assertEquals(CleaningAgent.getCleaningAgentsWithMemo().contains(ca), false);
	}
	
	/**
	 * Test if can be modified
	 */
	@Test
	public void testModifyCleaningAgent() {
		CleaningAgent ca = new CleaningAgent(ID);
		ca.setMemo("Changed");
		assertEquals(ca.getMemo().equals("Changed"), true);
	}

	/**
	 * Test if successfully removed
	 */
	@Test
	public void testRemove() {
		CleaningAgentOperator.removeCleaningAgent(CleaningAgent.getCleaningAgent(ID));
		assertEquals(CleaningAgent.getCleaningAgent(ID), null);
	}
}
