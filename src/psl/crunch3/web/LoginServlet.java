package psl.crunch3.web;

import javax.servlet.*;
import javax.servlet.http.*;


import java.io.*;
import java.net.InetAddress;
import java.util.*;


public class LoginServlet extends HttpServlet{

	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

	//get the bean for this session
		HttpSession s = request.getSession(true);
		RegisterBean lb = (RegisterBean)s.getAttribute("information");
		if (lb==null){
			lb = new RegisterBean();
			s.setAttribute("lb", lb);
		}
	
		boolean done = false;
		
		
		if((request.getParameter("reset")).equals("true")){
			
			done =(lb.resetPassword(request.getParameter("username"),request.getParameter("lastname"),request.getParameter("password")));
			
		       
		}
		
		
		
		lb.setUsername((request.getParameter("username")));
	    lb.setPassword((request.getParameter("password")));
		
			Cookie c = new Cookie("crunch", lb.getUsername());
        	InetAddress remoteInetAddress = InetAddress.getByName(request.getRemoteAddr());
        	if((remoteInetAddress.toString()).equals("/128.59.14.166")){
        		remoteInetAddress = InetAddress.getByName(((request.getParameter("ip")).substring(1)));
        	}
        if(lb.authenticate(remoteInetAddress)){	
        	lb.login(remoteInetAddress);
        	response.addCookie(c);
        	
        	HttpSession session = (request.getSession(true));
        	session.setAttribute("name",lb.getUsername());
        	
			RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/presets.html");
	        r.forward(request, response);
		}
		else{
			if((request.getParameter("reset")).equals("true")){
				RequestDispatcher r = getServletContext().getRequestDispatcher(
		          "/tryagain.html");
		        r.forward(request, response);
			}
			else{
				RequestDispatcher r = getServletContext().getRequestDispatcher(
				"/login.htm");
	        	r.forward(request, response);
			}
		}
		
	}
	
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	  	throws ServletException, IOException {
		  doGet(request, response);
	  }
	
	
	  
	  
}
