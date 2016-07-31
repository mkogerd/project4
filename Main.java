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

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Java FX Critters");
			
			// Add a grid pane to lay out the buttons and text fields.
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			int row = 0;
			
			// Add Field for Critter type.
			Label critName = new Label("Critter Name (e.g. Algae):");
			grid.add(critName, 0, row);
			TextField critNameField = new TextField();
			//row++;
			grid.add(critNameField, 1, row);
			
			// Add Field for No. of Critters
			Label numCrits = new Label("No of critters:");
			row++;
			grid.add(numCrits, 0, row);
			TextField critNumField = new TextField();
			//row++;
			grid.add(critNumField, 1, row);
			
			// Add Button to add Critters.
			Button addBtn = new Button("Add critters");
			HBox hbAddBtn = new HBox(10);
			hbAddBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbAddBtn.getChildren().add(addBtn);
			row += 2;
			grid.add(hbAddBtn, 1, row);
			
			// Action when Add Critters Button is pressed.
			final Text actionTarget = new Text();
			row += 2;
			grid.add(actionTarget, 1, row);
			
			//grid.setGridLinesVisible(true);
			
			Scene scene = new Scene(grid, 500, 1000);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Action when add critters button is pressed. Call makeCritter.
			// Uses something called an anonymous class of type EventHandler<ActionEvent>, which is a class that is
			// defined inline, in the curly braces.
			addBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String name = critNameField.getText();
					String numString = critNumField.getText();
					
					// ========== ADDED CODE ==========
					try {
						for (int i = 0; i < Integer.parseInt(numString); i+=1) {
							Critter.makeCritter(name);
						}
					} catch (Exception e) {
						throw new IllegalArgumentException();
					}
					// ========== ========== ==========
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("TODO: message to display how many Critters added etc.");	
					Critter.displayWorld(); // Optional
				}			
			});
			

		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
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
