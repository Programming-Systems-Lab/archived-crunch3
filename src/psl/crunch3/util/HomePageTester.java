/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New York. All Rights Reserved. 
 */
package psl.crunch3.util;
import java.net.*;

import psl.crunch3.Crunch3;
/**
 * @author Hila Becker
 *
 * Predicts whether or not a page is a frontpage/homepage
 */
public class HomePageTester {

	private String url;
	boolean isHomePage;
	String[] parts;
	
	public HomePageTester(String url)
	{
		this.url = url;
		isHomePage = false;
		parts = url.split("/");
		testPage();
	}
	
	private void testPage(){
		isHomePage = (checkSyntax() && checkStructure()); 
	}
	
	
	private boolean checkSyntax(){
		if (parts.length<2) return false; 
    	if (parts[parts.length-1].toLowerCase().trim().startsWith("index")) return true;
    	if (parts[parts.length-1].toLowerCase().trim().startsWith("default")) return true;
    	if (parts[parts.length-1].toLowerCase().trim().startsWith("~")) return true;
    	if (parts.length==3) return true;
    	return false;
	}
	
	
	private boolean checkStructure(){
		String tempURL="";
		int i = url.lastIndexOf("/");

		try{
			while(i!=-1){
				tempURL = url.substring(0,i);
				URL page = new URL(tempURL);
				String response = page.openConnection().getHeaderField(0);
				if (response.trim().endsWith("404 Not Found") || response.trim().endsWith("403 Forbidden") 
						|| response.trim().endsWith("401 Unauthorized")){
					return false;
				}
				i = tempURL.lastIndexOf("/");
				if (i==6) i=-1;
			}
		}	
		catch(Exception e){
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
		return true;
	}
	
	
	
	public boolean isHomePage(){
		return isHomePage;
	}
	
	
	
	
	
}
