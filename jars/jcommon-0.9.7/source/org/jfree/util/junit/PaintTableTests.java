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
 * --------------------
 * PaintTableTests.java
 * --------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 *
 */

package org.jfree.util.junit;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.util.PaintTable;


/**
 * Tests for the {@link PaintTable} class.
 */
public class PaintTableTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return the test suite.
     */
    public static Test suite() {
        return new TestSuite(PaintTableTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param  name the name of the tests.
     */
    public PaintTableTests(final String name) {
        super(name);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        final PaintTable t1 = new PaintTable();
        t1.setPaint(0, 0, Color.blue);
        t1.setPaint(0, 1, Color.red);
        t1.setPaint(1, 0, Color.yellow);
        t1.setPaint(1, 1, Color.green);

        PaintTable t2 = null;

        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(t1);
            out.close();

            final ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            t2 = (PaintTable) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(t1, t2);

    }

}
