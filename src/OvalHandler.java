
/**
 * The OvalHandler class extends the DrawHandler class and provides
 * event handling logic used to draw ovals.
 */
import javafx.scene.input.MouseEvent;

public class OvalHandler extends DrawHandler {

	/**
	 * Constructs an OvalHandler object with the specified ShapeCanvas.
	 *
	 * @param sc The ShapeCanvas where shapes are drawn.
	 */
	public OvalHandler(ShapeCanvas sc) {
		super(sc);
	}

	/**
	 * Overrides the mousePressed method to create a new Oval object
	 * when the mouse is pressed, and sets it as the current shape.
	 *
	 * @param e The MouseEvent representing the mouse press event.
	 */
	@Override
	protected void mousePressed(MouseEvent e) {
		shape = new Oval();
		super.mousePressed(e);
	}
}
