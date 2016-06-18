import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GOOD_INFO {

	public static void main(String[] args) {
		
		WebDriver driver = new FirefoxDriver();
		//prepare count how many possible tags
		List<String> tagVec = new Vector<String>();
		tagVec.add("³ø·¿");
		File file = new File("Tags.txt");
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//database prepare
		Connection c = null;
		Statement stmt = null;
		int rowNum =0;
		//change
		int currentRowNum =2;
		ResultSet rsSelect=null;
		try {
			Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:test.db");
		    c.setAutoCommit(false);
		    stmt = c.createStatement();
		    //count row
		    String sqlgetNum="SELECT COUNT(*) FROM goodsURL";
		    rsSelect=stmt.executeQuery(sqlgetNum);
		    c.commit();
		    rowNum =rsSelect.getInt("COUNT(*)");
		}catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
		//start getting URL
		while(rowNum!=0)
		{
			try {
				String sqlgetURL = "SELECT * FROM goodsURL where goodID="+currentRowNum;
				ResultSet rsURL=stmt.executeQuery(sqlgetURL);
				String goodURL = rsURL.getString("goodURL");
				
				//get the first data
				driver.get(goodURL);
				Thread.sleep(7000);
				String goodName= driver.findElement(By.xpath("//*[@id=\"parameter2\"]/li[1]")).getText();
				System.out.print(goodName+" ");
				
				String goodDescription = driver.findElement(By.xpath("//*[@id=\"parameter2\"]/li[3]")).getText()+" ";
				goodDescription += driver.findElement(By.xpath("//*[@id=\"parameter2\"]/li[4]")).getText()+" ";
				String productAppPlace = driver.findElement(By.xpath("//*[@id=\"parameter2\"]/li[5]")).getText();
				goodDescription += productAppPlace;
				System.out.println(goodDescription);
				
				for(int i =0;i<tagVec.size();i++)
				{
					if(tagVec.get(i).equals(productAppPlace))
					{
						break;
					}
					if(i==tagVec.size()-1)
					{
						tagVec.add(productAppPlace);
						FileWriter fileWritter = new FileWriter(file.getName(),true);
			            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			            bufferWritter.write(productAppPlace);
			            bufferWritter.close();
					}	
				}

				URL goodImageURL = new URL(driver.findElement(By.xpath("//*[@id=\"spec-n1\"]/img")).getAttribute("src"));
				Image goodImage = ImageIO.read(goodImageURL);
				System.out.println(goodImage);
				
				//write data into table CleaningAgent
				String sqlInsertID = "INSERT INTO CleaningAgent (cleaningAgentID) VALUES ("+currentRowNum+");";
				stmt.executeUpdate(sqlInsertID);
				
				String sqlInsertName = "UPDATE CleaningAgent set nameCn ="+goodName+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertName);
				
				String sqlInsertDes = "UPDATE CleaningAgent set descriptionCn ="+goodDescription+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertDes);
				
				String sqlInsertAppTime = "UPDATE CleaningAgent set applicationTime ="+(int)(1+Math.random()*(10-1+1))+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertAppTime);
				
				String sqlInsertFre = "UPDATE CleaningAgent set frequency ="+(int)(1+Math.random()*(10-1+1))+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertFre);
				
				String sqlInsertLanguage = "UPDATE CleaningAgent set mainLanguage ="+2+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertLanguage);
				c.commit();
				
				
				//delete useless data
//				String sqldeleteURL = "DELETE from goodsURL where goodID="+currentRowNum;
//				stmt.executeUpdate(sqldeleteURL);
//				c.commit();
				//end
				rowNum--;
				System.out.println("there is "+rowNum+" left");
				currentRowNum++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
