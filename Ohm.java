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

import project4.Critter.CritterShape;

/*
 * When the ohm critter gets low on energy, it will abstain from moving for 5 turns, causing it to gain 
 * energy so that it may walk for 5 more turns afterwards. The cycle repeats indefinitely, so something
 * needs to eat the critter for it to die. It also takes a whopping 200 energy to reproduce.
 */
public class Ohm extends Critter {
	
	@Override
	public String toString() { return "Ω"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	private int sleepy;
	
	public Ohm() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
		sleepy = 0;
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward or rest*/
		if(sleepy > 0) {
			addEnergy(Params.rest_energy_cost + Params.walk_energy_cost);
			sleepy--;
		}
		else {
			walk(dir);
		}
		
		if (getEnergy() > 200) {
			Ohm child = new Ohm();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		dir = (dir + turn) % 8;

		if(getEnergy() < Params.walk_energy_cost + Params.rest_energy_cost)
			sleepy = 5;
	}

	public static void runStats(java.util.List<Critter> ohms) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : ohms) {
			Ohm c = (Ohm) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + ohms.size() + " total Ohms    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * ohms.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * ohms.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * ohms.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * ohms.size()) + "% left   ");
		System.out.println();
	}
	public CritterShape viewShape() { return CritterShape.STAR; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.YELLOW; }
}
