/**
 * DrawEdit class represents an edit action to draw a shape on a canvas.
 * It extends the Edit class.
 */
public class DrawEdit extends Edit {

    /**
     * Constructs a new DrawEdit object.
     * 
     * @param canvas The canvas on which the shape is drawn.
     * @param shape The shape to be drawn.
     */
    public DrawEdit(ShapeCanvas canvas, MyShape shape) {
        super(canvas, shape);
    }

    /**
     * Undo method to remove the drawn shape from the canvas.
     */
    @Override
    public void undo() {
        canvas.deleteShape(shape);
    }

    /**
     * Redo method to add the drawn shape back to the canvas.
     */
    @Override
    public void redo() {
        canvas.addShape(shape);
    }
}
