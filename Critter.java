/* CRITTERS Critter.java
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	protected String look(int direction, boolean steps) {
		return null;
	}
	
	/* rest is unchanged from Project 4 */
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	private boolean hasMoved;
	protected int getEnergy() { return energy; }
	protected void addEnergy(int addition) {energy += addition; }
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {
		// decrease energy
		energy -= Params.walk_energy_cost;
		if (!hasMoved) {
			move(direction, 1);
		}
		// ==========FUTURE STUFF ==========
		// Move bug
		// In case of fight, do special stuff
	}
	
	protected final void run(int direction) {
		energy -= Params.run_energy_cost;
		if (!hasMoved) {
			move(direction, 2);
		}
	}
	// Moves the critter in the specified direction by the specified distance
	private final void move(int direction, int distance) {
		// Move critter
		if (direction == 7 || direction == 0 || direction == 1)
			x_coord += distance;
		if (direction == 3 || direction == 4 || direction == 5)
			x_coord -= distance;
		if (direction == 5 || direction == 6 || direction == 7)
			y_coord += distance;
		if (direction == 1 || direction == 2 || direction == 3)
			y_coord -= distance;
		
		// Wrap-around correction
		if (x_coord < 0)
			x_coord += Params.world_width;
		if (x_coord >= Params.world_width)
			x_coord = Params.world_width % x_coord;
		if (y_coord < 0)
			y_coord += Params.world_height;
		if (y_coord >= Params.world_height)
			y_coord = Params.world_height % y_coord;
		
		// Raise flag
		hasMoved = true;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		offspring.energy = this.energy / 2;						// Round energy down for the baby
		this.energy = (this.energy / 2) + (this.energy % 2);	// Round energy up for the mama
		offspring.move(direction, 1);							// Spawn babies next to mamas
		babies.add(offspring);									// Add the baby!
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		Class<?> myCritter = null;
		Constructor<?> constructor = null;
		Object instanceOfMyCritter = null;
		
		try {
			myCritter = Class.forName(critter_class_name);	// Get class object corresponding to s
			constructor = myCritter.getConstructor();		// get null parameter constructor
			instanceOfMyCritter = constructor.newInstance();// create instance
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Critter me = (Critter)instanceOfMyCritter; // Cast to critter
	
		me.x_coord = getRandomInt(Params.world_width);	// Set starting coordinates
		me.y_coord = getRandomInt(Params.world_height);
		me.energy = Params.start_energy;					// Set starting energy
		population.add(me);								// Add the critter

	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		// Find class using reflection
		Class<?> myCritter = null;
		try {
			myCritter = Class.forName(critter_class_name);	// Get class object corresponding to s
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}	
		
		// Populate result array
		for (Critter c: population) 
			if (myCritter.isInstance(c))
				result.add(c);	
	
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
		
	public static void worldTimeStep() {
		// 1. Loop through all of the critters in the collection, and call doTimeStep for each
		for (Critter c : population)
			c.doTimeStep();

		// 2. doEncounters();
		doEncounters();

		// 3. Update rest energy - Do critters have to move every step? and if not - do they only rest when they don’t move. Yes, always - it’s the cost of living
		for(Critter c : population)
			c.energy -= Params.rest_energy_cost;
		
		// 4. Add algae
		for (int i = 0; i <Params.refresh_algae_count; i+=1) {
			try {
				makeCritter("project4.Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
		
		// 5. Remove dead critters
		List<Critter> dead = new ArrayList<Critter>();
		for(Critter c : population)
			if (c.energy <= 0)
				dead.add(c);			// "Bring out your dead!"
		population.removeAll(dead);		// Remove the dead
		
		// 6. add babies to population
		population.addAll(babies);		// Dump-truck crib into the real world
		babies.clear();					// Prevent immortal babies
		
		// 7. Reset move flags
		for (Critter c: population)
			c.hasMoved = false;
	}
	
	public static void doEncounters() {
		for (Critter a: population)
			for (Critter b: population) {
				if (a.equals(b)) continue;		// Stop hitting yourself
				if (a.x_coord == b.x_coord && a.y_coord == b.y_coord && a.energy > 0 && b.energy > 0) {
					// Get die rolls
					int aRoll = 0;
					int bRoll = 0;
					// Check willingness to fight
					if (a.fight(b.toString()))
						aRoll = getRandomInt(a.energy);
					if (b.fight(a.toString())) 
						bRoll = getRandomInt(b.energy);
					// Find the winner
					if (aRoll >= bRoll) {
						a.energy += b.energy/2;
						b.energy = 0;
					} 
					else {
						b.energy += a.energy/2;
						a.energy = 0;
					}
				}
			}
	}
	
	private static Stage world = new Stage();
	public static void displayWorld() {
		// Add adjustable size columns and rows according to world parameters
		GridPane grid = new GridPane();
		for(int i = 0; i<Params.world_height; i +=1){
			RowConstraints rc = new RowConstraints();
			rc.setPercentHeight(100/Params.world_height);
			rc.setValignment(VPos.CENTER);
			grid.getRowConstraints().add(rc);
		}
		for(int i = 0; i<Params.world_width; i +=1){
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(100/Params.world_width);
			cc.setHalignment(HPos.CENTER);
			grid.getColumnConstraints().add(cc);
		}


		// Set the borders to be a line rather than a gap
		grid.setGridLinesVisible(true);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(0);
		grid.setVgap(0);
		grid.setPadding(new Insets(0,0,0,0));
		System.out.println(grid.getBoundsInLocal().getWidth());
		
		Scene scene = new Scene(grid, Params.scene_x, Params.scene_y);
		// Attempt to resize a circle based on grid
		//Circle circ = new Circle(grid.getBoundsInLocal().getWidth()/Params.world_width, Color.BLUE);
		
		// Add the critters to the gym according to shape
		for (Critter c: population) {
			Shape shape = null;
			switch (c.viewShape()) {
			case CIRCLE:
				shape = new Circle(10);
				break;
			case SQUARE:
				shape = new Rectangle(20, 20);
				break;
			case TRIANGLE:
				shape = new Polygon(0, 20, 10, 0, 20, 20);
				break;
			case DIAMOND:
				shape = new Polygon(0, 10, 10, 0, 20, 10, 10, 20);
				break;
			case STAR:
				shape = new Polygon(0, 7, 6, 7, 10, 0, 14, 7, 20, 7, 14, 12, 20, 20, 10, 14, 0, 20, 6, 12);
				break;
			}
			if (shape != null) {
				shape.setFill(c.viewColor());
				shape.setStroke(c.viewOutlineColor()); 
				grid.add(shape, c.x_coord, c.y_coord);
			}
		}
		
		
		// Old stuff
		world.setScene(scene);
		world.show();
		
		System.out.print("+");
		for(int i=0; i<Params.world_width; i+=1){
			System.out.print("-");
		}
		System.out.println("+");
		
		for(int i = 0; i < Params.world_height; i+=1){
			System.out.print("|");
			for(int k = 0; k<Params.world_width; k+=1){
				// ==========================
				boolean critterPrinted = false;
				for(Critter c : population){
					if(c.x_coord == k && c.y_coord == i) {
						System.out.print(c);
						critterPrinted = true;
						break;
					}
				}
				if (!critterPrinted)
					System.out.print(" ");
				// ===========================
			}
			System.out.println("|");
		}
		
		System.out.print("+");
		for(int i=0; i<Params.world_width; i+=1){
			System.out.print("-");
		}
		System.out.println("+");
		
	}
}
