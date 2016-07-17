package de.fhl.enca.dao;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fhl.enca.bl.User;
/**
 * Class SQLVisitor Execute select SQL operations.
 * @author Zeling WU
 * @version 15-07-2016
 *
 */
public class SQLVisitorTest {

	@BeforeClass
	public static void setUp() throws Exception {
		User.initialize();
	}

	@Test
	public void testVisitCleaningAgentsAll() {
		assertNotNull(SQLVisitor.visitCleaningAgentsAll());
	}

	@Test
	public void testVisitTagsAll()
	{
		assertNotNull(SQLVisitor.visitTagsAll());
	}
	
	@Test
	public void testVisitRelations()
	{
		assertNotNull(SQLVisitor.visitRelations());
	}
	
	@Test
	public void testVisitImage()
	{
		assertNotNull(SQLVisitor.visitImage(1));
	}
}
