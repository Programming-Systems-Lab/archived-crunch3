/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Text;

/**
 * Focus listener that forces a text widget to contain only valid integers.
 * @see TextDoubleListener
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class TextIntegerListener implements FocusListener {
	private int min;
	private int max;
	private int number;
	private boolean numberSet;
	
	/**
	 * Creats a new text integer listener that has an unbounded range.
	 */
	public TextIntegerListener() {
		this(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	/**
	 * Creates a new text integer listener that forces the integer
	 * to be within the range specified.
	 * @param min the minimum acceptable value of the integer
	 * @param max the maximum acceptable value of the integer
	 */
	public TextIntegerListener(final int min, final int max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Stores the current number.
	 */
	public void focusGained(final FocusEvent arg0) {
		try {
			Text text = (Text)arg0.getSource();
			String s = text.getText();
			int n = Integer.parseInt(s);
			if (n <= max && n >= min) {
				number = n;
				numberSet = true;
			}
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
	}
	
	/**
	 * Makes sure the current number is parsable.  Also makes sure the current
	 * number is within the range if specified.  If there is a problem with the
	 * number it is replaced with the old number.
	 */
	public void focusLost(final FocusEvent arg0) {
		if (!numberSet) return;
		try {
			Text text = (Text)arg0.getSource();
			String s = text.getText();
			try {
				int n = Integer.parseInt(s);
				if (n <= max && n >= min) {
					number = n;
				} else {
					text.setText(String.valueOf(number));
				}
			} catch (NumberFormatException nfe) {
				text.setText(String.valueOf(number));
			}
		} catch (Exception e) {
			if (Crunch3.settings.isVerbose())
				e.printStackTrace();
		}
	}
}
