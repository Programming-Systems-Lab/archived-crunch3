package psl.crunch3.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static void main(String[] args){
		
		Statement stmt = null;
		 ResultSet rs = null;
		 String username = "suhit";
		 String firstName = "suhit";
		 String lastName = "gupta";
		 String email = "suhit@aol.com";
		 String password = "hilaiscool";

		 try {
		
			 
			 //Register the JDBC driver for MySQL.
			System.out.println("starting");
			 //Class.forName("com.mysql.jdbc.Driver");
		     Class.forName("org.gjt.mm.mysql.Driver");
			 
		     System.out.println("point 1");
			 String url = "jdbc:mysql://localhost:3306/crunch";

			 System.out.println("point 2");

	            Connection conn = DriverManager.getConnection(url, "admin", "test"); 
	            stmt = conn.createStatement();
	            System.out.println("point 3");
	            String str = ("insert into user values ('" + username + "' , '"+
	            		firstName + "' , '"+ lastName + "' , '"+ email + "' , '"+
	            		password + "')");
	            System.out.println(str);
	            stmt.execute(str);
	            System.out.println("point 4");
	            
	            
	            File fromFile = new File("config" + File.separator +  "level6.ini");
				 File toFile = new File("users" + File.separator + "test" + ".ini");

				 FileInputStream from = new FileInputStream(fromFile);
				 FileOutputStream to = new FileOutputStream(toFile);
				 
				 byte[] buff = new byte[4096];
				 int i;
				 
				 while ((i = from.read(buff))!=-1){
					 to.write(buff, 0,i);
				 }
				 from.close();
				 to.close();
	            

	     } 
		 catch (Exception ex) {
			
			 	System.out.println(ex.getMessage());
	            ex.getStackTrace();
	     }
		 finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed

			    if (rs != null) {
			        try {
			            rs.close();
			        } catch (SQLException sqlEx) { // ignore }

			        rs = null;
			    }

			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { // ignore }

			        stmt = null;
			    }
			}
		  }
		}
		
	}
	
	public void createUserPrefFile(){
		 
		 try{
			 File fromFile = new File("config" + File.separator +  "level6.ini");
			 File toFile = new File("users" + File.separator + "test" + ".ini");

			 FileInputStream from = new FileInputStream(fromFile);
			 FileOutputStream to = new FileOutputStream(toFile);
			 
			 byte[] buff = new byte[4096];
			 int i;
			 
			 while ((i = from.read(buff))!=-1){
				 to.write(buff, 0,i);
			 }
			 from.close();
			 to.close();
			 //return true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	}
		 
	
}
