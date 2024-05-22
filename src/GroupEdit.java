/**
 * Represents an edit action for grouping shapes on a canvas.
 * Extends the Edit class.
 */
public class GroupEdit extends Edit {

    /** The shape group being edited. */
    ShapeGroup sg;

    /**
     * Constructs a new GroupEdit object.
     * @param c The canvas where the edit is applied.
     * @param s The shape to be grouped.
     */
    public GroupEdit(ShapeCanvas c, MyShape s) {
        super(c, s);
    }

    /**
     * Undoes the grouping action by un-grouping the shapes.
     */
    @Override
    public void undo() {
        sg = (ShapeGroup) shape;
        if (sg.size() != 0) {
            for (MyShape shape : sg.getMembers()) {
                canvas.addShape(shape);
            }
            canvas.deleteShape(sg);
            canvas.paint();
        }
    }

    /**
     * Re-does the grouping action by regrouping the shapes.
     */
    @Override
    public void redo() {
        sg = (ShapeGroup) shape;
        for (MyShape shape : sg.getMembers()) {
            canvas.deleteShape(shape);
        }
        canvas.addShape(sg);
        canvas.paint();
    }
}
