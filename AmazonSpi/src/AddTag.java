import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class AddTag {
	static Vector<Vector> vec = new Vector<Vector>();
	
	public static void main(String[] args) {
		//initialize the key words
		Vector<String> vecString1 = new Vector<String>();
		vecString1.add("³ø·¿");
		vecString1.add("µØ");
		vecString1.add("²£Á§");
		vecString1.add("Ï´Íë»ú");
		vecString1.add("Ïû¶¾");
		vec.add(vecString1);
		
		Vector<String> vecString2 = new Vector<String>();
		vecString2.add("Ô¡ÊÒ");
		vecString2.add("Ô¡");
		vecString2.add("µØ");
		vecString2.add("²£Á§");
		vecString2.add("Ï´ÒÂ»ú");
		vecString2.add("Ïû¶¾");
		vec.add(vecString2);
		
		Vector<String> vecString3 = new Vector<String>();
		vecString3.add("´²");
		vecString3.add("¿Õµ÷");
		vecString3.add("µØ");
		vecString3.add("²£Á§");
		vecString3.add("Ïû¶¾");
		vec.add(vecString3);

		Vector<String> vecString4 = new Vector<String>();
		vecString4.add("×À");
		vecString4.add("¿Õµ÷");
		vecString4.add("µØ");
		vecString4.add("²£Á§");
		vecString4.add("Ïû¶¾");
		vec.add(vecString4);
		
		Vector<String> vecString5 = new Vector<String>();
		vecString5.add("¿ÍÌü");
		vecString5.add("¿Õµ÷");
		vecString5.add("µØ");
		vecString5.add("²£Á§");
		vecString5.add("Ïû¶¾");
		vec.add(vecString5);
		
		Vector<String> vecString6 = new Vector<String>();
		vecString6.add("³ô");
		vecString6.add("Çå½à");
		vecString6.add("¶¾");
		vec.add(vecString6);
		
		Vector<String> vecString7 = new Vector<String>();
		vecString7.add("²£Á§");
		vecString7.add("¶¾");
		vec.add(vecString7);
		
		Vector<String> vecString8 = new Vector<String>();
		vecString8.add("¼Ò¾ß");
		vecString8.add("Ï´Íë»ú");
		vecString8.add("¶¾");
		vec.add(vecString8);
		
		Vector<String> vecString9 = new Vector<String>();
		vecString9.add("¼Ò¾ß");
		vecString9.add("Ôî");
		vecString9.add("¿¾");
		vecString9.add("¶¾");
		vec.add(vecString9);
		
		Vector<String> vecString10 = new Vector<String>();
		vecString10.add("Ô¡");
		vecString10.add("Åè");
		vecString10.add("¶¾");
		vec.add(vecString10);
		
		Vector<String> vecString11 = new Vector<String>();
		vecString11.add("µØ");
		vecString11.add("¶¾");
		vec.add(vecString11);
		
		Vector<String> vecString12 = new Vector<String>();
		vecString12.add("µØ");
		vecString12.add("´É");
		vecString12.add("¶¾");
		vec.add(vecString12);
		
		Vector<String> vecString13 = new Vector<String>();
		vecString13.add("¼Ò¾ß");
		vecString13.add("±ùÏä");
		vecString13.add("¶¾");
		vec.add(vecString13);
		
		Vector<String> vecString14 = new Vector<String>();
		vecString14.add("²£Á§");
		vecString14.add("¼Ò¾ß");
		vecString14.add("¶¾");
		vec.add(vecString14);
		
		Vector<String> vecString15 = new Vector<String>();
		vecString15.add("¼Ò¾ß");
		vecString15.add("×À");
		vecString15.add("¶¾");
		vec.add(vecString15);
		
		Vector<String> vecString16 = new Vector<String>();
		vecString16.add("¼Ò¾ß");
		vecString16.add("·çÉÈ");
		vecString16.add("¶¾");
		vec.add(vecString16);
		
		Vector<String> vecString17 = new Vector<String>();
		vecString17.add("²Þ");
		vecString17.add("´É");
		vecString17.add("¼Ò¾ß");
		vecString17.add("¶¾");
		vec.add(vecString17);
		
		Vector<String> vecString18 = new Vector<String>();
		vecString18.add("Ô¡");
		vecString18.add("¼Ò¾ß");
		vecString18.add("¶¾");
		vec.add(vecString18);
		
		Vector<String> vecString19 = new Vector<String>();
		vecString19.add("Ï´ÒÂ»ú");
		vecString19.add("¶¾");
		vec.add(vecString19);
		
		Vector<String> vecString20 = new Vector<String>();
		vecString20.add("Ô¡");
		vecString20.add("¼Ò¾ß");
		vecString20.add("¶¾");
		vec.add(vecString20);
		
		Vector<String> vecString21 = new Vector<String>();
		vecString21.add("²£Á§");
		vecString21.add("¶¾");
		vec.add(vecString21);
		
		Vector<String> vecString22 = new Vector<String>();
		vecString22.add("¼Ò¾ß");
		vecString22.add("Ä¾");
		vecString22.add("¶¾");
		vec.add(vecString22);
		
		Vector<String> vecString23 = new Vector<String>();
		vecString23.add("²£Á§");
		vecString23.add("¶¾");
		vec.add(vecString23);
		
		
		Vector<String> vecString24 = new Vector<String>();
		vecString24.add("¼Ò¾ß");
		vecString24.add("Æ¤");
		vecString24.add("¶¾");
		vec.add(vecString24);
		
		Vector<String> vecString25 = new Vector<String>();
		vecString25.add("²£Á§");
		vecString25.add("¶¾");
		vec.add(vecString25);
		
		//get String from database
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
	      		String nameCnSQL="SELECT nameCn,descriptionCn FROM testtable where cleaningAgentID="+i;
	      		ResultSet rsSelect=stmt.executeQuery(nameCnSQL);
	      		c.commit();
  			    String nameCn = rsSelect.getString("nameCn");
  			    String descriptionCn = rsSelect.getString("descriptionCn");
  			    String searchString = nameCn+descriptionCn;
  			    
  			    Vector<Integer> getTagsVec=addtag(searchString);
  			    
  			    for(Integer tagID:getTagsVec)
  			    {
  			    	String insertTagRel = "INSERT INTO TC(cleaningAgentID,tagID) VALUES ("+i+","+(tagID+1)+")";
  			    	stmt.executeUpdate(insertTagRel);
  			    	c.commit();
  			    }
  			    
	      	}
	      	//
			//ResultSet rsSelect=stmt.executeQuery(sqlgetNum);
	      	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("End");
	}
	
	private static Vector<Integer> addtag(String temp)
	{
		Vector<Integer> containTagVec= new Vector<Integer>();
		for(Vector vecTemp:vec)
		{
			for(Object tag:vecTemp)
			{	
				if(temp.indexOf((String)tag)!=-1)
				{
					containTagVec.add(vec.indexOf(vecTemp));
					break;
				}
			}
		}
		return containTagVec;
	}
}
