import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;

/**
 * An event handler for copying shapes on a ShapeCanvas.
 */
public class CopyHandler implements EventHandler<MouseEvent> {

    private ShapeCanvas canvas;
    private MyShape shape, copy;
    private double curX, curY, newX, newY;

    /**
     * Constructs a new CopyHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas associated with this CopyHandler.
     */
    public CopyHandler(ShapeCanvas sc) {
        canvas = sc;
    }

    /**
     * Handles mouse pressed event.
     *
     * @param e The MouseEvent representing the mouse pressed event.
     */
    private void mousePressed(MouseEvent e) {
        curX = e.getX();
        curY = e.getY();
        shape = canvas.closestShape(curX, curY);

        if (shape != null) {
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
            default:
                break;
        }
    }
}
