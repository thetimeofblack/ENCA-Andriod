import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AmazonTest {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:sqlite:test.db";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver = new FirefoxDriver();
        driver.get("https://www.amazon.de/Ha-Ra-Vollpflege-Konzentrat-500-ml/dp/B002Q55EYY/ref=sr_1_1/277-6832607-7254709?s=kitchen&ie=UTF8&qid=1466744977&sr=1-1&keywords=reinigungsmittel");
        try {
			Thread.sleep(9000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        WebElement goodNameEle = driver.findElement(By.xpath("//*[@id=\"productTitle\"]"));
        String goodName = goodNameEle.getText();
        System.out.println(goodName);
        
        List<WebElement> goodDesListEle = driver.findElements(By.xpath("//*[@id=\"prodDetails\"]/div[2]/div[1]/div/div[2]/div/div/table/tbody/tr"));
        System.out.println(driver.findElement(By.cssSelector("#landingImage")).getAttribute("src"));
        System.out.print("There existts goods properties"+goodDesListEle.size());
        for(int i=0;i<(goodDesListEle.size()-1);i++)
        {
        	String goodLabel = goodDesListEle.get(i).findElement(By.xpath("//*[@id=\"prodDetails\"]/div[2]/div[1]/div/div[2]/div/div/table/tbody/tr["+(i+1)+"]/td[1]")).getText();
        	String goodLabelVal = goodDesListEle.get(i).findElement(By.xpath("//*[@id=\"prodDetails\"]/div[2]/div[1]/div/div[2]/div/div/table/tbody/tr["+(i+1)+"]/td[2]")).getText();
        	System.out.println(goodLabel+": ");
        	System.out.println(goodLabelVal+" ");
        	
        }
        System.out.println();
	}

}
