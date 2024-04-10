/**
 * The Oval class represents an oval in 2D space.
 * It extends the MyShape class and provides methods for creating and drawing ovals.
 */
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class Oval extends MyShape {

	/**
	 * Constructs a default Oval object.
	 */
	public Oval() {
		super();
	}

	/**
	 * Constructs an Oval object with specified coordinates for the bounding rectangle.
	 *
	 * @param x1 The x-coordinate of the upper-left corner of the bounding rectangle.
	 * @param y1 The y-coordinate of the upper-left corner of the bounding rectangle.
	 * @param x2 The x-coordinate of the lower-right corner of the bounding rectangle.
	 * @param y2 The y-coordinate of the lower-right corner of the bounding rectangle.
	 */
	public Oval(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}

	/**
	 * Constructs an Oval object with specified points of the bounding rectangle.
	 *
	 * @param p1 The upper-left corner of the bounding rectangle.
	 * @param p2 The lower-right corner of the bounding rectangle.
	 */
	public Oval(Point2D p1, Point2D p2) {
		super(p1, p2);
	}

	/**
	 * Draws the oval on the specified GraphicsContext.
	 *
	 * @param gc The GraphicsContext on which to draw the oval.
	 */
	@Override
	public void draw(GraphicsContext gc) {
		drawBounds(gc);
		if (filled) {
			// Fill the oval with the specified color
			gc.setFill(color);
			gc.fillOval(ulx, uly, width, height);
		} else {
			// Draw the outline of the oval with the specified color
			gc.setStroke(color);
			gc.strokeOval(ulx, uly, width, height);
		}
	
	}
	
	/**
	 * A string representation for the shape
	 */
	@Override
	public String toString() {
		return "oval " + super.toString();
	}
}
