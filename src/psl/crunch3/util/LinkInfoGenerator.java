/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 */
package psl.crunch3.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author hb2143
 *
 */
public class LinkInfoGenerator {

	private final String SITES_FILE = "frequency" + File.separator + "sites.txt";
	
	/**
	 * 
	 */
	public LinkInfoGenerator() {
		try{
			BufferedReader inSites = new BufferedReader(new FileReader(new File(SITES_FILE)));
			String site = null;
			DataOutputStream out = new DataOutputStream(new FileOutputStream("linkinfo.txt"));
			while((site = inSites.readLine()) != null){
				process(site , out);
			}
			out.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LinkInfoGenerator lig = new LinkInfoGenerator(); 
	}
	
	private void process(String site, DataOutputStream o){
		try{
			System.out.println("getting link info for " + site);
			o.writeBytes("http://" + site.trim() + "\t" + getDomain("http://" + site.trim())+
					"\t" + site.length() + "\t" + "N/A" + "\t" + 
					0 + "\t" + getNumSubdir(site) + "\t" + getFormData(site) + "\t" + "http" + "\n" );
			URL url = new URL("http://" + site);
			
			generateLinks(new BufferedReader(new InputStreamReader(url.openStream())), "http://" + site.trim(), o);
			
			
		}
		catch(Exception e){
			
		}
		
	}
	
	private int isExternal(String link, String url){
		if(link.trim().startsWith(url.trim())){
			return 0;
		}
		else return 1;
	}
	
	private int getNumSubdir(String link){
		if (link.indexOf("http://") != -1){
			link = link.substring(7);
		}
		
		int counter = 0;
		int index;
		while( (index = link.indexOf("/")) != -1){
			counter++;
			link = link.substring(index+1);
		}
		if(link.endsWith("/")) --counter;
		return counter;
	}
	
	private String getFormData(String link){
		int index = link.indexOf("?");
		if(index == -1) return "N/A";
		else {
			return link.substring(index);
		}
	}
	
	public String getDomain(String link){
		int index = link.indexOf("//");
		if(index != -1){
			link = link.substring(index+2);
			index = link.indexOf("/");
			if(index != -1){
				return link.substring(0,index);
			}
			else return link;
		}
		else  return "";
		
	}
	
	public String getType(String link){
		int index = link.indexOf("://");
		if(index != -1){
			return link.substring(0,index);
		}
		else return "";
	}
	
	private void generateLinks(BufferedReader in, String url, DataOutputStream  o){
		
		try{
			int index;
			int start, end;
			String label = "N/A";
			String line = in.readLine();
			String foundLink = null;
			while(line != null){
				foundLink = null;
				label = "N/A";
				line.toLowerCase();
				//try to remove anything inside script tags
				index = line.indexOf("<script");
				if (index ==-1){
					index = line.indexOf("href=\"");
					//remove everything before the link
					if(index!=-1){
						line = line.substring(index+6);
						//System.out.println(url + line.substring(0,line.indexOf("\"")));
						//record the relative url
						if ((line.charAt(0) == '#') || (line.startsWith("javascript")) 
								|| (line.startsWith("mailto")) ||(line.startsWith("news"))); //do nothing
						
						else {
							if((line.charAt(0) == '/'))
								
								foundLink = url + line.substring(0,line.indexOf("\""));
							
							else{
								if(((line.substring(0,line.indexOf("\""))).indexOf("//")==-1)){
									foundLink = url + "/" +line.substring(0,line.indexOf("\""));
								}
								else{
									foundLink = line.substring(0,line.indexOf("\""));
								}
							}
						}
					}
					else{	
						line = in.readLine();
					}
				
					if(foundLink !=null){
						if (foundLink.equals("")) foundLink = url;
						start = line.indexOf(">");
						end = line.indexOf("<");
						if(start < end-1){
							label = line.substring(start+1, end);
						}
						o.writeBytes(foundLink + "\t" + getDomain(foundLink) + "\t" + foundLink.length() + "\t" + label +"\t"+
								isExternal(foundLink, url) + "\t" + getNumSubdir(foundLink) + "\t" + getFormData(foundLink) + 
								"\t" + getType(foundLink)+ "\n" );
						line = line.substring(line.indexOf("\"")+1);
					}
				}
				else{
					//try to find where the script tag closes
					while((line != null) && (index= line.indexOf("</script>")) == -1){
						line = in.readLine();
					}
					//now the line must contain the end of the script tag
					index= line.indexOf("</script>");
					line = line.substring(index+9);
					
					
				}
			}
			in.close();
			
		}
		
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}
	
}
