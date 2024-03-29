/**
 * The ShapeCanvas class extends the Canvas class and provides functionality
 * for drawing and managing shapes on a canvas.
 */
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ShapeCanvas extends Canvas {

	private GraphicsContext gc; 
	private ArrayList<MyShape> shapes; 
	private MyShape curShape; 
	private Color currColor = Color.BLACK; 
	private double width, height;
	private boolean curFilled; 

	/**
	 * Constructs a ShapeCanvas object with the specified width and height.
	 *
	 * @param width  The width of the canvas.
	 * @param height The height of the canvas.
	 */
	public ShapeCanvas(double width, double height) {
		super(width, height);
		this.width = width;
		this.height = height;
		gc = getGraphicsContext2D();
		shapes = new ArrayList<>();
	}

	/**
	 * Changes the status of fill, false means outline, true means .
	 *
	 * @param fill A boolean value indicating whether the shape should be filled.
	 */
	public void setCurFilled(Boolean fill) {
		curFilled = fill;
	}

	/**
	 * Clears the canvas and redraws all shapes.
	 */
	public void paint() {
		// Clear canvas
		gc.clearRect(0, 0, width, height);

		// Draw all shapes
		for (MyShape s : shapes) {
			s.draw(gc);
		}

		// Draw current shape if not null
		if (curShape != null) {
			curShape.draw(gc);
		}
	}

	/**
	 * Adds a shape to the canvas and repaints.
	 *
	 * @param s The shape to be added.
	 */
	public void addShape(MyShape s) {
		shapes.add(s);
		paint();
	}

	/**
	 * Sets the current shape to be drawn.
	 *
	 * @param s The shape to be set as the current shape.
	 */
	public void setCurrentShape(MyShape s) {
		curShape = s;

		if (s != null) {
			curShape.setColor(currColor);
			curShape.setFilled(curFilled);
		}
	}

	/**
	 * Clears all shapes from the canvas.
	 */
	public void clear() {
		shapes.clear();
		paint();
	}

	/**
	 * Replaces the current mouse listener (press/release) and mouse motion listener (drag)
	 * with the passed listener object.
	 *
	 * @param listener An EventHandler object.
	 */
	public void replaceMouseHandler(EventHandler listener) {
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
	}
}
