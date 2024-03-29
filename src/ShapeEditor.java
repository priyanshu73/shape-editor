/**
 * ShapeEditor is a JavaFX application for drawing shapes on a canvas.
 * It provides functionality for drawing lines, ovals, and rectangles,
 * with options to fill or outline the shapes.
 *
 * @author Priyanshu Pyakurel
 * @date 03/28/28
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShapeEditor extends Application{

	/*CONSTANTS*/
	private static final int APP_WIDTH      = 800;
	private static final int APP_HEIGHT     = 700;
	private static final int CONTROL_HEIGHT =  40; 
	private static final int CANVAS_HEIGHT  = APP_HEIGHT - CONTROL_HEIGHT;


	/** GUI variable */
	private BorderPane mainPane;
	private HBox controlPanel;
	private ShapeCanvas canvas;
	private RadioButton rbLine, rbOval, rbRect;
	private Button bnClear;
	private CheckBox cbFilled;
	private LineHandler lineHandler;
	private OvalHandler ovalHandler;
	private RectHandler rectHandler;


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
		cbFilled = new CheckBox("Filled");
		rbLine = new RadioButton("Line");
		rbOval = new RadioButton("Oval");
		rbRect = new RadioButton("Rect");


		lineHandler = new LineHandler(canvas);
		ovalHandler = new OvalHandler(canvas);
		rectHandler = new RectHandler(canvas);

		rbLine.setSelected(true);

		ToggleGroup group = new ToggleGroup();
		rbLine.setToggleGroup(group);
		rbOval.setToggleGroup(group);
		rbRect.setToggleGroup(group);

		cbFilled.setOnAction(e -> {
			if(cbFilled.isSelected()) {
				canvas.setCurFilled(true);
			}
			else {
				canvas.setCurFilled(false);
			}
		});


		if(rbLine.isSelected()) {
			canvas.replaceMouseHandler(lineHandler);
		}

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

		bnClear = new Button("Clear");
		bnClear.setOnAction(e -> {	
			canvas.clear();
		});


		controlPanel.getChildren().addAll(bnClear, cbFilled, rbLine, rbOval,rbRect);

		mainPane.setTop(controlPanel);
	}

	/**
	 * Sets up the canvas with the given dimensions and adds it to the mainPane.
	 */

	private void setupCanvas() {
		canvas = new ShapeCanvas(APP_WIDTH, CANVAS_HEIGHT);
		mainPane.setCenter(canvas);
	}
}
