/*
 * Created on Sep 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package psl.crunch3.plugins.contentextractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SiteDataLoader {
	private static String IMAGE_DB_FILE = "imagedb.txt";
	
	private HashMap sites;
	

	private void loadData () {
		try {
			FileReader fr = new FileReader(IMAGE_DB_FILE);
			BufferedReader buff = new BufferedReader(fr);
			
			try {
				for (String currentLine = buff.readLine(); currentLine != null; currentLine = buff.readLine()){
					StringTokenizer tokens = new StringTokenizer("\t");
					
					SiteData data = new SiteData();
					
					// name of webiste <t> 
					data.websiteName = tokens.nextToken();
					// url <t> 
					data.url = tokens.nextToken();
					// physical classification 1-4 (all tab delimited) <t>
					data.physicalClassification = tokens.nextToken();
					// content classification <t>
					data.contentClassification = tokens.nextToken();
					// image name <t> 
					data.imageName = tokens.nextToken();
					// layout image
					data.layoutImage = tokens.nextToken();
					
					URI uri;
					try {
						uri = new URI(data.url);
						sites.put(uri.getHost(), data);
					} catch (URISyntaxException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class SiteData {
	String websiteName;
	String url;
	String physicalClassification;
	String contentClassification;
	String imageName;
	String layoutImage;
}
