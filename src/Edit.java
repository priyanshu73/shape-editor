
/**
 * @author Priyanshu Pyakurel
 * @date 04/25/2024
 *  Edit is an abstract class that is a base for all the type of edit( draw, delete, move, copy and group)
 */
public abstract class Edit {

	protected MyShape shape;
	
	protected ShapeCanvas canvas;
	
	/**
	 * Constructor that takes and initializes the shape to be edited , and the associated canvas 
	 * @param c
	 * @param s
	 */
	public Edit(ShapeCanvas c, MyShape s) {
		shape = s;
		
		canvas = c;
	}
	
	/**
	 * Abstract method for undo operation
	 */
	public abstract void undo();
	
	/**
	 * Abstract method for re do operation
	 */
	public abstract void redo();
}
