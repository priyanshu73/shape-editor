import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * An event handler for moving the closest shape to mouse click on a ShapeCanvas.
 */
public class MoveHandler implements EventHandler<MouseEvent> {

    private ShapeCanvas canvas;
    private MyShape closestShape;
    private double x0, y0, x1, y1, clickX, clickY;

    /**
     * Constructs a new MoveHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas associated with this MoveHandler.
     */
    public MoveHandler(ShapeCanvas sc) {
        canvas = sc;
    }

    /**
     * Handles mouse pressed event.
     *
     * @param e The MouseEvent representing the mouse pressed event.
     */
    private void mousePressed(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        closestShape = canvas.closestShape(clickX, clickY);
        if (closestShape != null) {
            x0 = clickX;
            y0 = clickY;
        }
    }

    /**
     * Handles mouse dragged event.
     *
     * @param e The MouseEvent representing the mouse dragged event.
     */
    private void mouseDragged(MouseEvent e) {
        if (closestShape != null) {
            x1 = e.getX();
            y1 = e.getY();
            closestShape.move(x1 - x0, y1 - y0);
            x0 = x1;
            y0 = y1;
            canvas.paint();
        }
    }
    
    private void mouseReleased(MouseEvent e) {
    	if (closestShape != null) {
    		canvas.addEdit(new MoveEdit(canvas,closestShape,x0 - clickX, y0 - clickY));
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
