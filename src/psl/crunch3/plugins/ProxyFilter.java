/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins;

import org.w3c.dom.Document;

/**
 * A filter the proxy uses to modify the content it
 * forwards.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public abstract class ProxyFilter {
	private boolean enabled = true;

	/**
	 * Generates a settings gui to change the plugin
	 * specific settings.
	 */
	public void getSettingsGUI() {
		// no settings GUI is required
	}

	/**
	 * @return true if the plugin has a settings GUI
	 */
	public boolean hasSettingsGUI() {
		return false;
	}

	/**
	 * @return the name of the plugin
	 */
	public abstract String getName();

	/**
	 * @return the description of the plugin
	 */
	public abstract String getDescription();

	/**
	 * Sets whether or not the plugin is enabled
	 * 
	 * @param b
	 */
	public void setEnabled(boolean b) {
		enabled = b;
	}

	/**
	 * Whether or not the plugin is enabled. A disabled
	 * plugin will not be run on content.
	 * 
	 * @return whether or not the plugin is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Performs the processing on the currentDocument.
	 * originalDocument and previousDocument are provided
	 * for reference and should not be modified.
	 * 
	 * @param originalDocument
	 *            The original document
	 * @param previousDocument
	 *            The output of the filter run previously,
	 *            or null if there wasn't one
	 * @param currentDocument
	 *            the document to be modified
	 * @return the result of the modification (usually just
	 *         currentDocument)
	 */
	public abstract Document process(
		Document originalDocument,
		Document previousDocument,
		Document currentDocument);
}
