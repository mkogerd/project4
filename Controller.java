package project4;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
	
	// Stats stuff
	private static Stage statsStage = new Stage();
	public static VBox statBox = new VBox();
	
	
	// Map containing elements of <classname, textfield containing stats>
	private static Map<String, Text> stats = new HashMap<String, Text>();
	private static boolean running;		// Indicates when the animation is running
	private static int speed;			// Indicates the number of timesteps per frame
	public static void run() {
		if ( running ) {
			// do timesteps according to speedfield every frame
			for (int i = 0; i < speed; i+=1)
				Critter.worldTimeStep();
			Critter.displayWorld();
			updateStats();
		}
	}
	
	// Set initial values
	@FXML
	private void initialize() {
		// Initialize Choice boxes
		makeChoiceBox.setValue("Craig");
		makeChoiceBox.setItems(critterList);
		statsChoiceBox.setValue("Craig");
		statsChoiceBox.setItems(critterList);
		
		// Initial speed
		speed = (int)speedField.getValue();
		
		// Set up stats window
		for (String s: critterList) {
			stats.put(s, new Text(s + "stats N/A"));
		}
		Group root = new Group();
	    Scene scene = new Scene(root, 500, 150, Color.WHITE);
	    root.getChildren().add(statBox);
        statsStage.setTitle("CritterStats");
        statsStage.setScene(scene);
        statsStage.show();
		
	}
	
	private static void updateStats() {
		
		for (String s: critterList) {
			List<Critter> list = null;
			try {
				String crit = "project4." + s;
				list = Critter.getInstances(crit);
			
				Class<?> myCritter = null;
				Method method = null;
				
				myCritter = Class.forName(crit);	// Get class object corresponding to s
				method = myCritter.getMethod("runStats2", List.class);
				String statString = (String)method.invoke(null, list);			
				stats.get(s).setText(statString);
				
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
	}
	
	static // List of possible Critter names
	ObservableList<String> critterList = FXCollections.observableArrayList(
			"Craig","Ohm", "Worm", "BoxMan", "Snail", "Algae", "AlgaephobicCritter");
	
	
	@FXML
	private ChoiceBox makeChoiceBox;
	
	@FXML
	private ChoiceBox statsChoiceBox;

	@FXML
    private Slider speedField;
	
    @FXML
    private TextField stepField;
    
    @FXML
    private TextField makeField;

    @FXML
    private TextField seedField;

    @FXML
    void startRunning(ActionEvent event) {
    	running = true;
    }
    
    @FXML
    void stopRunning(ActionEvent event) {
    	running = false;
    }
    
    @FXML
    void changeSpeed(MouseEvent event) {
    	double var = speedField.getValue();
    	System.out.println("Slid to: " + var);
    	speed = (int)speedField.getValue();			// set the speed
    }
    
    @FXML
    void onQuitClick(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onSet_SeedClick(ActionEvent event) {
		Critter.setSeed(Long.parseLong(seedField.getText()));				// Apply the new seed
    }

    @FXML
    void takeSteps(ActionEvent event) {
		try {
			for(int i = 0; i < Integer.parseInt(stepField.getText()); i+=1){
				Critter.worldTimeStep();
			}
		} catch (Exception e){
				throw new IllegalArgumentException();
		}
		Critter.displayWorld();
    }
    
    @FXML
    void addCritters(ActionEvent event) {
    	String name = "project4." + makeChoiceBox.getValue();				// Type of critter to make
		try {
			for (int i = 0; i < Integer.parseInt(makeField.getText()); i+=1)// Make all the critters
				Critter.makeCritter(name);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		Critter.displayWorld(); // Optional
    }
    
    @FXML
    void addStats(ActionEvent event) {
    	String critString = (String) statsChoiceBox.getValue();	// Get specified Critter name
    	Text critText = stats.get(critString);
    	if (!statBox.getChildren().contains(critText)) 			// If the main vbox doesn't contain the selected critter stat
			statBox.getChildren().add(critText);
    }
    
    @FXML
    void removeStats(ActionEvent event) {
    	String critString = (String) statsChoiceBox.getValue();	// Get specified Critter name
    	Text critText = stats.get(critString);
    	if (statBox.getChildren().contains(critText)) 			// If the main vbox does contain the selected critter stat
			statBox.getChildren().remove(critText);
    }

}
