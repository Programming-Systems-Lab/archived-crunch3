/*
 * 
 */
package psl.crunch3;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TrayItem;

/**
 * Removes the system tray icon from the system tray when crunch closes.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class TrayShutdownHook extends Thread {
	MainWindow window;
	
	public TrayShutdownHook(){
		super("TrayShutdownHook");
	}
	
	public void run() {
		// Get the main window (to get the tray item)
		window = Crunch3.mainWindow;
		
		// if it doesn't exist, the tray item probably doesn't exist
		if(window==null)
			return;
		
		// grab the display to execute the tray removal
		Display display = Crunch3.Display_1;
		
		// if the display doesn't exist, can't remove the item
		if (display == null || display.isDisposed())
			return;
		
		// runnable to execute the tray item removal
		Runnable r = new Runnable(){
			public void run() {
				// get the tray item
				TrayItem trayItem = window.mainTrayItem;
				
				// if the tray item is nonexistent or already disposed,
				// there is nothing to do
				if (trayItem==null || trayItem.isDisposed())
					return;
				
				// remove the tray item
				trayItem.setVisible(false);
			}
		};
		
		if (Thread.currentThread() == display.getThread())
			r.run();
		else
			display.syncExec(r);
	}
}
