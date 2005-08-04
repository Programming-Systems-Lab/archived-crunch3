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
	
		lb.setUsername((request.getParameter("username")));
	    lb.setPassword((request.getParameter("password")));
		
			Cookie c = new Cookie("crunch", lb.getUsername());
        	InetAddress remoteInetAddress = InetAddress.getByName(request.getRemoteAddr()); 
        if(lb.authenticate(remoteInetAddress)){	
        	lb.login(remoteInetAddress);
        	response.addCookie(c);
			RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/interface.htm");
	        r.forward(request, response);
		}
		else{
			RequestDispatcher r = getServletContext().getRequestDispatcher(
	          "/login.htm");
	        r.forward(request, response);
		}
		
	}
	
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
	  	throws ServletException, IOException {
		  doGet(request, response);
	  }
	
	
}
