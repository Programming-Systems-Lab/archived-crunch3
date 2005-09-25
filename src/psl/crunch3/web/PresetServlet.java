package psl.crunch3.web;

import javax.servlet.*;
import javax.servlet.http.*;

import psl.crunch3.plugins.contentextractor.ContentExtractor;


import java.io.*;


public class PresetServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		
		String choice = request.getParameter("preset");
		Cookie[] cookies = request.getCookies();
		String username=null;
		
		if(cookies !=null){
			for(int i=0;i<cookies.length;i++){
				if ((cookies[i].getName()).equals("crunch")){
					username = cookies[i].getValue();
					break;
				}
			}
		}
		
		if(username==null){
			RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/login.htm");
	        r.forward(request, response);
		}
		else if(choice.equals("custom")){
			RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/interface.jsp");
	        r.forward(request, response);
			
		}
		else {
			
			
			if(choice.equals("shopping")){
		
				copy(username, ContentExtractor.LEVEL7_SETTINGS_FILE_DEF );
			}
		else if(choice.equals("news")){
			copy(username, ContentExtractor.LEVEL2_SETTINGS_FILE_DEF );
		}
		else if(choice.equals("textheavy")){
			
			copy(username, ContentExtractor.LEVEL2_SETTINGS_FILE_DEF );
		}
		else if(choice.equals("linkheavy")){
			
			copy(username, ContentExtractor.LEVEL10_SETTINGS_FILE_DEF );
		}
		else if(choice.equals("government")){
			
			copy(username, ContentExtractor.LEVEL5_SETTINGS_FILE_DEF );
		}
		else if(choice.equals("education")){
			
			copy(username, ContentExtractor.LEVEL6_SETTINGS_FILE_DEF );
		}
		else{
			copy(username, ContentExtractor.LEVEL11_SETTINGS_FILE_DEF );
		}
		
			RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/presets.html");
	        r.forward(request, response);	
			
		
	}
			
			
			
		
		
		
		
	}
	
	
	
	  //Process the HTTP Post request
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
	    doGet(request, response);
	  }
	  
	  
	  
	  
	  
	  
	  private boolean copy(String tof, String fromf){
		  
		  try{
				 File fromFile = new File("/home/hila/eclipse/workspace/crunch3/" + fromf);
				 File toFile = new File("/home/hila/eclipse/workspace/crunch3/users/" + tof + ".ini");

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
}
