/*
 * Created on Jul 11, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package psl.crunch3;

//import java.util.Scanner;
/**
 * @author Hila Becker
 *
 * Controls the main operations of Crunch
 * includes a command line interface
 */
public class MainControl {

	boolean GUIActive;
	
	public MainControl(){
		
		GUIActive = false;
		menuLoop();
	}
	
	
	/**
	 * Displays a menu for the user to choose actions from
	 * @return
	 */
	private void menuLoop(){
		
		int choice = -1; //corresponds to the menu item number
		
		//only active as long as the GUI is off and the user doesn't want to exit
		while ((GUIActive ==false) && (choice !=0)){
			System.out.println("please choose one of the following options:");
			System.out.println("0. Exit the program");
			System.out.println("1. Exit the program");
			System.out.println("2. Exit the program");
			System.out.println("3. Exit the program");
			System.out.println("4. Exit the program");
			System.out.println("5. Exit the program");
			System.out.println("6. Exit the program");
			
			
			
				
			switch(choice){
			
				case 0: System.out.println("Thank you for using Cruch. GoodBye.");
						break;
				
			
			
			
			}
			
			
			
			
		}
	}
	
	/**
	 * Creates a new GUI window 
	 * @return
	 */
	//public MainWindow activateGUI(){
		//return new MainWindow(this);
	//}
	
	
	/**
	 * @return the value of GUIActive
	 */
	public boolean isGUIActive(){
		return GUIActive;
	}
	
	
	
	/**
	 * Sets the parameter which indicates whether the GUI is active 
	 * @param active
	 */
	public void setGUIActive(boolean active){
		
		GUIActive = active;
		
	}
	
	
	
	
}
