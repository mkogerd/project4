package project4;


import java.lang.reflect.Method;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controller {
	
	private static boolean running;
	public static void run() {
		try {
        	if ( running ) {
        		Critter.worldTimeStep();
        		Critter.displayWorld();
        		Thread.sleep(1000); // Sleep for 1 second divided by speed slider value
        		//System.out.println("speedField: " + speedField.getValue());
        		//System.out.println("speedField: " + 1000/speedField.getValue());
        	}
        } catch (InterruptedException e) {
            // Do nothing
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
	}
	
	// List of possible Critter names
	ObservableList<String> critterList = FXCollections.observableArrayList(
			"Craig","Ohm", "Worm", "BoxMan", "Snail", "Algae", "AlgaephobicCritter");
	
	@FXML
	private ChoiceBox makeChoiceBox;
	
	@FXML
	private ChoiceBox statsChoiceBox;

	@FXML
    private static Slider speedField; // Not working yet
	
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
    void stats() {
    List<Critter> list = null;
		
		// Get specified critter list
		try {
			String crit = "project4." + statsChoiceBox.getValue();
			System.out.println(crit);
			list = Critter.getInstances(crit);
		
			Class<?> myCritter = null;
			Method method = null;
	
			myCritter = Class.forName(crit);	// Get class object corresponding to s
	
			method = myCritter.getMethod("runStats", List.class);
			method.invoke(null, list);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
    }

}
