package psl.crunch3.web;

import java.io.*;
import java.net.InetAddress;
import java.security.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;

public class RegisterBean implements Serializable{

	private String firstName, lastName, email;
	private String username, password;
	private String repeat, firstValid, lastValid, emailValid, userValid,passValid;
	private String inserted;
	private Connection conn;
	
	public RegisterBean(){
		connect();
	}
	
	private void connect(){
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			String url = "jdbc:mysql://localhost:3306/crunch";
			conn = DriverManager.getConnection(url, "admin", "test");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void setFirstName(String newName){
		firstName = newName;
	}
	
	public void setLastName(String newName){
		lastName = newName;
	}
	
	public void setEmail(String address){
		email = address;
	}
	
	public void setPassword(String newPass){
		password = newPass.trim();
	}
	
	public void setUsername(String name){
		username = name;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getRepeat(){
		return repeat;
	}
	
	public String getFirstValid(){
		return firstValid;
	}
	public String getLastValid(){
		return lastValid;
	}
	public String getEmailValid(){
		return emailValid;
	}
	public String getPassValid(){
		return passValid;
	}
	public String getUserValid(){
		return userValid;
	}
	public String getInserted(){
		return inserted;
	}
	
	
	/** form validation methods **/
	
	public boolean verifyPassword(String repeatPass){
		
		boolean b = password.equals(repeatPass.trim());
		repeat = Boolean.toString(!b);
		return b;
		
	}
	
	public boolean isFirstNameValid(){
	     if (firstName ==null) return false;
	     if (firstName != null){
	         if(firstName.equals(""))
	           return false;
	     }
	     return true;
	   }

	 public boolean isLastNameValid(){
	     if (lastName ==null) return false;
	     if (lastName != null){
	         if(lastName.equals(""))
	           return false;
	     }
	     return true;
	 }
	 
	 public boolean isPasswordValid(){
		 if (password ==null) return false;
	     if (password != null){
	         if(password.equals(""))
	           return false;
	     }
	     return true;
	 }
	 
	 public boolean isEmailValid(){
	     if (email ==null) return false;
	     if (email != null){
	         if(email.equals(""))
	           return false;
	         if (email.indexOf("@") == -1) return false; 
	     }
	     return true;

	   }
	 
	 public boolean isUsernameValid(){
	     if (username ==null) return false;
	     if (username != null){
	         if(username.equals(""))
	           return false;
	        
	     }
	     
	     if(exists(username)){
	    	 return false;
	     }
	     
	     return true;

	   }
	
	 public boolean isValid(){
		 
		 firstValid="true";
		 lastValid = "true";
		 emailValid="true"; 
		 userValid= "true";
		 passValid= "true";
		 
		 if(!isFirstNameValid()){
			 firstValid="false";
			 password = "";
			 return false;
		 }
		 if(!isLastNameValid()){
			 lastValid = "false";
			 password = "";
			 return false;
		 }
		 if(!isEmailValid()){
			 emailValid="false";
			 password = "";
			 return false;
		 }
		 
		 if(!isUsernameValid()){
			 userValid= "false";
			 password = "";
			 return false;
		 }
		 if(!isPasswordValid()){
			 passValid= "false";
			 password = "";
			 return false;
		 }
		 return true;
	 }
	 
	 
	 /**
	  * adds the user to the user table of the crunch DB
	  * @return
	  */
	 public boolean writeToDB(){
		 
		 Statement stmt = null;
		 ResultSet rs = null;
		 

		 try {
		
			   if(conn==null) connect();
	            stmt = conn.createStatement();
	            
	            stmt.execute("insert into user values ('" + username + "' , '"+
	            		firstName + "' , '"+ lastName + "' , '"+ email + "' , "+
	            		"MD5('" +password +"' ) )");
	            inserted="true";
	            return true;
	         

	     } 
		 catch (Exception ex) {
			 inserted="false";
			 return false;
	     }
		 
	 }
	 
	
	 /**
	  * checks if name is already a username in the database
	  * @param name
	  * @return
	  */
	public boolean exists(String name){
		
		Statement stmt = null;
		ResultSet rs = null;
		 

		 try {
		
			 

			   if(conn==null) connect();
		     
	            stmt = conn.createStatement();
	            
	            stmt.execute("select username from user where username='" + name + "'");
	            
	            rs= stmt.getResultSet();
	            if (!rs.next()) return false;
	            
	         

	     } 
		 catch (Exception ex) {
			 return true;
	     }
		 finally {
			    
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
		return true;
		
	}
	 
	
	/**
	 * encrypts x using MD5 encryption
	 * @param x
	 * @return the encrypted String
	 * @throws Exception
	 */
	 public String encrypt(String x) throws Exception
	 {
		 java.security.MessageDigest d = java.security.MessageDigest.getInstance("MD5");
		 d.reset();
		 d.update(x.getBytes());
		 return new String(d.digest());
		 
	 }
	
	 
	 
	 public boolean authenticate(InetAddress address){
		 Statement stmt = null;
		 ResultSet rs = null;
		 
		 boolean result=true;
		 
		 try {
		
			 

			   if(conn==null) connect();
		     
	            stmt = conn.createStatement();
	            
	            stmt.execute("select username from user where username='" + username + "' and password=MD5('"+ password + "')");
	            rs = stmt.getResultSet();
	            
	            if(!rs.next()){
	            	result= false;
	            }
	            
	            
	            
	            //remove row from connected table
	            stmt = conn.createStatement();
	            
	            stmt.execute("delete from connected where user='" + username +  "'");
	            
	            
	            //remove all users from the same ip from connected table
	            stmt = conn.createStatement();
	            
	            stmt.execute("delete from connected where ip='" + address +  "'");
	         

	     } 
		 catch (Exception ex) {
			 result= false;
	     }
		 finally {
			    

			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { // ignore }

			        stmt = null;
			    }
			}
		  
		}
		 return result;
		 
	 
	 }
	 
	 public String login(InetAddress address){

		 Statement stmt = null;

		 String result = "true";
			 try {
			
				 

				   if(conn==null) connect();
			     
		            stmt = conn.createStatement();
		            
		            stmt.execute("insert into connected values ('" + username + "' , '"+
		            		address + "')");

		     } 
			 catch (Exception ex) {
				 result = ex.getMessage();
		     }
			 finally {
				    

				    if (stmt != null) {
				        try {
				            stmt.close();
				        } catch (SQLException sqlEx) { // ignore }

				        stmt = null;
				    }
				}
			  
			}
			 return result;
	 }
	 
	 
	 /**
	  * Creates a new settings file where the user's preferred settings
	  * would be stored. The default file settings would be the same as config/level6.ini
	  */
	 public boolean createUserPrefFile(){
		 
		 try{
			 File fromFile = new File("/home/hila/eclipse/workspace/crunch3/config/level6.ini");
			 File toFile = new File("/home/hila/eclipse/workspace/crunch3/users/" + username + ".ini");

			 FileInputStream from = new FileInputStream(fromFile);
			 FileOutputStream to = new FileOutputStream(toFile);
			 
			 byte[] buff = new byte[4096];
			 int i;
			 
			 while ((i = from.read(buff))!=-1){
				 to.write(buff, 0,i);
			 }
			 from.close();
			 to.close();
			 return true;
		 }
		 catch(Exception e){
			 return false;
		 }
		 
		 
	 }
	 
	 public boolean resetPassword(String userName, String lastName, String pass){
		 Statement stmt = null;

		 boolean result =true;
			 try {

				   if(conn==null) connect();
			     
		            stmt = conn.createStatement();
		            
		            stmt.execute("update user set password=MD5('"+ pass + "') where username = '" +userName + "' and "+
		            		"lastname = '" + lastName + "'");

		     } 
			 catch (Exception ex) {
				 result = false;
		     }
			 finally {
				    

				    if (stmt != null) {
				        try {
				            stmt.close();
				        } catch (SQLException sqlEx) { // ignore }

				        stmt = null;
				    }
				}
			  
			}
			 return result;
	 }	  
		 
		 
	 
}
