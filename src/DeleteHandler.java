import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;

/**
 * An event handler for deleting the closest shape to mouse click on a ShapeCanvas.
 */
public class DeleteHandler implements EventHandler<MouseEvent> {

    private ShapeCanvas canvas;

    /**
     * Constructs a new DeleteHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas associated with this DeleteHandler.
     */
    public DeleteHandler(ShapeCanvas sc) {
        canvas = sc;
    }

    /**
     * Handles the mouse event click to delete the closest shape.
     *
     * @param e The MouseEvent to handle.
     */
    @Override
    public void handle(MouseEvent e) {
        String eventType = e.getEventType().getName();

        if ("MOUSE_CLICKED".equals(eventType)) {
            MyShape shape = canvas.closestShape(e.getX(), e.getY());
            if(shape != null) {
                canvas.deleteShape(shape);
                canvas.addEdit(new DeleteEdit(canvas,shape));
            }
            canvas.paint();
            
        }
    }
}
