import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class GOOD_TRANS_SUPP {

	public static void main(String[] args) {
		
		final int langTranFrom =2;
		String lanF=null;
		if(langTranFrom ==0)
		{lanF="En";}
		else if (langTranFrom==1)
		{lanF="Cn";}
		else
		{lanF="De";}
		
		final int langToTran =1;
		String lanT = null;
		if(langToTran ==0)
		{lanT="En";}
		else if (langToTran==1)
		{lanT="Cn";}
		else
		{lanT="De";}
		
		//web setting
		WebDriver driver=new FirefoxDriver();
		driver.get("https://translate.google.cn/");
		driver.manage().window().maximize();
		
		try {
			//select language to translate manually
			Thread.sleep(10000);
			
			//database setting
			Class.forName("org.sqlite.JDBC");
		    Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
		    c.setAutoCommit(false);
		    Statement stmt = c.createStatement();
		    
		    String sqlgetToTransNum = "SELECT COUNT(*) FROM AmaCleaningAgent";
		    ResultSet rsSelect=stmt.executeQuery(sqlgetToTransNum);
		    c.commit();
		    int totalNum = rsSelect.getInt("COUNT(*)");
		    
		    String sqlgetHaveTran = "SELECT COUNT(*) FROM AmaCleaningAgent WHERE name"+lanT+" IS NOT NULL";
		    rsSelect=stmt.executeQuery(sqlgetHaveTran);
		    c.commit();
		    int TranedNum = rsSelect.getInt("COUNT(*)");
		    
		    int leftNum = totalNum-TranedNum;
		    System.out.println("there is "+leftNum+" left");
			while(leftNum!=0)
			{
				String sqlgetCurrentTranData = "SELECT name"+lanF+",description"+lanF+" FROM AmaCleaningAgent WHERE cleaningAgentID ="+(TranedNum+1);
				ResultSet reToTran = stmt.executeQuery(sqlgetCurrentTranData);
				c.commit();
				String TranName=reToTran.getString("name"+lanF);
				String TranDes =reToTran.getString("description"+lanF);
				for(int i=0;i<2;i++)
				{
					String TranNeededStr=null;
					if(i==0)
					{ TranNeededStr = TranName;}
					else
					{ TranNeededStr = TranDes;}
					
					//tranlate process
					WebElement query = driver.findElement(By.id("source"));
				    query.sendKeys(TranNeededStr);
				    
				    WebElement query1 = driver.findElement(By.id("gt-submit"));
			        query1.click();
			        
			        WebElement result = driver.findElement(By.id("result_box"));
			        String resultStr = result.getText();
			        resultStr=replaceSplite(resultStr);
			        System.out.println(resultStr);
			        
			        query.clear();
			        
			        //update the database
			        String sqlUpdate = null;
			        if(i==0)
			        {sqlUpdate = "UPDATE AmaCleaningAgent SET name"+lanT+"='"+resultStr+"' WHERE cleaningAgentID ="+(TranedNum+1);}
			        else
			        {sqlUpdate = "UPDATE AmaCleaningAgent SET description"+lanT+"='"+resultStr+"' WHERE cleaningAgentID ="+(TranedNum+1);}
			        stmt.executeUpdate(sqlUpdate);
			        c.commit();
				}
	        
	        leftNum--;
	        TranedNum++;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
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
