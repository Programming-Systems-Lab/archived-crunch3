/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.util;

/**
 * Simple Class for Testing char cast speed.
 * About 8,000,000 chars per second on a p4 2.53GHz.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class CharCastTest {
	static StringBuffer buffer = new StringBuffer();
	
	public static void main(String[] args) {
		for(int j = 0; j < 10; j++) {
		buffer = new StringBuffer();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			buffer.append((char)(byte)i);
			if(buffer.length()>2) {
				if(buffer.charAt(buffer.length()-1)=='a' && 
				   buffer.charAt(buffer.length()-2)=='c')
				   System.out.println("newline");
			}
		}
		long stopTime = System.currentTimeMillis();
		long cps = (1000*(long)buffer.length())/(stopTime-startTime);
		System.out.println("Characters per second: "+cps);
		System.out.println("Characters total:"+buffer.length());
		}
	}
}
