/**
 * The DrawHandler class implements the event handling for drawing shapes.
 * It handles mouse events such as pressed, dragged, and released to  draw shapes.
 */
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class DrawHandler implements EventHandler<MouseEvent> {

	protected MyShape shape; 
	protected ShapeCanvas canvas; 

	/**
	 * Constructs a DrawHandler object with the specified ShapeCanvas.
	 *
	 * @param sc The ShapeCanvas where shapes are drawn.
	 */
	public DrawHandler(ShapeCanvas sc) {
		canvas = sc;
	}

	/**
	 * Handles the mouse pressed event.
	 *
	 * @param e The MouseEvent representing the mouse press event.
	 */
	protected void mousePressed(MouseEvent e) {
		if (shape != null) {
			canvas.setCurrentShape(shape);
			shape.setP1(e.getX(), e.getY());
		}
	}

	/**
	 * Handles the mouse dragged event.
	 *
	 * @param e The MouseEvent representing the mouse drag event.
	 */
	protected void mouseDragged(MouseEvent e) {
		if (shape != null) {
			shape.setP2(e.getX(), e.getY());
			canvas.paint();
		}
	}

	/**
	 * Handles the mouse released event.
	 *
	 * @param e The MouseEvent representing the mouse release event.
	 */
	protected void mouseReleased(MouseEvent e) {
		if (shape != null) {
			// Ensure that P2 is not null to avoid null pointer exception
			if (shape.getP2() == null) {
				shape.setP2(shape.getP1());
			}

			canvas.addShape(shape);
			canvas.setCurrentShape(null);
		}
	}

	/**
	 * Handles the mouse events.
	 *
	 * @param event The MouseEvent to handle.
	 */
	@Override
	public void handle(MouseEvent event) {
		String eventType = event.getEventType().getName();

		switch (eventType) {
		case "MOUSE_PRESSED":
			mousePressed(event);
			break;
		case "MOUSE_DRAGGED":
			mouseDragged(event);
			break;
		case "MOUSE_RELEASED":
			mouseReleased(event);
			break;
		default:
			break;
		}
	}
}
