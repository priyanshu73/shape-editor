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

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class ShapeCanvas extends Canvas {

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
	}

	/**
	 * Changes the status of fill, false means outline, true means .
	 *
	 * @param fill A boolean value indicating whether the shape should be filled.
	 */
	public void setCurFilled(Boolean fill) {
		curFilled = fill;
	}


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
	 * The file should contain lines in the format:
	 * "Type x1 y1 x2 y2 filled r g b"
	 * @param fileObj File object representing the text file with shape data
	 */

	public void fromTextFile(File fileObj) {
		try {
			Scanner fileIn = new Scanner(fileObj);

			clear();
			int nShapes = fileIn.nextInt();
			
			for( int i =0; i < nShapes; ++i) {
				String type = fileIn.next();
				int x1 = fileIn.nextInt();
				int y1 = fileIn.nextInt();
				int x2 = fileIn.nextInt();
				int y2  = fileIn.nextInt();
				Boolean fill = fileIn.nextBoolean(); 
				double r    = fileIn.nextDouble();
				double g    = fileIn.nextDouble();
				double b    = fileIn.nextDouble();
				Color  col  = Color.color(r, g, b);

				MyShape shape = null;

				if( type.equalsIgnoreCase("line")) {
					shape = new Line(x1, y1, x2, y2);
					shape.setFilled(fill);
					shape.setColor(col);
				}

				else if (type.equalsIgnoreCase("oval")){
					shape = new Oval(x1,y1,x2,y2);
					shape.setFilled(fill);
					shape.setColor(col); 
				}

				else {
					shape = new Rect(x1,y1,x2,y2);
					shape.setFilled(fill);
					shape.setColor(col);
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

	/**
	 * Replaces the current mouse listener (press/release) and mouse motion listener (drag)
	 * with the passed listener object.
	 *
	 * @param listener An EventHandler object.
	 */
	public void replaceMouseHandler(EventHandler listener) {
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
	}
}
