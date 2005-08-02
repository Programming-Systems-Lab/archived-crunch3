package psl.crunch3.web;

import java.io.*;
import java.sql.*;

public class RegisterBean implements Serializable{

	private String firstName, lastName, email;
	private String username, password;
	private String repeat, firstValid, lastValid, emailValid, userValid,passValid;
	
	
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
	
	//TODO: write a method to write to database, check that there are no duplicates
	//reading from file for now
	
	
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
		 if(!isPasswordValid()){
			 passValid= "false";
			 password = "";
			 return false;
		 }
		 if(!isUsernameValid()){
			 userValid= "false";
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
			 
			 //Register the JDBC driver for MySQL.
			 //Class.forName("com.mysql.jdbc.Driver");
		     
			 
			 String url = "jdbc:mysql://localhost:3306/mysql";

		 

	            Connection conn = DriverManager.getConnection(url, "admin", "CrTH3W#cH"); 
	            stmt = conn.createStatement();
	            //rs = stmt.executeQuery("");

	     } 
		 catch (Exception ex) {
	            return false;
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
		 
		 
		 return true;
	 }
	 
	
	 
	 public boolean login(){
		 return true;
	 }
}
