package de.fhl.enca.dao;

import static org.junit.Assert.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;

import org.junit.Ignore;
/**
 * Test Class whicheExecute update, insert into and delete SQL operation
 * @author Zeling.Wu
 * @version 15.06.2016
 */
public class SQLAmenderTest {

	SQLAmender sqlAmender;
	CleaningAgent cleaningAgent;
	int testID=67003;
	Tag tag;
	
	/**
	 * setUp cleaningAgent and tag for test use
	 */
	@Before
	public void setUp() throws Exception {
		sqlAmender = new SQLAmender();
		
		cleaningAgent=new CleaningAgent(testID);
		
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
		
		InternationalString string = new InternationalString();
		string.setString(LanguageType.ENGLISH, "testTag");
		
		tag = new Tag(50,string,TagType.ITEM,false);
	}

	/**
	 * test Writing Memo whether it writes correctly
	 */
	@Test
	public void testWriteMemo() {
		sqlAmender.createCleaningAgent(cleaningAgent);
		sqlAmender.writeMemo(testID, "test");
		assertEquals(getMemo(cleaningAgent.getCleaningAgentID()),"test");
		sqlAmender.removeCleaningAgent(cleaningAgent);	
	}
	
	/**
	 * test Writing Image check whether it is written
	 */
	@Test
	public void testWriteImage()
	{
		try{
		sqlAmender.createCleaningAgent(cleaningAgent);
		URL imageURL = new URL("https://upload.wikimedia.org/wikipedia/en/e/eb/SupermanRoss.png");
		Image goodImage = ImageIO.read(imageURL);
		byte[] temp=imageToStream(goodImage);
		sqlAmender.writeImage(testID,temp);
		assertTrue(imageExist(testID));
		sqlAmender.removeCleaningAgent(cleaningAgent);
		}catch(Exception e)
		{e.printStackTrace();}
	}
	
	/**
	 * test Creating Relation whether it exists
	 */
	@Test
	public void testCreateTCRelation(){
		sqlAmender.createTCRelation(10, 5);
		assertTrue(existTC(10,5));
		sqlAmender.removeTCRelation(10, 5);
	}
	
	/**
	 * test Removing Relation whether it succeeds removin
	 */
	@Test
	public void testRemoveTCRelation()
	{
		sqlAmender.createTCRelation(10, 5);
		sqlAmender.removeTCRelation(10, 5);
		assertFalse(existTC(10,5));
	}
	
	/**
	 * 	test Modifying Cleaning Agent
	 */
	@Test
	public void testModifyCleaningAgent()
	{
		sqlAmender.createCleaningAgent(cleaningAgent);
		sqlAmender.writeMemo(cleaningAgent.getCleaningAgentID(), "test");
		cleaningAgent.setMemo("test2");
		sqlAmender.modifyCleaningAgent(cleaningAgent);
		assertEquals(getMemo(cleaningAgent.getCleaningAgentID()),"test2");
		sqlAmender.removeCleaningAgent(cleaningAgent);
	}
	
	/**
	 * test Creating CleaningAgent with temporary value
	 */
	@Test
	public void testCreateCleaningAgent()
	{
		sqlAmender.createCleaningAgent(cleaningAgent);
		assertTrue(existCleaningAgent(cleaningAgent.getCleaningAgentID()));
		sqlAmender.removeCleaningAgent(cleaningAgent);	
	}
	
	/**
	 * test Removing by check whether it exists after removing
	 */
	@Test
	public void testRemoveCleaningAgent()
	{
		sqlAmender.createCleaningAgent(cleaningAgent);
		sqlAmender.removeCleaningAgent(cleaningAgent);
		assertFalse(existCleaningAgent(cleaningAgent.getCleaningAgentID()));
	}
	
	/**
	 * test by changing some data
	 */
	@Test
	public void testModifyTag()
	{
		sqlAmender.createTag(tag);
		
		InternationalString string = new InternationalString();
		string.setString(LanguageType.ENGLISH, "testTag2");
		tag.setName(string);
		sqlAmender.modifyTag(tag);
		assertEquals(getTagName(tag.getTagID()),"testTag2");
		sqlAmender.removeTag(tag);
	}
	
	/**
	 * test creating tag
	 */
	@Test
	public void testCreateTag()
	{
		sqlAmender.createTag(tag);
		assertNotNull(getTagName(tag.getTagID()));
		sqlAmender.removeTag(tag);
	}
	
	/**
	 * test removing tag
	 */
	@Test
	public void testRemoveTag()
	{
		sqlAmender.createTag(tag);
		sqlAmender.removeTag(tag);
		assertNull(getTagName(tag.getTagID()));
	}
	
	/**
	 * a tool used to provide help for Memo related test
	 * @param cleaningAgentID
	 * @return
	 */
	public static String getMemo(int cleaningAgentID)
	{
		ResultSet rs=Connector.executeSelect("select Memo from CleaningAgents where cleaningAgentID="+cleaningAgentID);
		String temp=null;
		try {
			temp = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	/**
	 * check whether the relation exists
	 * @param cleaningAgentID
	 * @param tagID
	 * @return
	 */
	private static boolean existTC(int cleaningAgentID, int tagID)
	{
		ResultSet rs=Connector.executeSelect("select * from TC where cleaningAgentID="+cleaningAgentID+" and tagID="+tagID);
		try {
			if(rs.next())
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * check whether exists cleaningAgent
	 * @param cleaningAgentID
	 * @return
	 */
	private static boolean existCleaningAgent(int cleaningAgentID)
	{
		ResultSet rs=Connector.executeSelect("select * from CleaningAgents where cleaningAgentID="+cleaningAgentID);
		try {
			if(rs.next())
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * convert image to Stream to put it into database
	 * @param goodImage
	 * @return
	 */
	private static byte[] imageToStream(Image goodImage)
	{	
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		int w = goodImage.getWidth(null);
		int h = goodImage.getHeight(null);
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage out = new BufferedImage(w,h,type);
		Graphics2D g2=out.createGraphics();
		g2.drawImage(goodImage,0,0,null);
		g2.dispose();
		try {
			ImageIO.write(out, "jpg", bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
	
	/**
	 * check whether the image Exists 
	 * @param cleaningAgentID
	 * @return
	 */
	private static boolean imageExist(int cleaningAgentID)
	{
		ResultSet rs=Connector.executeSelect("select count(*) from CleaningAgents where cleaningAgentID="+cleaningAgentID+" and image!=NULL");
		try {
			if(rs.next())
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * a tool help to get TagName 
	 * @param tagID
	 * @return
	 */
	private static String getTagName(int tagID)
	{
		String temp=null;
		ResultSet rs=Connector.executeSelect("select * from Tags where tagID="+tagID);
		try {
			if(rs.next())
			{
				temp = rs.getString("nameEn");	
			}
			else{return null;}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
}

