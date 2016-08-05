package project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    	System.out.println("Seed setting...");
    	long newSeed = Long.parseLong(seedField.getText());		// Convert the new seed string to a long
		Critter.setSeed(newSeed);								// Apply the new seed
		Critter.displayWorld(); // Optional
		System.out.println("Seed Set!");
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
    	String name = "project4.Craig";						// Type of critter to make (TEMPORARILY JUST CRAIG)
		try {
			for (int i = 0; i < Integer.parseInt(makeField.getText()); i+=1)// Make all the critters
				Critter.makeCritter(name);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		Critter.displayWorld(); // Optional
    }

}
