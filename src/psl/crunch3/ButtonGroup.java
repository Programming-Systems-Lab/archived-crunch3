/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import java.util.ArrayList;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * Performs the same function as the swing button group by ensuring that only
 * one button at any given time is selected.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class ButtonGroup implements SelectionListener {
	ArrayList buttons;

	/**
	 * Creates a new button group
	 */
	public ButtonGroup() {
		super();

		this.buttons = new ArrayList();
	}
	/**
	 * adds a button to the button group
	 * 
	 * @param b
	 *            the button to add
	 */
	public void add(Button b) {
		buttons.add(b);
		b.addSelectionListener(this);
	}

	/**
	 * Removes a button from the button group.
	 * 
	 * @param b
	 *            the button to remove
	 */
	public void remove(Button b) {
		buttons.remove(b);
		b.removeSelectionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		Object selectedButton = e.getSource();
		
		// deselect all other buttons in the group except the selected button
		for (int i = 0; i < buttons.size(); i++) {
			Button currentButton = (Button) buttons.get(i);
			if (currentButton != selectedButton)
				currentButton.setSelection(false);
			else
				currentButton.setSelection(true);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}
