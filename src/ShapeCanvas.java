/**
 * The ShapeCanvas class extends the Canvas class and provides functionality


 * for drawing and managing shapes on a canvas.
 */
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ShapeCanvas extends Canvas {

	private Stack<Edit> stackUndo, stackRedo;
	private GraphicsContext gc; 
	private ArrayList<MyShape> shapes; 
	private MyShape curShape; 
	private Color curColor = Color.BLACK; 
	private double width, height;
	private boolean curFilled; 

	/**
	 * Constructs a ShapeCanvas object with the specified width and height.
	 *
	 * @param width  The width of the canvas.
	 * @param height The height of the canvas.
	 */
	public ShapeCanvas(double width, double height) {
		super(width, height);
		this.width = width;
		this.height = height;
		gc = getGraphicsContext2D();
		shapes = new ArrayList<>();
		stackUndo = new Stack<>();
		stackRedo = new Stack<>();
	}

	/**
	 * Changes the status of fill, false means outline, true means .
	 *
	 * @param fill A boolean value indicating whether the shape should be filled.
	 */
	public void setCurFilled(Boolean fill) {
		curFilled = fill;
	}


	/*
	 * Set's the color of the canvas to the given color
	 * 
	 * @param col Color to be set
	 */
	public void setCurColor(Color col) {
		curColor = col;
	}
	
	/**
	 * Clears the canvas and redraws all shapes.
	 */
	public void paint() {
		// Clear canvas
		gc.clearRect(0, 0, width, height);

		// Draw all shapes
		for (MyShape s : shapes) {
			s.draw(gc);
		}

		// Draw current shape if not null
		if (curShape != null) {
			curShape.draw(gc);
		}
	}

	/**
	 * Adds a shape to the canvas and repaints.
	 *
	 * @param s The shape to be added.
	 */
	public void addShape(MyShape s) {
		shapes.add(s);
		//paint();
		//System.out.println(s.toString());
	}

	/**
	 * Sets the current shape to be drawn.
	 *
	 * @param s The shape to be set as the current shape.
	 */
	public void setCurrentShape(MyShape s) {
		curShape = s;

		if (s != null) {
			curShape.setColor(curColor);
			curShape.setFilled(curFilled);
		}
	}


	public ArrayList<MyShape> getShapes(){
		return shapes;
	}
	/**
	 * Clears all shapes from the canvas.
	 */
	public void clear() {
		shapes.clear();
		paint();
	}


	/**
	 * Writes shape data to a text file.
	 * Each shape is represented as a string in the format:
	 * "Type x1 y1 x2 y2 filled r g b"	 *
	 * @param fileObj File object representing the text file to write shape data to
	 */
	public void toTextFile(File fileObj) {
		try {
			PrintWriter fileOut = new PrintWriter(fileObj);
			fileOut.println(shapes.size());

			//get string format for each shape and write it on the file
			for (MyShape s : shapes) {
				fileOut.println(s.toString());
				//System.out.println(s.toString()); //for testing
			}

			fileOut.close();
		}
		catch(FileNotFoundException e) {
			System.out.println(fileObj.getName() + " Couldn't be opened for writing");
			e.printStackTrace();
		}
	}

	/**
	 * Reads shape data from a text file and adds shapes to the internal list.
	 * Methods loadSingletonText and loadGroupText are used depending on the shape type
	 * @param fileObj File object representing the text file with shape data
	 */

	public void fromTextFile(File fileObj) {
		try {
			Scanner fileIn = new Scanner(fileObj);

			clear();
			
			int nShapes = fileIn.nextInt();

			for( int i =0; i < nShapes; ++i) {
				String type = fileIn.next();
				MyShape shape = null;
				
				if( !type.equalsIgnoreCase("ShapeGroup")) {
					shape = loadSingletonText(fileIn, type);
				}
				
				else {
					shape = loadGroupText(fileIn);
				}
				

				shapes.add(shape);

			}

			fileIn.close();

			paint();
		}
		catch(FileNotFoundException e){
			System.out.println(fileObj.getName()+ " could not be opened for reading");
			e.printStackTrace();
		}
	}

	/**
	 * Writes the shapes data to a binary file.
	 * @param fileObj The file object to write the data to.
	 */

	public void toBinaryFile(File fileObj) {
		try {
			FileOutputStream fOS = new FileOutputStream(fileObj);
			ObjectOutputStream fOut = new ObjectOutputStream(fOS);
			fOut.writeInt(shapes.size());

			for(MyShape s : shapes) {
				fOut.writeObject(s);
			}
			
			fOS.close();
			fOut.close();
		}
		catch(IOException e){
			System.out.println(fileObj.getName()+ "could not be opened for reading");
			e.printStackTrace(); 
		}
	}


	/**
	 * Reads shapes data from a binary file.
	 * @param fileObj The file object to read the data from.
	 */

	public void fromBinaryFile(File fileObj) {

		try {
			FileInputStream fIS = new FileInputStream(fileObj);
			ObjectInputStream fIn = new ObjectInputStream(fIS);
			clear();

			int n = fIn.readInt();

			for ( int i = 0; i < n ; i ++) {
				MyShape s = (MyShape) fIn.readObject();
				shapes.add(s);
			}

			fIn.close();

			fIS.close();
			paint();

		} 


		catch (IOException e) {
			for( MyShape s : shapes)
				e.printStackTrace();
		}

		catch (ClassNotFoundException e2) {
			for( MyShape s : shapes)
				e2.printStackTrace();
		}

	}


	public MyShape closestShape(double x, double y) {
		if(shapes.size() == 0) {
			return null;
		}

		double minDistance = Double.POSITIVE_INFINITY ;
		double distance;
		MyShape closestShape = null ;

		for (MyShape shape : shapes) {
			distance = shape.distance(x,y);
			if( distance < minDistance) {
				closestShape = shape;
				minDistance = distance;
			}
		}
		return closestShape;
	}

	/**
	 * Removes the given shape object from the canvas
	 * @param s the shape object to be removed
	 */
	public void deleteShape(MyShape s) {
		shapes.remove(s);
	}

	/**
	 * Adds a new edit action to the manager for potential undoing.
	 * 
	 * @param edit The edit action to be added for undoing.
	 */
	public void addEdit(Edit edit) {
	    stackUndo.push(edit);
        stackRedo.clear(); //so that re-do can only be used right after un-do
	}

	/**
	 * Undoes the last edit action if available.
	 */
	public void undo() {
	    if (!stackUndo.empty()) {
	        Edit edit = stackUndo.pop();
	        stackRedo.push(edit);
	        edit.undo();
	    }
	}

	/**
	 * Redoes the last undone edit action if available.
	 */
	public void redo() {
	    if (!stackRedo.empty()) {
	        Edit edit = stackRedo.pop();
	        edit.redo();
	        stackUndo.push(edit);
	    }
	}

	
	/**
	 * Loads and returns a singleton shape object from the input Scanner.
	 * The shape type is determined by the provided shapeType parameter.
	 *
	 * @param fIn       The Scanner object used for reading input.
	 * @param shapeType A String indicating the type of shape to load ("line", "oval", or "rect").
	 * @return A MyShape object representing the loaded shape.
	 */

	private MyShape loadSingletonText(Scanner fIn, String shapeType) {
		int x1 = fIn.nextInt();
		int y1 = fIn.nextInt();
		int x2 = fIn.nextInt();
		int y2  = fIn.nextInt();
		Boolean fill = fIn.nextBoolean(); 
		double r    = fIn.nextDouble();
		double g    = fIn.nextDouble();
		double b    = fIn.nextDouble();
		Color  col  = Color.color(r, g, b);

		MyShape shape = null;
		
		if( shapeType.equalsIgnoreCase("line")) {
			shape = new Line(x1, y1, x2, y2);
		}

		else if (shapeType.equalsIgnoreCase("oval")){
			shape = new Oval(x1,y1,x2,y2);
		}

		else {
			shape = new Rect(x1,y1,x2,y2);				
		}
		
		shape.setFilled(fill);
		shape.setColor(col);
		
		return shape;

	}
	
	
	/**
	 * Loads and returns a ShapeGroup object from the input Scanner.
	 *
	 * @param fIn The file Scanner object used for reading input.
	 * @return A ShapeGroup object representing the loaded shape group.
	 */
	
	private ShapeGroup loadGroupText(Scanner fIn) {
		
		int n = fIn.nextInt();
		int x1 = fIn.nextInt();
		int y1 = fIn.nextInt();
		int x2 = fIn.nextInt();
		int y2  = fIn.nextInt();
		
		ShapeGroup group =  new ShapeGroup();
		group.setP1(x1, y1);
		group.setP2(x2, y2);
		
		for( int i = 0; i < n; i++) {
			String type = fIn.next();
			
			MyShape s = null;
			if(!type.equalsIgnoreCase("ShapeGroup")) {
				s = loadSingletonText(fIn, type);
			}		
			else {
				s = loadGroupText(fIn);
			}
			group.addMember(s);
		}
			
		return group;
	}
	
	
	
	/**
	 * Replaces the current mouse listener (clicked/press/release) and mouse motion listener (drag)
	 * with the passed listener object
	 *
	 * @param listener An EventHandler object.
	 */
	public void replaceMouseHandler(EventHandler listener) {
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
		setOnMouseClicked(listener);
	}
}
