/**
 * MoveEdit class represents an edit action to move a shape on a canvas.
 * It extends the Edit class.
 */
public class MoveEdit extends Edit {

	private double dx; 
	private double dy; 

	/**
	 * Constructs a new MoveEdit object.
	 * 
	 * @param canvas The canvas on which the shape is moved.
	 * @param shape The shape to be moved.
	 * @param dx The change in x-coordinate for the movement.
	 * @param dy The change in y-coordinate for the movement.
	 */
	public MoveEdit(ShapeCanvas canvas, MyShape shape, double dx, double dy) {
		super(canvas, shape);
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Undo method to revert the move.
	 */
	@Override
	public void undo() {
		shape.move(-dx, -dy);
	}

	/**
	 * Redo method to re apply the move.
	 */
	@Override
	public void redo() {
		shape.move(dx, dy);
	}
}
