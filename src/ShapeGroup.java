import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a group of shapes.
 */
public class ShapeGroup extends MyShape {

    private ArrayList<MyShape> group;
    private static final Color color = Color.LIGHTGRAY;

    /**
     * Constructs a new ShapeGroup.
     */
    public ShapeGroup() {
        group = new ArrayList<>();
    }

    /**
     * Checks if the group is empty.
     *
     * @return True if the group is empty, false otherwise.
     */
    public boolean isEmpty() {
        return group.isEmpty();
    }

    /**
     * Returns the number of shapes in the group.
     *
     * @return The number of shapes in the group.
     */
    public int size() {
        return group.size();
    }

    /**
     * Creates and returns a copy of this ShapeGroup object.
     *
     * @return A copy of this ShapeGroup object.
     */
    @Override
    public Object clone() {
        ShapeGroup copy = (ShapeGroup) super.clone();

        copy.group = new ArrayList<>();
        MyShape shapeCopy;

        for (MyShape shape : group) {
            shapeCopy = (MyShape) shape.clone();
            copy.addMember(shapeCopy);
        }

        return copy;
    }

    /**
     * Adds a shape to the group.
     *
     * @param shape The shape to add.
     */
    public void addMember(MyShape shape) {
        if (!group.contains(shape)) {
            group.add(shape);
            updateCenter();
        }
    }

    /**
     * Removes a shape from the group.
     *
     * @param shape The shape to remove.
     */
    public void removeMember(MyShape shape) {
        group.remove(shape);
        updateCenter();
    }

    /**
     * Checks if a given shape is within the bounding box of the group.
     *
     * @param shape The shape to check.
     * @return True if the shape is within the bounding box of the group, false otherwise.
     */
    public boolean within(MyShape shape) {
        double shapeX = shape.getCenter().getX();
        double shapeY = shape.getCenter().getY();
        boolean xWithin = shapeX > this.ulx && shapeX < (this.ulx + this.width);
        boolean yWithin = shapeY > this.uly && shapeY < (this.uly + this.height);

        return xWithin && yWithin;
    }

    /**
     * Updates the center of the group based on the positions of its member shapes.
     */
    @Override
    public void updateCenter() {
        Point2D point = new Point2D(0, 0);

        for (MyShape curShape : group) {
            point = point.add(curShape.getCenter());
        }

        center = point.multiply(1.0 / size());
    }

    /**
     * Moves the group by the specified distances in the x and y directions.
     *
     * @param dx The distance to move in the x-direction.
     * @param dy The distance to move in the y-direction.
     */
    @Override
    public void move(double dx, double dy) {
    	super.move(dx, dy);
        for (MyShape shape : group) {
            shape.move(dx, dy);
        }
    }

    /**
     * Draws the group and its member shapes on the specified graphics context.
     *
     * @param g The graphics context on which to draw.
     */
    @Override
    public void draw(GraphicsContext g) {
        for (MyShape shape : group) {
            shape.draw(g);
        }
        setColor(color);
        drawBounds(g);
    }

    /**
     * Returns a string representation of the group, including its size and bounding box coordinates.
     *
     * @return A string representation of the group.
     */
    @Override
    public String toString() {
        String title = "ShapeGroup " + String.format("%d %-3.0f %-3.0f %-3.0f %-3.0f", size(), p1.getX(), p1.getY(), p2.getX(), p2.getY());

        String result = "";
        for (MyShape shape : group) {
        	
        	result = result + "\n" + shape.toString();
        }

        return title + result;
    }
}