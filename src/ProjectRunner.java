import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;


public class ProjectRunner {

	
	public static void main(String[] args) {
		Connection con = null;
		try {
			
			try {
				Class.forName("org.hsqldb.jdbcDriver");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			
            //con=DriverManager.getConnection("jdbc:hsqldb:file:testdb","SA","");
			Properties p = new Properties();
			p.put("hsqldb.result_max_memory_rows", 5000);
			p.put("hsqldb.default_table_type", "memory");
			
			con=DriverManager.getConnection("jdbc:hsqldb:file:testdb;close_result=true;hsqldb.result_max_memory_rows=5000;shutdown=true", p);
			//con=DriverManager.getConnection("jdbc:hsqldb:mem:mohsin","SA","");
            con.createStatement().executeUpdate("create TABLE Key (key varchar(50),value varchar(50))");
            
            //con.createStatement().executeUpdate("DECLARE LOCAL TEMPORARY TABLE Key (key varchar(50),value varchar(50)");
            
           
            
            for (int j = 0 ; j < 1000000; j++) {
            	insert(con);
            	
            	System.out.println(j);
            }
            
            
            
            for (int j = 0 ; j < 1000000; j++) {
            	insert(con);
            	System.out.println(j);
            }
            
            System.out.println("Here");
            
            PreparedStatement pst1=con.prepareStatement("select key , count(key) from Key GROUP BY key");
            pst1.clearParameters();
            ResultSet rs=pst1.executeQuery();
            while(rs.next()){
               //System.out.println(rs.getString(1) + "\t" + rs.getString(2));  
            	System.out.println(rs.getString(1) + "\t" +rs.getString(2));  
            }
            
            con.createStatement().executeUpdate("DELETE FROM Key");
            
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

	}
	
	static void insert(Connection con) {
		try {
			 PreparedStatement pst2=con.prepareStatement("insert into Key values(?,?)");
	         pst2.clearParameters();
	         pst2.setString(1, "hahah");
	         pst2.setString(2, "ali");
	         
	         pst2.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
