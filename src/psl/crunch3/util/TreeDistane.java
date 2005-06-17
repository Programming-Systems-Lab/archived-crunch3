/*
 * Created on Jun 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package psl.crunch3.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * @author hb2143
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeDistane {

	public static void main(String[] args) {
		
		Document tree1, tree2;
		String site1,site2;
		site1 = args[0];
		site2 = args[1];
		
		
		
		//generate DOM trees for both sites
		tree1 = getDOMTree(site1);
		
		tree2 = getDOMTree(site2);
		
		
		//measure distance by comparing the trees, node by node
		
		
		
		
	}
	
	private static Document getDOMTree(String site){
		
		org.cyberneko.html.parsers.DOMParser parser = new org.cyberneko.html.parsers.DOMParser();
		try{
			URL url = new URL(site);
			
			InputStream in = url.openStream();
			InputStreamReader reader = new InputStreamReader(in,"ISO-8859-1");			
			parser.parse(new InputSource(reader));
			return parser.getDocument();
			
		}
		catch(Exception e){
			System.out.println(e.getClass());
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * compare nodes a and b and recursively compare their children
	 * if a != b increase the distance measure by 1. 
	 * returns the total distance 
	 */
	private int getDistanceEasy(Node a, Node b){
		
		int counter = 0;
		if(!(a.equals(b))){
			counter ++;
		}
		
		NodeList aChildren = a.getChildNodes();
		NodeList bChildren = b.getChildNodes();
		
		int numChildren;
		
		if (aChildren.getLength() < bChildren.getLength()){
			numChildren = aChildren.getLength();
			counter += (bChildren.getLength() - numChildren);
		}
		else{
			numChildren = bChildren.getLength();
			counter += (aChildren.getLength() - numChildren);
		}
		
		for(int i=0;i<numChildren;i++){
			
			counter += getDistanceEasy(aChildren.item(i), bChildren.item(i));
			
		}
		
		return counter;
		
	}
	
	
	
	
}
