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
 * ObjectUtilsTests.java
 * ---------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 15-Sep-2004 : Version 1 (DG);
 *
 */

package org.jfree.util.junit;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.util.ObjectUtils;

/**
 * Tests for the {@link ObjectUtils} class.
 */
public class ObjectUtilsTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ObjectUtilsTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public ObjectUtilsTests(String name) {
        super(name);
    }

    /**
     * Some checks for the clone(List) method.
     */
    public void testHasDuplicateItems() {
        List l1 = new ArrayList();
        l1.add("S1");
        l1.add(null);
        l1.add("S3");
        List l2 = null;
        try {
            l2 = ObjectUtils.clone(l1);
        }
        catch (CloneNotSupportedException e) {
            assertTrue(false);
        }
        assertTrue(l1.equals(l2));
        
        // check that the clone is independent of the original
        l2.clear();
        assertFalse(l1.equals(l2));
        
    }
}
