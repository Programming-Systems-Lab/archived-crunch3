/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
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
 * DataTimePackageTests.java
 * -------------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 13-Mar-2001 : Version 1 (DG);
 * 23-Aug-2004 : Restructured org.jfree.data package (DG);
 *
 */

package org.jfree.data.time.junit;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for the <code>org.jfree.data.time</code> package that can be run using JUnit.
 * You can find more information about JUnit at http://www.junit.org.
 */
public class DataTimePackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return the test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.data.time");
        suite.addTestSuite(DateRangeTests.class);
        suite.addTestSuite(SimpleTimePeriodTests.class);
        suite.addTestSuite(YearTests.class);
        suite.addTestSuite(QuarterTests.class);
        suite.addTestSuite(MonthTests.class);
        suite.addTestSuite(WeekTests.class);
        suite.addTestSuite(DayTests.class);
        suite.addTestSuite(HourTests.class);
        suite.addTestSuite(MinuteTests.class);
        suite.addTestSuite(SecondTests.class);
        suite.addTestSuite(MillisecondTests.class);
        suite.addTestSuite(FixedMillisecondTests.class);
        suite.addTestSuite(MovingAverageTests.class);
        suite.addTestSuite(TimeSeriesDataItemTests.class);
        suite.addTestSuite(TimeSeriesTests.class);
        suite.addTestSuite(TimeSeriesCollectionTests.class);
        suite.addTestSuite(TimePeriodAnchorTests.class);
        suite.addTestSuite(TimePeriodValueTests.class);
        suite.addTestSuite(TimePeriodValuesTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the test suite name.
     */
    public DataTimePackageTests(final String name) {
        super(name);
    }

}
