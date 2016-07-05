package de.fhl.enca.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SQLVisitorTest {

	@Before
	public void setUp() throws Exception {
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
