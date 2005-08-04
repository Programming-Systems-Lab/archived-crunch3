/*
 * Created on Jul 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package psl.crunch3;

import java.util.Scanner;
import java.io.*;

import psl.crunch3.plugins.contentextractor.ContentExtractor;
import psl.crunch3.plugins.contentextractor.ContentExtractorDescription;
import psl.crunch3.plugins.sample.SamplePlugin;
import psl.crunch3.plugins.sizemodifier.SizeModifier;
/**
 * @author Hila Becker
 *
 * Controls the main operations of Crunch
 * includes a command line interface
 */
public class MainControl extends Thread{

	boolean GUIActive;
	String currentURL;
	ContentExtractor ce;
	ContentExtractorDescription description = null;
	
	public MainControl(boolean gui){
		
		super("MainControlThread");
		GUIActive = gui;
		this.start();
		
	}
	
	
	public void run(){
		if(!GUIActive){
			menuLoop();
		}
	}
	
	
	/**
	 * Displays a menu for the user to choose actions from
	 * @return
	 */
	private void menuLoop(){
		
		int choice = -1; //corresponds to the menu item number
		Scanner in = new Scanner(System.in);
		ce = new ContentExtractor();
		description = ce.getControl();
		yield();
		Crunch3.proxy.registerPlugin(ce);
	
		if(description == null) description = new ContentExtractorDescription();
		description.commitSettings((Crunch3.settings).getSettingsFile(), 0);
		
		
		//only active as long as the GUI is off and the user doesn't want to exit
		while ((GUIActive ==false) && (choice !=0)){
			
			
			System.out.println("please choose one of the following options:");
			System.out.println("0. Exit the program");
			System.out.println("1. load settings file");
			System.out.println("2. Activate GUI");
			
			
			choice = in.nextInt();
				
			switch(choice){
			
				case 0: System.out.println("Thank you for using Crunch. GoodBye.");
						System.exit(0);
						break;
				case 1: System.out.println("Please specify the name and location of the file");
						loadFile(in.next());
						break;
				case 2: System.out.println("Activating GUI");
						activateGUI();
						break;
			
			
			}	
		}
	}
	

	
	
	/**
	 * @return the value of GUIActive
	 */
	public boolean isGUIActive(){
		return GUIActive;
	}
	
	
	
	
	public void printStatus(){
		System.out.println("***********************************************************");
		System.out.println();
		
		System.out.println("check for front page: " + description.getCheckFrontPage());
		System.out.println("check for next page links: " + description.getCheckNextPage());
		System.out.println("current url: " + description.getCurrentURL());
		System.out.println("settings level: " + description.getSettingsLevel());
		System.out.println("settings file: " + Crunch3.settings.getSettingsFile());
		System.out.println("settings label: " + description.getSettingsLabel());
		System.out.println("***********************************************************");
		
		
		
	}
	
	
	/**
	 * Sets the parameter which indicates whether the GUI is active 
	 * @param active
	 */
	public void setGUIActive(boolean active){
		
		GUIActive = active;
		
	}
	
	public String parseURL(String original, String host){
		
		try{
		//original should be in the form GET /... HTTP/1.1
		int length = original.length()-9;
		return "http://" + host.trim() + original.substring(4,length).trim();
		
		}
		catch(Exception e){
			if (Crunch3.settings.isVerbose())
	    		System.out.println("error parsing the url");
		}
		return "";
	}
	
	public String getCurrentURL(){
		return currentURL;
	}
	
	public void setCurrentURL(String url){
		currentURL = url;
	}
	
	
	/**
	 * set the Crunch3Settings settings file to the specified file
	 * @param file
	 */
	public void loadFile(String file){
		
		//try to read the file
		System.out.println("changing settings from loadFile ******");
		try{
			
			FileReader reader = new FileReader(file);
			reader.close();
			Crunch3.settings.setSettingsFile(file);
			description.commitSettings(file, 0);
		}
		catch(Exception e){
			System.out.println("error reading the file, please verify the name and location of your file and try again");
		}
		
		
	}
	
	/**
	 * Activate the GUI
	 */
	private void activateGUI(){
		
		GUIActive = true;
		Crunch3.settings.setGUI(true);
		
		Crunch3.mainWindow = new MainWindow(this);		
		
		Crunch3.proxy.registerPlugin(new SamplePlugin());
		Crunch3.proxy.registerPlugin(new SizeModifier());
		Crunch3.proxy.registerPlugin(ce);
	}
	
	
}
