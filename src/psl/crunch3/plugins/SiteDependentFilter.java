/*
 * Created on Sep 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package psl.crunch3.plugins;

/**
 * @author Suhit Gupta (suhit@cs.columbia.edu)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface SiteDependentFilter {

	public void reportReferer(String referer);

	public void reportHost(String host);
	
	public void reportApplication(String application);
	
	public void reportURL(String URL);
}
