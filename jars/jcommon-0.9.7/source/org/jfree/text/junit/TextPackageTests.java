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
 * ---------------------
 * TextPackageTests.java
 * ---------------------
 * (C) Copyright 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes:
 * --------
 * 22-Mar-2004 : Version 1 (DG);
 *
 */

package org.jfree.text.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * A collection of tests for the org.jfree.text package. These tests can be run using JUnit 
 * (http://www.junit.org).
 */
public class TextPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite("org.jfree.text");
        suite.addTestSuite(TextBlockTests.class);
        suite.addTestSuite(TextBlockAnchorTests.class);
        suite.addTestSuite(TextBoxTests.class);
        suite.addTestSuite(TextFragmentTests.class);
        suite.addTestSuite(TextLineTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public TextPackageTests(final String name) {
        super(name);
    }

}
