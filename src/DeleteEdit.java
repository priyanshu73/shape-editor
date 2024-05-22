/**
 * DeleteEdit class represents an edit action to delete a shape from a canvas.
 * It extends the Edit class.
 */
public class DeleteEdit extends Edit {

    /**
     * Constructs a new DeleteEdit object.
     * 
     * @param canvas The canvas from which the shape is deleted.
     * @param shape The shape to be deleted.
     */
    public DeleteEdit(ShapeCanvas canvas, MyShape shape) {
        super(canvas, shape);
    }

    /**
     * Undo method to add the deleted shape back to the canvas.
     */
    @Override
    public void undo() {
        canvas.addShape(shape);
    }

    /**
     * Redo method to delete the shape from the canvas.
     */
    @Override
    public void redo() {
        canvas.deleteShape(shape);
    }
}
