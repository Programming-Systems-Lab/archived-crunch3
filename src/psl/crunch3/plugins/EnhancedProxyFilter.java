/*
 * Copyright (c) 2004: The Trustees of Columbia University in the City of New
 * York. All Rights Reserved.
 *  
 */
package psl.crunch3.plugins;

import org.eclipse.swt.widgets.Composite;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EnhancedProxyFilter extends ProxyFilter {
	public abstract Composite getDescriptionGUI(Composite c);
	public abstract void selectCustom();
}
