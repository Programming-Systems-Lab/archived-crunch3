/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins;

/**
 * Settings for a proxy filter.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public interface ProxyFilterSettings {
	/**
	 * Changes a setting for a proxy filter.
	 * 
	 * @param key
	 *            the setting to change
	 * @param value
	 *            the new value for the setting to be
	 *            changed
	 */
	public void set(String key, String value);

	/**
	 * Gets a settings for a proxy filter.
	 * 
	 * @param key
	 *            the setting to get
	 * @return the value of the setting specified by the
	 *         key
	 */
	public String get(String key);

	/**
	 * Commits the settings.
	 */
	public void commitSettings();

	/**
	 * Reverts the settings.
	 */
	public void revertSettings();
}
