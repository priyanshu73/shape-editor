/**
 *  Writing the abstract shape class that serves as a base for other shapes such as line, oval and rectangle. 
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MyShape implements Serializable {
	protected transient Point2D p1, p2 , center;
	protected transient Color color;
	protected double ulx , uly, width, height;
	protected boolean filled; 
	private static final Color COLOR = Color.BLACK;

	/**
	 * Constructs a MyShape object with default properties (empty body)
	 */
	public MyShape() {
	}

	/**
	 * Constructs a MyShape object with specified points.
	 * The default color is black.
	 * 
	 * @param p1 The first point of the shape.
	 * @param p2 The second point of the shape.
	 */
	public MyShape(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
		color = COLOR;
		updateCenter();
		updateBounds();    
	}

	/**
	 * Constructs a MyShape object with specified point coordinates.
	 * The default color is black.
	 * 
	 * @param x1 The x-coordinate of the first point.
	 * @param y1 The y-coordinate of the first point.
	 * @param x2 The x-coordinate of the second point.
	 * @param y2 The y-coordinate of the second point.
	 */
	public MyShape( double x1, double y1, double  x2, double y2) {
		this( new Point2D(x1, y1), new Point2D(x2, y2));
	}

	/**
	 * Retrieves the first point of the shape.
	 * 
	 * @return The first point of the shape.
	 */
	public Point2D getP1() {
		return p1;
	}

	/**
	 * Retrieves the second point of the shape.
	 * 
	 * @return The second point of the shape.
	 */
	public Point2D getP2() {
		return p2;
	}

	/**
	 * Retrieves the color of the shape.
	 * 
	 * @return The color of the shape.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Checks if the shape is filled.
	 * 
	 * @return True if the shape is filled, false otherwise.
	 */
	public Boolean isFilled() {
		return filled;
	}

	/**
	 * Retrieves the x-coordinate of the upper-left corner of the bounding box.
	 * 
	 * @return The x-coordinate of the upper-left corner of the bounding box.
	 */
	public double getULX() {
		return ulx;
	}

	/**
	 * Retrieves the y-coordinate of the upper-left corner of the bounding box.
	 * 
	 * @return The y-coordinate of the upper-left corner of the bounding box.
	 */
	public double getULY() {
		return uly;
	}

	/**
	 * Retrieves the width of the bounding box.
	 * 
	 * @return The width of the bounding box.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Retrieves the height of the bounding box.
	 * 
	 * @return The height of the bounding box.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Retrieves the center point of the shape.
	 * 
	 * @return The center point of the shape.
	 */
	public Point2D getCenter() {
		return center;
	}

	/**
	 * Updates the bounding box of the shape.
	 */
	public void updateBounds() {
		double x1 = p1.getX();
		double x2 = p2.getX();
		double y1 = p1.getY();
		double y2 = p2.getY();
		ulx = Math.min(x1, x2);
		uly = Math.min(y1, y2);
		width = Math.abs(x1-x2);
		height = Math.abs(y1-y2);
	}

	/**
	 * Updates the center point of the shape.
	 */
	public void updateCenter() {
		center = p1.midpoint(p2);
	}

	/**
	 * Sets the first point of the shape.
	 * 
	 * @param p1 The new first point of the shape.
	 */
	public void setP1(Point2D p1) {
		this.p1 = p1;
	}

	/**
	 * Sets the first point of the shape using specified coordinates.
	 * 
	 * @param x1 The x-coordinate of the new first point.
	 * @param y1 The y-coordinate of the new first point.
	 */
	public void setP1(double x1, double y1) {
		p1 = new Point2D(x1, y1);
	}

	/**
	 * Sets the second point of the shape.
	 * 
	 * @param p2 The new second point of the shape.
	 */
	public void setP2(Point2D p2) {
		this.p2 = p2;
		updateCenter();
		updateBounds();
	}

	/**
	 * Sets the second point of the shape using specified coordinates.
	 * 
	 * @param x2 The x-coordinate of the new second point.
	 * @param y2 The y-coordinate of the new second point.
	 */
	public void setP2(double x2, double y2) {
		p2 = new Point2D(x2, y2);
		updateCenter();
		updateBounds();
	}

	/**
	 * Sets the color of the shape.
	 * 
	 * @param color The new color of the shape.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets whether the shape is filled.
	 * 
	 * @param filled True if the shape should be filled, false otherwise.
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Calculates the distance from the center of the shape to a specified point.
	 * 
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 * @return The distance between the center of the shape and the specified point.
	 */
	public double distance(double x, double y) {
		return center.distance(x, y);
	}

	/**
	 * Draws the bounding box of the shape on the specified graphics context.
	 * 
	 * @param gc The graphics context on which to draw the bounding box.
	 */
	public void drawBounds(GraphicsContext gc) {
		gc.setLineDashes(5);
		gc.setStroke(color);
		gc.strokeRect(ulx, uly, width, height);
		gc.setLineDashes(null);
	}

	/**
	 * Abstract method to draw the shape on the specified graphics context.
	 * 
	 * @param gc The graphics context on which to draw the shape.
	 */
	public abstract void draw(GraphicsContext gc);

	@Override
	public String toString() {
		return String.format("%-3.0f %-3.0f %-3.0f %-3.0f %b %.3f %.3f %.3f",p1.getX(), p1.getY(), p2.getX(), p2.getY(), filled, color.getRed(),color.getGreen(), color.getBlue());
	}

	//customize serialization
	
	/**
	 * For serializing the Color and Point object
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		//default serialization all basic types,

		out.defaultWriteObject();


		//serialize color

		out.writeDouble(color.getRed());
		out.writeDouble(color.getGreen());
		out.writeDouble(color.getBlue());
		
		//serialize points
		out.writeDouble(p1.getX());
		out.writeDouble(p1.getY());
		out.writeDouble(p2.getX());
		out.writeDouble(p2.getY());

	}

	/**
	 * For reading the custom serialized objects
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{

		in.defaultReadObject();
		double r = in.readDouble();
		double g = in.readDouble();
		double b = in.readDouble();

		//reading the point coordinates
		double x1 = in.readDouble();
		double y1 = in.readDouble();
		double x2 = in.readDouble();
		double y2 = in.readDouble();

		setP1(x1,y1);
		setP2(x2,y2);
		color = Color.color(r, g, b);
	}

}