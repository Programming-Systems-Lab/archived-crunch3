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
 * ----------------
 * NumberUtils.java
 * ----------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 27-Aug-2003 : Version 1 (DG);
 * 04-Oct-2004 : Deprecated, no longer used (DG);
 *
 */

package org.jfree.util;

/**
 * Useful static utility methods.
 * 
 * @deprecated To be removed.
 */
public abstract class NumberUtils {

    /** The tolerance for equality of two doubles. */
    private static double doubleEpsilon = 0.0000000001;
    
    /**
     * Returns <code>true</code> if the two objects are equal OR both <code>null</code>.
     *
     * @param d1  double 1.
     * @param d2  double 2.
     *
     * @return <code>true</code> or <code>false</code>.
     */
    public static boolean equal(double d1, double d2) {
        return Math.abs(d1 - d2) < doubleEpsilon;
    }
    
}
