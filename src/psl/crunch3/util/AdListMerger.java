/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Merges ad server lists.  Ad server lists contain one ad server per line
 * and nothing else.  Blank lines are skipped.
 * 
 * Usage: java psl.crunch3.util.AdListMerger in1 in2 ... out
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class AdListMerger {
	static Hashtable hashtable = new Hashtable();
	static Vector vector = new Vector();
	public static void main(String[] args) throws Exception {
		for (int i=0; i<args.length-1; i++) {
			System.out.println("Reading "+args[i]+"...");
			long count = 0;
			BufferedReader buff = new BufferedReader(new FileReader(args[i]));
			for(String line = buff.readLine(); line != null; line = buff.readLine()) {
				line = line.trim().toLowerCase();
				if(line.length()==0)
					continue;
				else if (hashtable.get(line)==null) {
					hashtable.put(line, line);
					vector.add(line);
					count++;
				}	
			}
			System.out.println(count+" entries added.");
		}
		
		hashtable = null;
		Collections.sort(vector);
		
		BufferedWriter buff = new BufferedWriter(new FileWriter(args[args.length-1]));
		Enumeration enumeration = vector.elements();
		while(enumeration.hasMoreElements())
			buff.write(enumeration.nextElement()+"\n");
		
		buff.close();
	}
}
