/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;
/**
 * Handles the shutdown sequence.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class ShutdownThread extends Thread {
	public void run () {
		if (Crunch3.settings.isVerbose())
			System.out.println("Shutting down...");
	}
}
