import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dataProcess {

	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      	c.setAutoCommit(false);
	      	System.out.println("Opened database successfully");
	      	stmt = c.createStatement();
	      	
	      	
	      	for(int i=1;i<1263;i++)
	      	{
	      		String nameCnSQL="SELECT nameCn FROM testtable where cleaningAgentID="+i;
	      		ResultSet rsSelect=stmt.executeQuery(nameCnSQL);
	      		c.commit();
  			    String nameCn = rsSelect.getString("nameCn");
  			    System.out.println(nameCn.split("£º")[1]);
  			    
  			    String updatenameCnSQL = "UPDATE testtable SET nameCn='"+nameCn.split("£º")[1]+"' where cleaningAgentID="+i;
  			    stmt.executeUpdate(updatenameCnSQL);
	      	}
	      	//
			//ResultSet rsSelect=stmt.executeQuery(sqlgetNum);
	      	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
