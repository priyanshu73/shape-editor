import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * An event handler for grouping shapes on a ShapeCanvas.
 */
public class GroupHandler implements EventHandler<MouseEvent> {

    private ShapeCanvas canvas;
    private ShapeGroup shapeGroup;

    /**
     * Constructs a new GroupHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas associated with this GroupHandler.
     */
    public GroupHandler(ShapeCanvas sc) {
        canvas = sc;
    }

    /**
     * Handles mouse pressed event.
     *
     * @param e The MouseEvent representing the mouse pressed event.
     */
    private void mousePressed(MouseEvent e) {
        shapeGroup = new ShapeGroup();
        canvas.setCurrentShape(shapeGroup);
        shapeGroup.setP1(e.getX(), e.getY());
    }

    /**
     * Handles mouse dragged event.
     *
     * @param e The MouseEvent representing the mouse dragged event.
     */
    private void mouseDragged(MouseEvent e) {
        if (shapeGroup != null) {
            shapeGroup.setP2(e.getX(), e.getY());
            canvas.paint();
        }
    }

    /**
     * Handles mouse released event.
     *
     * @param e The MouseEvent representing the mouse released event.
     */
    private void mouseReleased(MouseEvent e) {
        ArrayList<MyShape> shapes = canvas.getShapes();

        int i = 0;
        while (shapes.size() > 0 && i < shapes.size()) {
            MyShape shape = shapes.get(i);
            if (shapeGroup.within(shape)) {
                canvas.deleteShape(shape);
                shapeGroup.addMember(shape);
            }
            else {
            	++i;
            }
            
        }

        if (!shapeGroup.isEmpty()) {
            canvas.addShape(shapeGroup);
            canvas.addEdit(new GroupEdit(canvas,shapeGroup));
            canvas.setCurrentShape(null);
        } 
        else {
            canvas.setCurrentShape(null);
            canvas.paint(); // to remove the group from canvas if it has no members
        }
    }

    /**
     * Handles the mouse event.
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