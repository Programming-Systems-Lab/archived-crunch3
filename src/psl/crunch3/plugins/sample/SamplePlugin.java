/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins.sample;

import org.w3c.dom.Document;

import psl.crunch3.plugins.ProxyFilter;

/**
 * A sample plugin that does nothing.
 * 
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 */
public class SamplePlugin extends ProxyFilter {

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.ProxyFilter#getSettingsGUI()
	 */
	public void getSettingsGUI() {
		return;
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.ProxyFilter#getName()
	 */
	public String getName() {
		return "Sample Plugin";
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.ProxyFilter#getDescription()
	 */
	public String getDescription() {
		return "This plugin is in existence only to serve as a simple template for making plugins.";
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.ProxyFilter#hasSettingsGUI()
	 */
	public boolean hasSettingsGUI() {
		return false;
	}

	/* (non-Javadoc)
	 * @see psl.crunch3.plugins.ProxyFilter#process(org.w3c.dom.Document, org.w3c.dom.Document, org.w3c.dom.Document)
	 */
	public Document process(Document originalDocument, Document previousDocument, Document currentDocument) {
		return currentDocument;
	}

}
