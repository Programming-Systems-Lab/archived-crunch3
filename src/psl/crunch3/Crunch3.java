/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import org.eclipse.swt.widgets.Display;

import psl.crunch3.plugins.contentextractor.ContentExtractor;
import psl.crunch3.plugins.sample.SamplePlugin;
import psl.crunch3.plugins.sizemodifier.SizeModifier;

/**
 * This is the second version of a content extraction proxy.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class Crunch3 {
	/**
	 * Parsed set of command line arguments.
	 */
	public static Crunch3Settings settings;
	public static Proxy proxy;
	public static MainWindow mainWindow;
	public static Display Display_1;
	
	public static void main(String[] args){
		settings = new Crunch3Settings(args);
		new Crunch3();
	}
	
	public Crunch3 () {
		if(settings.isVerbose())
			System.out.println("Crunch3 Started...");
			
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		
		mainWindow = new MainWindow();
		
		proxy = new Proxy(settings.getListenPort());		
		
		proxy.registerPlugin(new ContentExtractor());
		proxy.registerPlugin(new SamplePlugin());
		proxy.registerPlugin(new SizeModifier());
		
		//spin
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
