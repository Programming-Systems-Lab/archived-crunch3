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
 * -------------------------
 * RectangleInsetsTests.java
 * -------------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 14-Jun-2004 : Version 1 (DG);
 *
 */

package org.jfree.ui.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.ui.RectangleInsets;
import org.jfree.util.UnitType;

/**
 * Tests for the {@link RectangleInsets} class.
 */
public class RectangleInsetsTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RectangleInsetsTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RectangleInsetsTests(final String name) {
        super(name);
    }

    /**
     * Test the equals() method.
     */
    public void testEquals() {
        RectangleInsets i1 = new RectangleInsets(UnitType.ABSOLUTE, 1.0, 2.0, 3.0, 4.0);
        RectangleInsets i2 = new RectangleInsets(UnitType.ABSOLUTE, 1.0, 2.0, 3.0, 4.0);
        assertTrue(i1.equals(i2));
        assertTrue(i2.equals(i1));
        
        i1 = new RectangleInsets(UnitType.RELATIVE, 1.0, 2.0, 3.0, 4.0);
        assertFalse(i1.equals(i2));
        i2 = new RectangleInsets(UnitType.RELATIVE, 1.0, 2.0, 3.0, 4.0);
        assertTrue(i1.equals(i2));

        i1 = new RectangleInsets(UnitType.RELATIVE, 0.0, 2.0, 3.0, 4.0);
        assertFalse(i1.equals(i2));
        i2 = new RectangleInsets(UnitType.RELATIVE, 0.0, 2.0, 3.0, 4.0);
        assertTrue(i1.equals(i2));
        
        i1 = new RectangleInsets(UnitType.RELATIVE, 0.0, 0.0, 3.0, 4.0);
        assertFalse(i1.equals(i2));
        i2 = new RectangleInsets(UnitType.RELATIVE, 0.0, 0.0, 3.0, 4.0);
        assertTrue(i1.equals(i2));
        
        i1 = new RectangleInsets(UnitType.RELATIVE, 0.0, 0.0, 0.0, 4.0);
        assertFalse(i1.equals(i2));
        i2 = new RectangleInsets(UnitType.RELATIVE, 0.0, 0.0, 0.0, 4.0);
        assertTrue(i1.equals(i2));
        
        i1 = new RectangleInsets(UnitType.RELATIVE, 0.0, 0.0, 0.0, 0.0);
        assertFalse(i1.equals(i2));
        i2 = new RectangleInsets(UnitType.RELATIVE, 0.0, 0.0, 0.0, 0.0);
        assertTrue(i1.equals(i2));
        
    }
    
    /**
     * Serialize an instance, restore it, and check for identity.
     */
    public void testSerialization() {

        RectangleInsets i1 = new RectangleInsets(UnitType.ABSOLUTE, 1.0, 2.0, 3.0, 4.0);
        RectangleInsets i2 = null;
        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(i1);
            out.close();

            final ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            i2 = (RectangleInsets) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertTrue(i1.equals(i2)); 

    }

}
