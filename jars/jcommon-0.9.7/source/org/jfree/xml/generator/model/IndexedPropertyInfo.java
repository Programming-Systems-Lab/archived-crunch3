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
 * ------------------------
 * IndexedPropertyInfo.java
 * ------------------------
 * (C)opyright 2003, 2004, by Thomas Morgner and Contributors.
 *
 * Original Author:  Thomas Morgner;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id$
 *
 * Changes
 * -------
 * 21.06.2003 : Initial version
 *
 */

package org.jfree.xml.generator.model;

/**
 * Indexed property info.
 */
public class IndexedPropertyInfo extends PropertyInfo {

    /** The key. */
    private KeyDescription key;

    /**
     * Creates a new instance.
     * 
     * @param name  the name.
     * @param type  the type.
     */
    public IndexedPropertyInfo(final String name, final Class type) {
        super(name, type);
    }

    /**
     * Returns the key.
     * 
     * @return the key.
     */
    public KeyDescription getKey() {
        return this.key;
    }

    /**
     * Sets the key.
     * 
     * @param key  the key.
     */
    public void setKey(final KeyDescription key) {
        this.key = key;
    }
}
