/**
 * ShapeEditor is a JavaFX application for drawing shapes on a canvas.
 * It provides functionality for drawing lines, ovals, and rectangles, as well as shape group
 * with options to fill or outline the shapes.
 * It also has the menu bar with saving/ loading files in txt/paf format
 *
 * @author Priyanshu Pyakurel
 * @date 04/18/24
 */
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ShapeEditor extends Application{

	/*CONSTANTS*/
	private static final int APP_WIDTH      = 900;
	private static final int APP_HEIGHT     = 700;
	private static final int CONTROL_HEIGHT =  40; 
	private static final int CANVAS_HEIGHT  = APP_HEIGHT - CONTROL_HEIGHT;


	/** GUI variable */
	private BorderPane mainPane;
	private HBox controlPanel;
	private ShapeCanvas canvas;
	private RadioButton rbLine, rbOval, rbRect, rbDelete, rbMove, rbCopy, rbGroup;
	private Button bnClear, undo,redo;
	private CheckBox cbFilled;
	private LineHandler lineHandler;
	private OvalHandler ovalHandler;
	private RectHandler rectHandler;
	private MoveHandler moveHandler;
	private CopyHandler copyHandler;
	private GroupHandler groupHandler;
	
	

	private DeleteHandler deleteHandler;
	private ColorPicker colorPicker;

	private Color curColor = Color.BLACK;
	private MenuBar menuBar;
	private Menu menuFile, menuAbout;
	private MenuItem miLoad, miSave, miLoadB, miSaveB;
	private FileChooser fcLoad, fcSave;



	@Override
	/**
	 * Initializes and sets up the main components of the application.
	 * This includes creating the canvas and controls, setting up the scene,
	 * and displaying the main stage.
	 *
	 * @param stage The main stage of the application.
	 */
	public void start(Stage stage) {
		mainPane = new BorderPane();
		setupCanvas();
		setupMenu();
		setupControls();

		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("SHAPE EDITOR");

		stage.show();
		canvas.paint();
	}

	/**
	 * Sets up the control panel, and canvas as well the functionality for the control Panel elements using specific Line Handlers.
	 */
	private void setupControls() {
		controlPanel = new HBox(10);

		//HBox elements
		
		cbFilled = new CheckBox("Filled");

		rbLine = new RadioButton("Line");
		
		rbOval = new RadioButton("Oval");
		
		rbRect = new RadioButton("Rect");		
		
		rbMove = new RadioButton("Move");
		
		rbCopy = new RadioButton("Copy");
		
		rbDelete = new RadioButton("Delete");
		
		rbGroup = new RadioButton("Group");

		bnClear = new Button("Clear");
		
		colorPicker = new ColorPicker(curColor);
		
		undo = new Button("undo");
		
		redo = new Button("redo");
		
		//Handlers

		lineHandler = new LineHandler(canvas);
		
		ovalHandler = new OvalHandler(canvas);
		
		rectHandler = new RectHandler(canvas);
		
		deleteHandler = new DeleteHandler(canvas);
		
		moveHandler = new MoveHandler(canvas);

		copyHandler = new CopyHandler(canvas);
		
		groupHandler = new GroupHandler(canvas);


		

		ToggleGroup group = new ToggleGroup();
		rbLine.setToggleGroup(group);
		rbOval.setToggleGroup(group);
		rbRect.setToggleGroup(group);
		rbMove.setToggleGroup(group);
		rbDelete.setToggleGroup(group);
		rbCopy.setToggleGroup(group);
		rbGroup.setToggleGroup(group);
		
		cbFilled.setOnAction(e -> {
			canvas.setCurFilled(cbFilled.isSelected());
		});

		rbLine.setSelected(true);
		canvas.replaceMouseHandler(lineHandler);


		rbLine.setOnAction(e -> {
			if(rbLine.isSelected()) {
				canvas.replaceMouseHandler(lineHandler);
			}
		});

		rbOval.setOnAction(e -> {
			if(rbOval.isSelected()) {
				canvas.replaceMouseHandler(ovalHandler);
			}
		});

		rbRect.setOnAction(e -> {
			if(rbRect.isSelected()) {
				canvas.replaceMouseHandler(rectHandler);
			}
		});


		bnClear.setOnAction(e -> {	
			canvas.clear();
		});


		undo.setOnAction(e -> {
			canvas.undo();
			canvas.paint();
		});
		
		redo.setOnAction(e ->{
			canvas.redo();
			canvas.paint();
		});

		colorPicker.setOnAction(e ->{
			curColor = colorPicker.getValue();
			canvas.setCurColor(curColor);
		});

		rbMove.setOnAction(e -> {
			if( rbMove.isSelected()) {
				canvas.replaceMouseHandler(moveHandler);
			}
		});


		rbDelete.setOnAction(e -> {
			if( rbDelete.isSelected()) {
				canvas.replaceMouseHandler(deleteHandler);
			}
		});
		
		rbCopy.setOnAction(e -> {
			if(rbCopy.isSelected()) {
				canvas.replaceMouseHandler(copyHandler);
			}
		});
		
		rbGroup.setOnAction(e -> {
			canvas.replaceMouseHandler(groupHandler);
		});

		controlPanel.getChildren().addAll(bnClear, cbFilled, rbLine, rbOval, rbRect, rbDelete, rbMove, rbCopy, rbGroup, colorPicker, undo,redo);

		controlPanel.setPrefHeight(CONTROL_HEIGHT);
		mainPane.setTop(controlPanel);
	}

	/**
	 * sets up the menu by adding options for saving the file in text, as well as binary format
	 */
	private void setupMenu() {
		menuBar   = new MenuBar();

		menuFile  = new Menu("File");
		menuAbout = new Menu("About");

		miLoad    = new MenuItem("Load");
		miSave    = new MenuItem("Save");

		miLoadB   = new MenuItem("Load in Binary");
		miSaveB   = new MenuItem("Save in Binary");

		fcLoad = new FileChooser();

		fcLoad.setTitle("Load a drawing");


		miLoad.setOnAction(e ->{
			File file = fcLoad.showOpenDialog(null);
			if (file!=null) {
				canvas.fromTextFile(file);
			}
		});

		fcSave = new FileChooser();
		fcSave.setTitle("Save current drawing as");
		//action handlers 

		miSave.setOnAction(e ->{
			File file = fcSave.showSaveDialog(null);
			if( file!=null) {
				canvas.toTextFile(file);
			}
		});

		miLoadB.setOnAction( e -> {
			File selectedFile = fcLoad.showOpenDialog(null);

			if (selectedFile != null) {

				canvas.fromBinaryFile(selectedFile);
			}
		});


		miSaveB.setOnAction( e -> {
			File selectedFile = fcLoad.showSaveDialog(null);

			if (selectedFile != null) {

				canvas.toBinaryFile(selectedFile);
			}
		});

		menuBar.getMenus().addAll(menuFile, menuAbout);
		menuFile.getItems().addAll(miLoad, miSave, miLoadB, miSaveB);

		mainPane.setLeft(menuBar);
	}

	/**
	 * Sets up the canvas with the given dimensions and adds it to the mainPane.
	 */

	private void setupCanvas() {
		canvas = new ShapeCanvas(APP_WIDTH, CANVAS_HEIGHT);
		mainPane.setCenter(canvas);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
