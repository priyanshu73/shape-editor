/**
 * The RectHandler class extends the DrawHandler class and provides
 * event handling logic used to drawing rectangle 
 *  
**/

import javafx.scene.input.MouseEvent;

public class RectHandler extends DrawHandler {

    /**
     * Constructs a RectHandler object with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas where shapes are drawn.
     */
    public RectHandler(ShapeCanvas sc) {
        super(sc);
    }

    /**
     * Overrides the mousePressed method to create a new Rect object
     * when the mouse is pressed, and sets it as the current shape.
     *
     * @param e The MouseEvent representing the mouse press event.
     */
    @Override
    protected void mousePressed(MouseEvent e) {
        shape = new Rect();
        super.mousePressed(e);
    }
}
