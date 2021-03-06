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
 * ---------------------------
 * StringAttributeHandler.java
 * ---------------------------
 * (C)opyright 2003, 2004, by Thomas Morgner and Contributors.
 *
 * Original Author:  Thomas Morgner;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id$
 *
 * Changes 
 * -------
 * 24.09.2003 : Initial version
 *  
 */

package org.jfree.xml.attributehandlers;

/**
 * A {@link String} attribute handler.
 */
public class StringAttributeHandler implements AttributeHandler {

    /**
     * Creates a new attribute handler.
     */
    public StringAttributeHandler() {
        super();
    }

    /**
     * Converts the attribute to a string.
     * 
     * @param o  the attribute ({@link String} expected).
     * 
     * @return A string.
     */
    public String toAttributeValue(final Object o) {
        final String in = (String) o;
        if (in != null) {
            return in.toString(); 
        }
        return null;
    }

    /**
     * Converts a copy of the string.
     * 
     * @param s  the string.
     * 
     * @return a {@link Short}.
     */
    public Object toPropertyValue(final String s) {
        return new String(s);  // since String is immutable, is it necessary to create
                               // a new one?
    }
    
}
