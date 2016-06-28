import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GOOD_URL_Amazon {
	
    public static void main(String[] args) {
        
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.amazon.de/s/ref=nb_sb_ss_i_1_16?__mk_de_DE=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Dkitchen&field-keywords=reinigungsmittel&sprefix=Reinigungsmittel%2Ckitchen%2C224");
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        WebElement nextPage = driver.findElement(By.xpath("//*[@id=\"pagnNextLink\"]"));
        
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
	  
		//climb prepare
		int prePageNum =0;
		int page =1;
        while(page!=100)
        {
        	//In case of load page fail, still remain in the same page 
        	WebElement goodsPage = driver.findElement(By.cssSelector("#pagn > span.pagnCur"));
			page = Integer.parseInt(goodsPage.getText());
			while(prePageNum==page)
			{
				nextPage.click();
				goodsPage = driver.findElement(By.cssSelector("#pagn > span.pagnCur"));
				page = Integer.parseInt(goodsPage.getText());
				System.out.println("load Page fail, please operate manually");
			}
			prePageNum=page;
			//page climbing
        	for(int i=1;i<21;i++)
        	{
        		try{
        			//get URL and pagenum
        			WebElement goodLink = driver.findElement(By.xpath("//*[@id=\"s-results-list-atf\"]/li["+i+"]/div/div[2]/div/div/a"));
        			String goodURL = goodLink.getAttribute("href").toString();

        			//because of the page is loaded dynamically so we have to scroll down 
        			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", goodLink);
        			Thread.sleep(500);
        			
      		      	//get the last row num
      			    String sqlgetNum="SELECT COUNT(*) FROM AmaURL";
      			    ResultSet rsSelect=stmt.executeQuery(sqlgetNum);
      			    c.commit();
      			    int lastRecord = rsSelect.getInt("COUNT(*)")+1;
      			    System.out.println();
      		      	
      			    //Insert URL into goodsURL
      			    String sqlInsert = "INSERT INTO AmaURL (goodID,goodURL,goodPage)"+
      			    					"VALUES ("+lastRecord+",'"+goodURL+"',"+page+")";
      			    stmt.executeUpdate(sqlInsert);

      			    System.out.println("It is the "+i+" in "+page+" page and the "+lastRecord+" good: "+goodURL);
        		}catch(Exception e)
        		{
        			if(i!=12)
        			{
        				e.printStackTrace();
        			}
        		}
        	}
        	nextPage = driver.findElement(By.xpath("//*[@id=\"pagnNextLink\"]"));
        	nextPage.click();
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
