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
 * ----------------------
 * ContributorsPanel.java
 * ----------------------
 * (C) Copyright 2001-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 10-Dec-2001 : Version 1 (DG);
 * 28-Feb-2002 : Moved into package com.jrefinery.ui.about.  Changed import statements and
 *               updated Javadoc comments (DG);
 * 08-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 *
 */

package org.jfree.ui.about;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * A panel containing a table that lists the contributors to a project.
 * <P>
 * Used in the AboutFrame class.
 *
 */
public class ContributorsPanel extends JPanel {

    /** The table. */
    private JTable table;

    /** The data. */
    private TableModel model;

    /**
     * Creates a new contributors panel.
     *
     * @param contributors  a list of contributors (represented by Contributor objects).
     */
    public ContributorsPanel(final List contributors) {

        setLayout(new BorderLayout());
        this.model = new ContributorsTableModel(contributors);
        this.table = new JTable(this.model);
        add(new JScrollPane(this.table));

    }

}
