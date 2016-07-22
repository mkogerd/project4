/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Summer 2016
 */
package project4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	
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
		Critter temp;
		switch(critter_class_name){
		case "Craig" :
			temp = new Craig();
			break;
		case "Algae" :
			temp = new Algae();
			break;
		case "Worm" :
			temp = new Worm();
			break;
		case "Snail" :
			temp = new Snail();
			break;
		default :
			throw new InvalidCritterException(critter_class_name);		
		}
		temp.x_coord = getRandomInt(Params.world_width);	// Set starting coordinates
		temp.y_coord = getRandomInt(Params.world_height);
		temp.energy = Params.start_energy;					// Set starting energy
		population.add(temp);								// Add the critter

	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		for (Critter c: population) {
			switch (critter_class_name) {
			case "project4.Craig":
				if (c.toString().equals("C"))
					result.add(c);
				break;
			case "project4.Algae":
				if (c.toString().equals("@"))
					result.add(c);
				break;
			case "project4.Worm":
				if (c.toString().equals("~"))
					result.add(c);
				break;
			case "project4.Snail":
				if (c.toString().equals("S"))
					result.add(c);
				break;
			default : 
				throw new InvalidCritterException(critter_class_name);
			}
		}
			
	
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
//		   0. timestep++;
		
		// 1. Loop through all of the critters in the collection, and call doTimeStep for each
		for (Critter c : population)
			c.doTimeStep();

		// 2. doEncounters();
		doEncounters();

//		   3. Update rest energy - Do critters have to move every step? and if not - do they only rest when they don’t move. Yes, always - it’s the cost of living
		for(Critter c : population)
			c.energy -= Params.rest_energy_cost;
		
//		   4. Add algae
		for (int i = 0; i <Params.refresh_algae_count; i+=1) {
			try {
				makeCritter("Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
		
//		   5. Remove dead critters
		List<Critter> dead = new ArrayList<Critter>();
		for(Critter c : population)
			if (c.energy <= 0)
				dead.add(c);			// "Bring out your dead!"
		population.removeAll(dead);		// Remove the dead
		
//		   6. add babies to population
		population.addAll(babies);		// Dump-truck crib into the real world
		babies.clear();					// Prevent immortal babies
		
//		   7. Reset move flags
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
	
	public static void displayWorld() {
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
