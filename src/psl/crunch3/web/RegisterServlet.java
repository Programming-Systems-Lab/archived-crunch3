package psl.crunch3.web;

import javax.servlet.*;
import javax.servlet.http.*;


import java.io.*;
import java.net.InetAddress;
import java.util.*;

public class RegisterServlet extends HttpServlet{

	private static final String CONTENT_TYPE = "text/html";
	private static final String DOC_TYPE =
	      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\"" +
	      " \"http://www.w3.org/TR/html4/strict.dtd\">";
	
	//Initialize global variables
	  public void init() throws ServletException {
	  }





	  //Process the HTTP Get request
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {

		//get the bean for this session
	    HttpSession s = request.getSession(true);
	    RegisterBean rb = (RegisterBean)s.getAttribute("information");
	    if (rb==null){
	      rb = new RegisterBean();
	      s.setAttribute("rb", rb);
	    }
	    
	    
	    //store form data in bean variables
	    rb.setFirstName((request.getParameter("firstname")));
	    rb.setLastName((request.getParameter("lastname")));
	    rb.setEmail((request.getParameter("email")));
	    rb.setUsername((request.getParameter("username")));
	    rb.setPassword((request.getParameter("password")));
	    
	    //error checking
	    //check if the password entered a second time is the same as the original password
	    if (!rb.isValid()){
	    	RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/Register.jsp");
	        r.forward(request, response);
	    	
	    }
	    else if(!rb.verifyPassword(request.getParameter("confirmpass"))){
	    	RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/Register.jsp");
	        r.forward(request, response);
	    }
	    else{
	    	
	    	
	        if(rb.writeToDB()){
	        	
	        	Cookie c = new Cookie("crunch", rb.getUsername());
	        	InetAddress remoteInetAddress = InetAddress.getByName(request.getRemoteAddr());
	        	if((remoteInetAddress.toString()).equals("/128.59.14.166")){
	        		remoteInetAddress = InetAddress.getByName(((request.getParameter("ip")).substring(1)));
	        	}
	        	rb.createUserPrefFile();
	        	rb.authenticate(remoteInetAddress);
	        	String s1 = rb.login(remoteInetAddress);
	        	if(s1.equals("true")){
	        		response.addCookie(c);
	        		HttpSession session = (request.getSession(true));
	        		session.setAttribute("name",rb.getUsername());
	        		RequestDispatcher r = getServletContext().getRequestDispatcher(
	        			"/presets.html");
	        		r.forward(request, response);
	        	}
	        	else{
	        		RequestDispatcher r = getServletContext().getRequestDispatcher("/Register.jsp");
	        		r.forward(request, response);
	        	}
	        }
	        else {RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/Register.jsp");
	        r.forward(request, response);
	        }
	    }
	    
	    
	  }
	  
	  
	  
	  //Process the HTTP Post request
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
	    doGet(request, response);
	  }
	
	
}
