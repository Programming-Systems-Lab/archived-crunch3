/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * -------------
 * BaseBoot.java
 * -------------
 * (C)opyright 2004, by Thomas Morgner and Contributors.
 *
 * Original Author:  Thomas Morgner;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id$
 *
 * Changes
 * -------
 * 07-Jun-2004 : Added source headers (DG);
 *
 */

package org.jfree.base;

import org.jfree.JCommon;
import org.jfree.base.config.HierarchicalConfiguration;
import org.jfree.base.config.PropertyFileConfiguration;
import org.jfree.base.config.SystemPropertyConfiguration;
import org.jfree.base.config.ModifiableConfiguration;

import org.jfree.base.log.DefaultLogModule;
import org.jfree.util.Log;
import org.jfree.util.Configuration;

/**
 * The base boot class. This initializes the services provided by
 * JCommon.
 */
public class BaseBoot extends AbstractBoot {

    /**
     * Singleton instance.
     */
    private static BaseBoot singleton;

    /**
     * The project info.
     */
    private BootableProjectInfo bootableProjectInfo;

    /**
     * Default constructor (private).
     */
    private BaseBoot() {
        bootableProjectInfo = JCommon.INFO;
    }

    /**
     * Returns the global configuration as modifiable configuration reference.
     *
     * @return the global configuration
     */
    public static ModifiableConfiguration getConfiguration() {
        return (ModifiableConfiguration) getInstance().getGlobalConfig();
    }

    /**
     * Returns the global configuration for JFreeReport.
     * <p/>
     * In the current implementation, the configuration has no properties defined, but
     * references a parent configuration that: <ul> <li>copies across all the
     * <code>System</code> properties to use as report configuration properties (obviously
     * the majority of them will not apply to reports);</li> <li>itself references a parent
     * configuration that reads its properties from a file <code>jfreereport.properties</code>.
     * </ul>
     *
     * @return the global configuration.
     */
    protected synchronized Configuration loadConfiguration() {
        final HierarchicalConfiguration globalConfig = new HierarchicalConfiguration();

        final PropertyFileConfiguration rootProperty = new PropertyFileConfiguration();
        rootProperty.load("/org/jfree/base/jcommon.properties");
        globalConfig.insertConfiguration(rootProperty);
        globalConfig.insertConfiguration(getPackageManager().getPackageConfiguration());

        final PropertyFileConfiguration baseProperty = new PropertyFileConfiguration();
        baseProperty.load("/jcommon.properties");
        globalConfig.insertConfiguration(baseProperty);

        final SystemPropertyConfiguration systemConfig = new SystemPropertyConfiguration();
        globalConfig.insertConfiguration(systemConfig);
        // just in case it is not already started ...
        return globalConfig;
    }

    /**
     * Returns the boot instance.
     * 
     * @return The boot instance.
     */
    public static synchronized AbstractBoot getInstance() {
        if (singleton == null) {
            singleton = new BaseBoot();
        }
        return singleton;
    }

    /**
     * Performs the boot process.
     */
    protected void performBoot() {
        getPackageManager().addModule(DefaultLogModule.class.getName());
        getPackageManager().load("org.jfree.base.");
        getPackageManager().initializeModules();
    }

    /**
     * Returns the project info.
     * 
     * @return The project info.
     */
    protected BootableProjectInfo getProjectInfo() {
        return bootableProjectInfo;
    }

    /**
     * A test. Will print "hello world" using the debug log.
     *
     * @param args ignored.
     */
    public static void main(final String[] args) {
        //DefaultLog.getInstance();
        BaseBoot.getInstance().start();
        Log.debug("Hello world");
    }

}
