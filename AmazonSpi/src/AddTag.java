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
		vecString1.add("����");
		vecString1.add("��");
		vecString1.add("����");
		vecString1.add("ϴ���");
		vec.add(vecString1);
		
		Vector<String> vecString2 = new Vector<String>();
		vecString2.add("ԡ��");
		vecString2.add("ԡ");
		vecString2.add("��");
		vecString2.add("����");
		vecString2.add("ϴ�»�");
		vec.add(vecString2);
		
		Vector<String> vecString3 = new Vector<String>();
		vecString3.add("��");
		vecString3.add("�յ�");
		vecString3.add("��");
		vecString3.add("����");
		vec.add(vecString3);

		Vector<String> vecString4 = new Vector<String>();
		vecString4.add("��");
		vecString4.add("�յ�");
		vecString4.add("��");
		vecString4.add("����");
		vec.add(vecString4);
		
		Vector<String> vecString5 = new Vector<String>();
		vecString5.add("����");
		vecString5.add("�յ�");
		vecString5.add("��");
		vecString5.add("����");
		vec.add(vecString5);
		
		Vector<String> vecString6 = new Vector<String>();
		vecString6.add("��");
		vecString6.add("���");
		vecString6.add("��");
		vec.add(vecString6);
		
		Vector<String> vecString7 = new Vector<String>();
		vecString7.add("����");
		vec.add(vecString7);
		
		Vector<String> vecString8 = new Vector<String>();
		vecString8.add("�Ҿ�");
		vecString8.add("ϴ���");
		vec.add(vecString8);
		
		Vector<String> vecString9 = new Vector<String>();
		vecString9.add("��");
		vecString9.add("��");
		vec.add(vecString9);
		
		Vector<String> vecString10 = new Vector<String>();
		vecString10.add("ԡ");
		vecString10.add("��");
		vec.add(vecString10);
		
		Vector<String> vecString11 = new Vector<String>();
		vecString11.add("��");
		vec.add(vecString11);
		
		Vector<String> vecString12 = new Vector<String>();
		vecString12.add("��");
		vecString12.add("��");
		vec.add(vecString12);
		
		Vector<String> vecString13 = new Vector<String>();
		vecString13.add("����");
		vec.add(vecString13);
		
		Vector<String> vecString14 = new Vector<String>();
		vecString14.add("����");
		vec.add(vecString14);
		
		Vector<String> vecString15 = new Vector<String>();
		vecString15.add("��");
		vec.add(vecString15);
		
		Vector<String> vecString16 = new Vector<String>();
		vecString16.add("����");
		vec.add(vecString16);
		
		Vector<String> vecString17 = new Vector<String>();
		vecString17.add("��");
		vecString17.add("��");
		vec.add(vecString17);
		
		Vector<String> vecString18 = new Vector<String>();
		vecString18.add("ԡ");
		vec.add(vecString18);
		
		Vector<String> vecString19 = new Vector<String>();
		vecString19.add("ϴ�»�");
		vec.add(vecString19);
		
		Vector<String> vecString20 = new Vector<String>();
		vecString20.add("ԡ");
		vec.add(vecString20);
		
		Vector<String> vecString21 = new Vector<String>();
		vecString21.add("����");
		vec.add(vecString21);
		
		Vector<String> vecString22 = new Vector<String>();
		vecString22.add("ľ");
		vec.add(vecString22);
		
		Vector<String> vecString23 = new Vector<String>();
		vecString23.add("����");
		vec.add(vecString23);
		
		
		Vector<String> vecString24 = new Vector<String>();
		vecString24.add("Ƥ");
		vec.add(vecString24);
		
		Vector<String> vecString25 = new Vector<String>();
		vecString25.add("����");
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
	      		String nameCnSQL="SELECT nameCn,descriptionCn FROM CleaningAgents where cleaningAgentID="+i;
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
