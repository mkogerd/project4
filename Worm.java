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
//Worms will always try to run instead of fight
//Worms takes twice the energy of min_reproduce_energy*2 to reproduce
//Worms only do the run function and not walk
package project4;

import project4.Critter.CritterShape;

public class Worm extends Critter {
	
	@Override
	public String toString() { return "~"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Worm() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return false; }

	@Override
	public void doTimeStep() {
		/* take one run() forward */
		run(dir);
		
		if (getEnergy() > (Params.min_reproduce_energy*2)) {
			Worm child = new Worm();
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
	}

	public static void runStats(java.util.List<Critter> worms) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : worms) {
			Worm w = (Worm) obj;
			total_straight += w.genes[0];
			total_right += w.genes[1] + w.genes[2] + w.genes[3];
			total_back += w.genes[4];
			total_left += w.genes[5] + w.genes[6] + w.genes[7];
		}
		System.out.print("" + worms.size() + " total Worms    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * worms.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * worms.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * worms.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * worms.size()) + "% left   ");
		System.out.println();
	}
	public CritterShape viewShape() { return CritterShape.DIAMOND; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.RED; }
}