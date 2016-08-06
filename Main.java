/* CRITTERS <MyClass.java>
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Michael Darden
 * MKD788
 * 76550
 * Lei Liu
 * LL28379
 * 76550
 * Slip days used: <0>
 * Summer 2016
 */

package project4;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Utilizing the new controller
	@Override
	public void start(Stage primaryStage) {
		
		try {
            TitledPane page = (TitledPane) FXMLLoader.load(Main.class.getResource("Controller.fxml"));
            Scene scene = new Scene(page);  
            primaryStage.setTitle("FXML is Simple");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		// =============== Animation ===============
		new AnimationTimer() {
			private long lastUpdate = 0;
            @Override public void handle(long currentNanoTime) {
            	if (currentNanoTime - lastUpdate >= 1000000000/Params.fps) {	// Running at 1 FPS
            		Controller.run();
            		lastUpdate = currentNanoTime;
            	}
            }
        }.start();
	}
}