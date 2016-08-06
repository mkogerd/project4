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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
/* ========== Pre Java-FX stuff ==========
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String[] cmd = new String[0];
		String line = null;
		while(cmd.length == 0 || !cmd[0].equals("quit")) {
			line = kb.nextLine();
			cmd = line.split(" "); // Get user command
			try {
				if(cmd.length != 0) {
					switch (cmd[0]) {
					case "show" :
						Critter.displayWorld();
						break;
					case "step" :
						step(cmd);
						break;
					case "seed" :
						Critter.setSeed(Integer.parseInt(cmd[1]));
						break;
					case "make" :
						make(cmd[1], Integer.parseInt(cmd[2]));
						break;
					case "stats" :
						stats(cmd[1]);
						break;
					case "quit" :
						break;
					default :
						throw new Exception();
					}
				}
			}
			catch (IllegalArgumentException e) {
				System.out.println("Error Processing: " + line);
			}
			catch (Exception e) {
				System.out.println("Invalid Command: " + line);
			} 
		}
		kb.close();
	}
	
	public static void step (String[] cmd) {
		if (cmd.length == 1)
			Critter.worldTimeStep();
		else if(cmd.length == 2) {
			try {
			for(int i =0; i < Integer.parseInt(cmd[1]); i+=1)
				Critter.worldTimeStep();
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException();
			}
		}
		else throw new IllegalArgumentException();
	}
	
	// Function to make a new Critter
	public static void make(String name, int n) {
		try {
			for (int i = 0; i < n; i+=1) {
				Critter.makeCritter(name);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	// Function to display stats
	public static void stats(String critter_class_name) {
		
		List<Critter> list = null;
		
		// Get specified critter list
		try {
			list = Critter.getInstances(critter_class_name);
		
			Class<?> myCritter = null;
			Method method = null;

			myCritter = Class.forName(critter_class_name);	// Get class object corresponding to s

			method = myCritter.getMethod("runStats", List.class);
			method.invoke(null, list);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		
	}
}
/**/
