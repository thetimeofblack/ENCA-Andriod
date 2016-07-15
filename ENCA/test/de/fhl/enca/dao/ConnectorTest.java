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
/**
 * Class which responsible for connecting the database and sending SQL query to database
 * @author Zeling Wu
 * @version 15.07.2016
 */
public class ConnectorTest {
	
	/**
	 * test by trying out a selection
	 */
	@Test
	public void testExecuteSelect() {
		ResultSet rs=Connector.executeSelect("select COUNT(*) from TC where cleaningAgentID=1");
		assertNotNull(rs);
	}
	
	/**
	 * test by trying out a insert
	 */
	@Test
	public void testexecuteNonSelect(){
		Connector.executeNonSelect("insert into TC values ( 1000,1000)");
		ResultSet rs=Connector.executeSelect("select COUNT(*) from TC where cleaningAgentID=1000 and tagID=1000");
		try {
			assertEquals(rs.getInt(1),1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connector.executeNonSelect("delete from TC where cleaningAgentID=1000 and tagID=1000");
	}
	
	/**
	 * test by trying on a Image
	 */
	@Test
	public void testexecuteImage()
	{
		SQLAmender sqlAmender = new SQLAmender();
		
		CleaningAgent cleaningAgent=new CleaningAgent(10000);
		
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
		
		sqlAmender.createCleaningAgent(cleaningAgent);
		
		try{
			URL imageURL = new URL("https://upload.wikimedia.org/wikipedia/en/e/eb/SupermanRoss.png");
			Image goodImage = ImageIO.read(imageURL);
			byte[] temp=imageToStream(goodImage);
			Connector.executeImage("update CleaningAgents set image=? where cleaningAgentID="+10000, temp);
			assertTrue(imageExist(10000));
		}catch(Exception e)
			{e.printStackTrace();}
		
		sqlAmender.removeCleaningAgent(cleaningAgent);
	}
	
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
	
	private static boolean imageExist(int cleaningAgentID)
	{
		ResultSet rs=Connector.executeSelect("select count(*) from CleaningAgents where cleaningAgentID="+cleaningAgentID+" and image!=NULL");
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
}
