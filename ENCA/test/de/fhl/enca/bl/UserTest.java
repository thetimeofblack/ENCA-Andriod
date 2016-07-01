package de.fhl.enca.bl;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UserTest {

	User mUser;
	
	@Before
	public void setUp() throws Exception {
		mUser= new User();
	}

	@Test
	public void testInitialize() {
		mUser.initialize();
	}
	
	@Test
	public void testIsFirstUse() {
		deleteInitFile();
		testInitialize();
		assertEquals(mUser.isFirstUse(),true);
	}
	
	@Test
	public void testWriteUser() {
		testIsFirstUse();
		assertEquals(mUser.isFirstUse(),true);
	}
	
	public void deleteInitFile()
	{
		File directory=null;
		File file=null;
		if (System.getProperty("os.name").startsWith("Windows")) {
			directory = new File(System.getProperty("user.home") + "\\Documents\\Enca");
			file = new File(directory, "user.ini");
		} else if (System.getProperty("os.name").startsWith("Mac")) {
			directory = new File(System.getProperty("user.home") + "/Library/Application Support/Enca");
			file = new File(directory, "user.ini");
		}
		
		if (directory.exists() && file.exists()) {
			file.delete();
		}
	}

}
