/* CRITTERS Main.java
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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		System.out.println("GLHF");
		try {
			Critter.makeCritter("Craig");
			Critter.makeCritter("Craig");
			Critter.makeCritter("Craig");
		} catch (InvalidCritterException e) {
			e.printStackTrace();
		}
		Critter.displayWorld();
		while (true) {
			/*for(int i = 0 ; i < (Params.world_height+2)*(Params.world_width+2); i++)
				System.out.print("\b");*/
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Critter.displayWorld();
			Critter.worldTimeStep();
		}
	}
}
