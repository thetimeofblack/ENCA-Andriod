import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GOOD_INFO_Amazon {

	public static void main(String[] args) {
		
		WebDriver driver = new FirefoxDriver();
		
		//database prepare
		Connection c = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		//control variables
		int rowNum =0;
		int currentRowNum =1;
		
		ResultSet rsSelect=null;
		try {
			Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:test.db");
		    c.setAutoCommit(false);
		    stmt = c.createStatement();
		    //count row
		    String sqlgetNum="SELECT COUNT(*) FROM AmaURL";
		    rsSelect=stmt.executeQuery(sqlgetNum);
		    c.commit();
		    rowNum =rsSelect.getInt("COUNT(*)");
		    //get downloaded num
		    String sqlgetdnNum = "SELECT COUNT(*) FROM AmaCleaningAgent";
		    rsSelect=stmt.executeQuery(sqlgetdnNum);
		    c.commit();
		    currentRowNum+=rsSelect.getInt("COUNT(*)");
		    rowNum=rowNum-currentRowNum+1;
		}catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
		//start getting URL
		while(rowNum!=0)
		{
			try {
				String sqlgetURL = "SELECT * FROM AmaURL where goodID="+currentRowNum;
				ResultSet rsURL=stmt.executeQuery(sqlgetURL);
				String goodURL = rsURL.getString("goodURL");
				
				//get the first data
				driver.get(goodURL);
				Thread.sleep(15000);
				String goodName= driver.findElement(By.xpath("//*[@id=\"productTitle\"]")).getText();
				goodName = replaceSplite(goodName);
				System.out.print(goodName+" ");
				
				String goodDescription=null;
				try{
					List<WebElement> goodDesListEle = driver.findElements(By.xpath("//*[@id=\"prodDetails\"]/div[2]/div[1]/div/div[2]/div/div/table/tbody/tr"));
			        for(int i=0;i<(goodDesListEle.size()-1);i++)
			        {
			        	String goodLabel = goodDesListEle.get(i).findElement(By.xpath("//*[@id=\"prodDetails\"]/div[2]/div[1]/div/div[2]/div/div/table/tbody/tr["+(i+1)+"]/td[1]")).getText();
			        	String goodLabelVal = goodDesListEle.get(i).findElement(By.xpath("//*[@id=\"prodDetails\"]/div[2]/div[1]/div/div[2]/div/div/table/tbody/tr["+(i+1)+"]/td[2]")).getText();
			        	goodDescription += goodLabel+": "+goodLabelVal+" ";
			        }
				}catch(Exception e)
				{
					System.out.println("Skip some lines");
				}
				goodDescription = replaceSplite(goodDescription);
				System.out.println(goodDescription);
				
				//get good picture
				URL goodImageURL = new URL(driver.findElement(By.cssSelector("#landingImage")).getAttribute("src"));
				Image goodImage = ImageIO.read(goodImageURL);
				
				//write data into table CleaningAgent
				String sqlInsertID = "INSERT INTO AmaCleaningAgent (cleaningAgentID) VALUES ("+currentRowNum+");";
				stmt.executeUpdate(sqlInsertID);
				
				String sqlInsertName = "UPDATE AmaCleaningAgent set nameDe ='"+goodName+"' where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertName);
				
				String sqlInsertDes = "UPDATE AmaCleaningAgent set descriptionDe ='"+goodDescription+"' where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertDes);
				
				String sqlInsertAppTime = "UPDATE AmaCleaningAgent set applicationTime ="+(int)(1+Math.random()*(10-1+1))+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertAppTime);
				
				String sqlInsertFre = "UPDATE AmaCleaningAgent set frequency ="+(int)(1+Math.random()*(10-1+1))+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertFre);
				
				String sqlInsertRate = "UPDATE AmaCleaningAgent set rate ="+(int)(1+Math.random()*(10-1+1))+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertRate);
				
				String sqlInsertType = "UPDATE AmaCleaningAgent set cleaningAgentType = 'System' where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertType);
				
				String sqlInsertLanguage = "UPDATE AmaCleaningAgent set mainLanguage ="+2+" where cleaningAgentID="+currentRowNum+";";
				stmt.executeUpdate(sqlInsertLanguage);
				
				
				
				//write picture into table
				String sqlInsertImage = "UPDATE AmaCleaningAgent set image=? where cleaningAgentID="+currentRowNum+";";
				pstmt = c.prepareStatement(sqlInsertImage);
				byte[] temp=imageToStream(goodImage);
				pstmt.setBytes(1, temp);
				pstmt.execute();
				
				c.commit();
				
				
				//delete useless data
//				String sqldeleteURL = "DELETE from goodsURL where goodID="+currentRowNum;
//				stmt.executeUpdate(sqldeleteURL);
//				c.commit();
				//end
			} catch (NoSuchElementException e) {
				String sqlInsertID = "INSERT INTO AmaCleaningAgent (cleaningAgentID) VALUES ("+currentRowNum+");";
				try {
					stmt.executeUpdate(sqlInsertID);
					String sqlInsertName = "UPDATE AmaCleaningAgent set nameCn ='²âÊÔ' where cleaningAgentID="+currentRowNum+";";
					stmt.executeUpdate(sqlInsertName);
					String sqlInsertDes = "UPDATE AmaCleaningAgent set descriptionCn ='²âÊÔ' where cleaningAgentID="+currentRowNum+";";
					stmt.executeUpdate(sqlInsertDes);
					c.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				rowNum--;
				System.out.println("there is "+rowNum+" left");
				currentRowNum++;
			}
		}
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
	
	private static String replaceSplite(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("'");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }
}
