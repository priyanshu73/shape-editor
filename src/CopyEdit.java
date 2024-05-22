/**
 * Represents an edit action for copying a shape on a canvas.
 * Extends the Edit class.
 */
public class CopyEdit extends Edit {
    
    /**
     * Constructs a new CopyEdit object.
     * @param c The canvas where the edit is applied.
     * @param s The shape to be copied.
     */
    public CopyEdit(ShapeCanvas c, MyShape s) {
        super(c, s);
    }

    /**
     * Undoes the copy action by deleting the copied shape from the canvas.
     */
    @Override
    public void undo() {
        canvas.deleteShape(shape);
    }

    /**
     * Redoes the copy action by adding the copied shape back to the canvas.
     */
    @Override
    public void redo() {
        canvas.addShape(shape);
    }
}
