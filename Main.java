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
	private boolean running = false;
	
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
            @Override public void handle(long currentNanoTime) {
                Controller.run();
            }
        }.start();
	}

	/*	OLD STUFF
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
			
			// Add Buttons to run and stop Simulation.
			Button runBtn = new Button("Run");
			Button stopBtn = new Button("Stop");
			HBox hbSimBtns = new HBox(10);
			hbSimBtns.setAlignment(Pos.CENTER);
			hbSimBtns.getChildren().add(runBtn);
			hbSimBtns.getChildren().add(stopBtn);
			//row += 2;
	
			grid.add(hbSimBtns, 0, row);
			grid.setColumnSpan(hbSimBtns, 2);
			
			// Add Field for Critter type.
			row++;
			Label critName = new Label("Critter Name (e.g. Algae):");
			grid.add(critName, 0, row);
			TextField critNameField = new TextField();
			grid.add(critNameField, 1, row);
			
			// Add Field for No. of Critters
			Label numCrits = new Label("Number of critters:");
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
			final Text addActionTarget = new Text();
			hbAddBtn.getChildren().add(0, addActionTarget);
			
			// Add Field for seed No.
			Label seed = new Label("Seed:");
			row++;
			grid.add(seed, 0, row);
			TextField seedField = new TextField();
			//row++;
			grid.add(seedField, 1, row);
			
			// Add Button to change Seed.
			Button seedBtn = new Button("Set Seed");
			HBox hbSeedBtn = new HBox(10);
			hbSeedBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbSeedBtn.getChildren().add(seedBtn);
			row += 2;
			grid.add(hbSeedBtn, 1, row);
			
			// Action when Add Critters Button is pressed.
			final Text seedActionTarget = new Text();
			hbSeedBtn.getChildren().add(0, seedActionTarget);
			
			//Label for steps to take and text box for user to input how many steps to take
			Label stepName = new Label("Number of steps to take:");
			row++;
			grid.add(stepName,  0, row);
			TextField stepNameField = new TextField();
			grid.add(stepNameField, 1, row);
			
			//Add button to take steps
			Button stepBtn = new Button("Take steps");
			HBox hbstepBtn = new HBox(10);
			hbstepBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbstepBtn.getChildren().add(stepBtn);
			row += 2;
			grid.add(hbstepBtn, 1, row);
			
			grid.setGridLinesVisible(true);
			
			Scene scene = new Scene(grid, 500, 1000);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// =============== Animation ===============
			new AnimationTimer() {
	            @Override public void handle(long currentNanoTime) {
	                try {
	                	if ( running ) {
	                		Critter.worldTimeStep();
	                		Critter.displayWorld();
	                		Thread.sleep(100);
	                	}
	                } catch (InterruptedException e) {
	                    // Do nothing
	                }
	            }
	        }.start();
			
	        // ================ Action Event Handlers ===============
			// Action when run button is pressed. sets a flag. IN PROGRESS
			runBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					running = true;
				}			
			});
			
			// Action when stop button is pressed. sets a flag. IN PROGRESS
			stopBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					running = false;
				}			
			});
			
			// Action when add critters button is pressed. Call makeCritter specified # of times.
			// Uses something called an anonymous class of type EventHandler<ActionEvent>, which is a class that is
			// defined inline, in the curly braces.
			addBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String name = critNameField.getText();				// Type of critter to make
					int num = Integer.parseInt(critNumField.getText());	// Number of critters to make
					try {
						for (int i = 0; i < num; i+=1)					// Make all the critters
							Critter.makeCritter(name);
					} catch (Exception e) {
						throw new IllegalArgumentException();
					}
					addActionTarget.setFill(Color.FIREBRICK);
					addActionTarget.setText(num + " " + name + " critters added");	
					Critter.displayWorld(); // Optional
				}			
			});
			
			// Action when set seed button is pressed. Call setSeed.
			seedBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					long newSeed = Long.parseLong(seedField.getText());		// Convert the new seed string to a long
					Critter.setSeed(newSeed);								// Apply the new seed
					seedActionTarget.setFill(Color.FIREBRICK);				// Font color
					seedActionTarget.setText("Seed set to " + newSeed);		// Print change
					Critter.displayWorld(); // Optional
				}			
			});
			
			// Action when set take steps button is pressed. Call step specified # of times.
			stepBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					String stepNumber = stepNameField.getText();
					try {
						for(int i = 0; i < Integer.parseInt(stepNumber); i+=1){
							Critter.worldTimeStep();
						}
					} catch (Exception e){
							throw new IllegalArgumentException();
					}
					Critter.displayWorld();
				}
			});
			
			// Infinite loop for "running" animation

		} catch(Exception e) {
			e.printStackTrace();		
		}
		*/
	
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
