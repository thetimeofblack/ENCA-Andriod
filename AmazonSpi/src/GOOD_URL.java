

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.sql.*;

public class GOOD_URL {
	
    public static void main(String[] args) {
        
        WebDriver driver = new FirefoxDriver();
        driver.get("http://search.jd.com/search?keyword=%E6%B8%85%E6%B4%81%E6%B6%B2&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&cid3=1663#keyword=%E6%B8%85%E6%B4%81%E6%B6%B2&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&sttr=1&cid2=1625&cid3=1663&page=1&s=1&click=0");
        WebElement nextPage = driver.findElement(By.xpath("//*[@id=\"J_bottomPage\"]/span[1]/a[9]"));
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
      //database prepare
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      	c.setAutoCommit(false);
	      	System.out.println("Opened database successfully");
	      	stmt = c.createStatement();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  
        while(!(nextPage.getAttribute("class").toString().equals("pn-next disabled")))
        {
        	for(int i=1;i<60;i++)
        	{
        		try{
        			//get URL and pagenum
        			WebElement goodLink = driver.findElement(By.xpath("//*[@id=\"J_goodsList\"]/ul/li["+i+"]/div/div[4]/a"));
        			String goodURL = goodLink.getAttribute("href").toString();
        			WebElement goodsPage = driver.findElement(By.xpath("//*[@id=\"J_topPage\"]/span/b"));
        			int page = Integer.parseInt(goodsPage.getText());
        			//because of the page is loaded dynamically so we have to scroll down 
        			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", goodLink);
        			Thread.sleep(500);
        			
      		      	//get the last row num
      			    String sqlgetNum="SELECT COUNT(*) FROM goodsURL";
      			    ResultSet rsSelect=stmt.executeQuery(sqlgetNum);
      			    c.commit();
      			    int lastRecord = rsSelect.getInt("COUNT(*)")+1;
      			    System.out.println();
      		      	
      			    //Insert URL into goodsURL
      			    String sqlInsert = "INSERT INTO goodsURL (goodID,goodURL,goodPage)"+
      			    					"VALUES ("+lastRecord+",'"+goodURL+"',"+page+")";
      			    stmt.executeUpdate(sqlInsert);

      			    System.out.println("It is the "+i+" in this page and the "+lastRecord+" good: "+goodURL);
        		}catch(Exception e)
        		{
        			if(i!=12)
        			{
        				e.printStackTrace();
        			}
        		}
        	}
        	nextPage = driver.findElement(By.xpath("//*[@id=\"J_bottomPage\"]/span[1]/a[9]"));
        	nextPage.click();
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	nextPage = driver.findElement(By.xpath("//*[@id=\"J_bottomPage\"]/span[1]/a[9]"));
        }
    }
}
	
//	public static void main(String[] args)
//	{
//		 Connection c = null;
//		    try {
//		      Class.forName("org.sqlite.JDBC");
//		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
//		      c.setAutoCommit(false);
//		    System.out.println("Opened database successfully");
//		    Statement stmt = c.createStatement();
//		    //insert row
//		    String sqlInsert = "INSERT INTO goodsURL (goodID,goodURL,goodPage)"+
//		    					"VALUES (2,'www.google.com',10)";
//		    stmt.executeUpdate(sqlInsert);
//		    
//		    //count row
//		    String sqlgetNum="SELECT COUNT(*) FROM goodsURL";
//		    ResultSet rsSelect=stmt.executeQuery(sqlgetNum);
//		    c.commit();
//		    System.out.println(rsSelect.getInt("COUNT(*)"));
//		    
//		    //delete row
//		    String sqlDelete = "DELETE from goodsURL where goodID=2";
//		    stmt.executeUpdate(sqlDelete);
//		    c.commit();
//		    
//		    } catch ( Exception e ) {
//			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//			    }
//	}
