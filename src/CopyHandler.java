import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;

/**
 * An event handler for copying shapes on a ShapeCanvas.
 */
public class CopyHandler implements EventHandler<MouseEvent> {

    private ShapeCanvas canvas;
    private MyShape shape, copy;
    private double curX, curY, newX, newY, x0, y0;

    /**
     * Constructs a new CopyHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas associated with this CopyHandler.
     */
    public CopyHandler(ShapeCanvas sc) {
        canvas = sc ;
    }
    /**
     * Handles mouse pressed event.
     *
     * @param e The MouseEvent representing the mouse pressed event.
     */
    private void mousePressed(MouseEvent e) {
        x0 = e.getX();
        y0 = e.getY();
        shape = canvas.closestShape(x0, y0);

        if (shape != null) {
            curX = x0;
            curY = y0;
            copy = (MyShape) shape.clone();
            canvas.addShape(copy);
        }
    }

    /**
     * Handles mouse dragged event.
     *
     * @param e The MouseEvent representing the mouse dragged event.
     */
    private void mouseDragged(MouseEvent e) {
        if (copy != null) {
            newX = e.getX();
            newY = e.getY();
            copy.move(newX - curX, newY - curY);
            curX = newX;
            curY = newY;
            canvas.paint();
        }
    }
    
    private void mouseReleased(MouseEvent e) {
    	if (copy != null) {
    		shape = null;
    		canvas.addEdit(new CopyEdit(canvas, copy));
    	}
    }

    /**
     * Handles the mouse event.
     *
     * @param e The MouseEvent to handle.
     */
    @Override
    public void handle(MouseEvent e) {
        String eventType = e.getEventType().getName();

        switch (eventType) {
            case "MOUSE_PRESSED":
                mousePressed(e);
                break;
            case "MOUSE_DRAGGED":
                mouseDragged(e);
                break;
            case "MOUSE_RELEASED":
            	mouseReleased(e);
            	break;
            default:
                break;
        }
    }
}
