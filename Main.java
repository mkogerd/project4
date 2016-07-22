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
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String[] cmd = new String[0];
		
		while(cmd.length == 0 || !cmd[0].equals("quit")) {
			cmd = kb.nextLine().split(" "); // Get user command
			if(cmd.length != 0) {
				switch (cmd[0]) {
				case "show" :
					Critter.displayWorld();
					break;
				case "step" :
					Critter.worldTimeStep();
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
					System.out.println("Invalid Command");
				}
			}
		}
		kb.close();
	}
	
	// Function to make a new Critter
	public static void make(String name, int n) {
		try {
			for (int i = 0; i < n; i+=1) {
				Critter.makeCritter(name);
			}
		} catch (InvalidCritterException e) {
			System.out.println("Invalid Critter");
		}
	}
	
	// Function to display stats
	public static void stats(String critter_class_name) {
		
		List<Critter> list = null;
		
		// Get specified critter list
		try {
			list = Critter.getInstances(critter_class_name);
		} catch (InvalidCritterException e) {
			System.out.println("Invalid Critter Name");
		}
		
		// Print critter stats
		switch (critter_class_name) {
		case "project4.Craig":
			Craig.runStats(list);
			break;
		case "project4.BoxMan":
			BoxMan.runStats(list);
			break;
		case "project4.Worm":
			Worm.runStats(list);
			break;
		case "project4.Snail":
			Snail.runStats(list);
			break;
		case "project4.Ohm":
			Ohm.runStats(list);
			break;
		}
		
	}
}
