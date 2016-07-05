package de.fhl.enca.bl;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class tests User class
 * @author Wu Zeling, Zhang Haoze
 * @version 05072016
 */
public class UserTest {

	static File directory = null;
	static File file = null;

	/**
	 * Set up directory and file path
	 */
	@BeforeClass
	public static void setup() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			directory = new File(System.getProperty("user.home") + User.WINDOWS_DIR);
		} else if (System.getProperty("os.name").startsWith("Mac")) {
			directory = new File(System.getProperty("user.home") + User.OSX_DIR);
		}
		file = new File(directory, User.FILE_NAME);
	}
	
	/**
	 * Delete existing profile files
	 */
	@Before
	@After
	public void deleteFile()
	{	
		if (directory.exists() && file.exists()) {
			file.delete();
			directory.delete();
		}
	}
	
	/**
	 * Test first use detection functionality
	 */
	@Test
	public void testInitialize() {
		User.initialize();
		assertEquals(User.isFirstUse(), true);
	}
	
	/**
	 * Test user preference logging functionality
	 */
	@Test
	public void testWriteUser() {
		User.initialize();
		User.writeUser();
		assertEquals(User.isFirstUse(), false);
		assertEquals(file.exists(), true);
	}
	
}
