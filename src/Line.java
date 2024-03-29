/**
 * The Line class represents a line in a 2D space.
 * It extends the MyShape class and provides methods for creating and drawing lines.
 */
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;



public class Line extends MyShape {

	/**
	 * Constructs a default Line object.
	 */
	public Line() {
		super();
	}

	/**
	 * Constructs a Line object with specified coordinates for the points.
	 *
	 * @param x1 The x-coordinate of the first point.
	 * @param y1 The y-coordinate of the first point.
	 * @param x2 The x-coordinate of the second point.
	 * @param y2 The y-coordinate of the second point.
	 */
	public Line(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}

	/**
	 * Constructs a Line object with specified points represented by Point2D objects.
	 *
	 * @param p1 The first point of the line.
	 * @param p2 The second point of the line.
	 */
	public Line(Point2D p1, Point2D p2) {
		super(p1, p2);
	}

	/**
	 * Draws the line on the specified GraphicsContext.
	 *
	 * @param gc The GraphicsContext on which to draw the line.
	 */
	@Override
	public void draw(GraphicsContext gc) {
		// Set the stroke color
		gc.setStroke(color);

		// Draw the line between the specified points
		gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

		// Draw bounds if available
		drawBounds(gc);
	}
}

