/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3;
import java.util.Properties;

/**
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 *
 */
public class TypedProperties extends Properties {

	public TypedProperties() {
		super();
	}

	public TypedProperties(final Properties arg0) {
		super(arg0);
	}
	
	public Object setProperty(final String property, final boolean value) {
		return setProperty(property, String.valueOf(value));
	}
	
	public boolean getProperty(final String property, final boolean def) {
		String value = def?getProperty(property,"true"):getProperty(property,"false");
		value = value.trim().toLowerCase();
		if ("true".equals(value))
			return true;
		if ("false".equals(value))
			return false;
		
		return def;
	}
	
	public Object setProperty(final String property, final int value) {
		return setProperty(property, String.valueOf(value));
	}
	
	public int getProperty(final String property, final int def) {
		String value = getProperty(property, String.valueOf(def));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	public Object setProperty(final String property, final double value) {
		return setProperty(property, String.valueOf(value));
	}
	
	public double getProperty(final String property, final double def) {
		String value = getProperty(property, String.valueOf(def));
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return def;
		}
	}
}
