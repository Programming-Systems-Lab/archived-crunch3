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
 * SerialUtilities.java
 * --------------------
 * (C) Copyright 2000-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id$
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 * 18-Sep-2003 : Added capability to serialize GradientPaint (DG);
 * 26-Apr-2004 : Added read/writePoint2D() methods (DG);
 *
 */

package org.jfree.io;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A class containing useful utility methods relating to serialization.
 */
public abstract class SerialUtilities {

    /**
     * Returns <code>true</code> if a class implements <code>Serializable</code> and 
     * <code>false</code> otherwise.
     * 
     * @param c  the class.
     * 
     * @return A boolean.
     */
    public static boolean isSerializable(Class c) {
        boolean result = false;
        Class[] interfaces = c.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(Serializable.class)) {
                result = true;                
            }
        }
        return result;
    }
    
    /**
     * Reads a <code>Paint</code> object that has been serialised by the
     * {@link SerialUtilities#writePaint(Paint, ObjectOutputStream)} method.
     *
     * @param stream  the input stream (<code>null</code> not permitted).
     *
     * @return The paint object (possibly <code>null</code>).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Paint readPaint(final ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        Paint result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class c = (Class) stream.readObject();
            if (isSerializable(c)) {
                result = (Paint) stream.readObject();
            }
            else if (c.equals(GradientPaint.class)) {
                float x1 = stream.readFloat();
                float y1 = stream.readFloat();
                Color c1 = (Color) stream.readObject();
                float x2 = stream.readFloat();
                float y2 = stream.readFloat();
                Color c2 = (Color) stream.readObject();
                boolean isCyclic = stream.readBoolean();
                result = new GradientPaint(x1, y1, c1, x2, y2, c2, isCyclic);
            }
        }
        return result;

    }

    /**
     * Serialises a <code>Paint</code> object.
     *
     * @param paint  the paint object (<code>null</code> permitted).
     * @param stream  the output stream (<code>null</code> not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writePaint(Paint paint, 
                                  ObjectOutputStream stream) throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        if (paint != null) {
            stream.writeBoolean(false);
            stream.writeObject(paint.getClass());
            if (paint instanceof Serializable) {
                stream.writeObject(paint);
            }
            else if (paint instanceof GradientPaint) {
                GradientPaint gp = (GradientPaint) paint;
                stream.writeFloat((float) gp.getPoint1().getX());
                stream.writeFloat((float) gp.getPoint1().getY());
                stream.writeObject(gp.getColor1());
                stream.writeFloat((float) gp.getPoint2().getX());
                stream.writeFloat((float) gp.getPoint2().getY());
                stream.writeObject(gp.getColor2());
                stream.writeBoolean(gp.isCyclic());
            }
        }
        else {
            stream.writeBoolean(true);
        }

    }

    /**
     * Reads a <code>Stroke</code> object that has been serialised by the
     * {@link SerialUtilities#writeStroke(Stroke, ObjectOutputStream)} method.
     *
     * @param stream  the input stream (<code>null</code> not permitted).
     *
     * @return The stroke object (possibly <code>null</code>).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Stroke readStroke(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        Stroke result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class c = (Class) stream.readObject();
            if (c.equals(BasicStroke.class)) {
                float width = stream.readFloat();
                int cap = stream.readInt();
                int join = stream.readInt();
                float miterLimit = stream.readFloat();
                float[] dash = (float[]) stream.readObject();
                float dashPhase = stream.readFloat();
                result = new BasicStroke(width, cap, join, miterLimit, dash, dashPhase);
            }
            else {
                result = (Stroke) stream.readObject();
            }
        }
        return result;

    }

    /**
     * Serialises a <code>Stroke</code> object.
     *
     * @param stroke  the stroke object (<code>null</code> permitted).
     * @param stream  the output stream (<code>null</code> not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writeStroke(Stroke stroke, 
                                   ObjectOutputStream stream) throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        if (stroke != null) {
            stream.writeBoolean(false);
            if (stroke instanceof BasicStroke) {
                BasicStroke s = (BasicStroke) stroke;
                stream.writeObject(BasicStroke.class);
                stream.writeFloat(s.getLineWidth());
                stream.writeInt(s.getEndCap());
                stream.writeInt(s.getLineJoin());
                stream.writeFloat(s.getMiterLimit());
                stream.writeObject(s.getDashArray());
                stream.writeFloat(s.getDashPhase());
            }
            else {
                stream.writeObject(stroke.getClass());
                stream.writeObject(stroke);
            }
        }
        else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a <code>Shape</code> object that has been serialised by the 
     * {@link #writeShape(Shape, ObjectOutputStream)} method.
     *
     * @param stream  the input stream (<code>null</code> not permitted).
     *
     * @return The shape object (possibly <code>null</code>).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Shape readShape(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        Shape result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class c = (Class) stream.readObject();
            if (c.equals(Line2D.class)) {
                double x1 = stream.readDouble();
                double y1 = stream.readDouble();
                double x2 = stream.readDouble();
                double y2 = stream.readDouble();
                result = new Line2D.Double(x1, y1, x2, y2);
            }
            else if (c.equals(Rectangle2D.class)) {
                double x = stream.readDouble();
                double y = stream.readDouble();
                double w = stream.readDouble();
                double h = stream.readDouble();
                result = new Rectangle2D.Double(x, y, w, h);
            }
            else if (c.equals(Ellipse2D.class)) {
                double x = stream.readDouble();
                double y = stream.readDouble();
                double w = stream.readDouble();
                double h = stream.readDouble();
                result = new Ellipse2D.Double(x, y, w, h);
            }
            else {
                result = (Shape) stream.readObject();
            }
        }
        return result;

    }

    /**
     * Serialises a <code>Shape</code> object.
     *
     * @param shape  the shape object (<code>null</code> permitted).
     * @param stream  the output stream (<code>null</code> not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writeShape(Shape shape, 
                                  ObjectOutputStream stream) throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        if (shape != null) {
            stream.writeBoolean(false);
            if (shape instanceof Line2D) {
                Line2D line = (Line2D) shape;
                stream.writeObject(Line2D.class);
                stream.writeDouble(line.getX1());
                stream.writeDouble(line.getY1());
                stream.writeDouble(line.getX2());
                stream.writeDouble(line.getY2());
            }
            else if (shape instanceof Rectangle2D) {
                Rectangle2D rectangle = (Rectangle2D) shape;
                stream.writeObject(Rectangle2D.class);
                stream.writeDouble(rectangle.getX());
                stream.writeDouble(rectangle.getY());
                stream.writeDouble(rectangle.getWidth());
                stream.writeDouble(rectangle.getHeight());
            }
            else if (shape instanceof Ellipse2D) {
                Ellipse2D ellipse = (Ellipse2D) shape;
                stream.writeObject(Ellipse2D.class);
                stream.writeDouble(ellipse.getX());
                stream.writeDouble(ellipse.getY());
                stream.writeDouble(ellipse.getWidth());
                stream.writeDouble(ellipse.getHeight());
            }
            else {
                stream.writeObject(shape.getClass());
                stream.writeObject(shape);
            }
        }
        else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a <code>Point2D</code> object that has been serialised by the 
     * {@link #writePoint2D(Point2D, ObjectOutputStream)} method.
     *
     * @param stream  the input stream (<code>null</code> not permitted).
     *
     * @return The point object (possibly <code>null</code>).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Point2D readPoint2D(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        Point2D result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            double x = stream.readDouble();
            double y = stream.readDouble();
            result = new Point2D.Double(x, y);
        }
        return result;

    }

    /**
     * Serialises a <code>Point2D</code> object.
     *
     * @param p  the point object (<code>null</code> permitted).
     * @param stream  the output stream (<code>null</code> not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writePoint2D(Point2D p, 
                                    ObjectOutputStream stream) throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");   
        }
        if (p != null) {
            stream.writeBoolean(false);
            stream.writeDouble(p.getX());
            stream.writeDouble(p.getY());
        }
        else {
            stream.writeBoolean(true);
        }
    }

}

