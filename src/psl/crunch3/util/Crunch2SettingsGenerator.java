/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.util;
import java.io.*;

import psl.crunch3.TypedProperties;

/*
 *
 */

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 * 
 */
public class Crunch2SettingsGenerator {
	public static void main(String[] args) throws Exception{
		TypedProperties properties = new TypedProperties();
		
		properties.setProperty("listen port", 4000);
		properties.setProperty("filter ad servers", true);
		properties.setProperty("filter types", "text/html");
		properties.setProperty("filter homepages", true);
		properties.setProperty("socket timeout", 1000);
		
		properties.list(System.out);
		
		File outputFile = new File("Crunch3 Settings.ini");
		FileOutputStream fos = new FileOutputStream(outputFile);
		properties.store(fos,"Crunch3 Settings");
		
		fos.close();
		fos = null;
	}
}
