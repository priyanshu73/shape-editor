/**
 * The LineHandler class extends the DrawHandler class and provides
 * event handling specific to draw a line.
 */
import javafx.scene.input.MouseEvent;


public class LineHandler extends DrawHandler {

	/**
	 * Constructs a LineHandler object with the specified ShapeCanvas.
	 *
	 * @param sc The ShapeCanvas where shapes are drawn.
	 */
	public LineHandler(ShapeCanvas sc) {
		super(sc);
	}

	/**
	 * Overrides the mousePressed method to create a new Line object
	 * when the mouse is pressed, and sets it as the current shape.
	 *
	 * @param e The MouseEvent representing the mouse press event.
	 */
	@Override
	protected void mousePressed(MouseEvent e) {
		shape = new Line();
		super.mousePressed(e);
	}
}
