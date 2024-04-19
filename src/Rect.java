/**
 * The Rect class represents a rectangle in 2D space.
 * It extends the MyShape class and provides methods for creating and drawing rectangles.
 */
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class Rect extends MyShape {

	/**
	 * Constructs a default Rect object.
	 */
	public Rect() {
		super();
	}

	/**
	 * Constructs a Rect object with specified coordinates for the bounding rectangle.
	 *
	 * @param x1 The x-coordinate of the upper-left corner of the bounding rectangle.
	 * @param y1 The y-coordinate of the upper-left corner of the bounding rectangle.
	 * @param x2 The x-coordinate of the lower-right corner of the bounding rectangle.
	 * @param y2 The y-coordinate of the lower-right corner of the bounding rectangle.
	 */
	public Rect(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}

	/**
	 * Constructs a Rect object with specified points of the bounding rectangle.
	 *
	 * @param p1 The upper-left corner of the bounding rectangle.
	 * @param p2 The lower-right corner of the bounding rectangle.
	 */
	public Rect(Point2D p1, Point2D p2) {
		super(p1, p2);
	}

	/**
	 * Draws the rectangle on the specified GraphicsContext.
	 *
	 * @param gc The GraphicsContext on which to draw the rectangle.
	 */
	@Override
	public void draw(GraphicsContext gc) {
		//drawBounds(gc);
		if (filled) {
			// Fill the rectangle with the specified color
			gc.setFill(color);
			gc.fillRect(ulx, uly, width, height);
		}
		else {
			// Draw the outline of the rectangle with the specified color
			gc.setStroke(color);
			gc.strokeRect(ulx, uly, width, height);
		
		}
	}
	
	/**
	 * A string representation for the shape
	 */
	public String toString() {
		return "rect " + super.toString();
	}
}
