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
//Snails only move in one direction
//Snails will always fight
//Snails will reproduce normally
//Will be shown by an S on the world
package project4;

import project4.Critter.CritterShape;

public class Snail extends Critter {
	
	@Override
	public String toString() { return "S"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Snail() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) {
		int num = Critter.getRandomInt(1);
		if(num==0){
			return true;
		}
			return false;
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		if(look(dir)==null || look(dir).equals("@")){
			walk(dir);
		}
		else{
			dir+=4;
			dir = dir%8;
			run(dir);
		}
		
		if (getEnergy() > (Params.min_reproduce_energy/2)) {
			Snail child = new Snail();
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
		
	}

	public static void runStats(java.util.List<Critter> snails) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : snails) {
			Snail s = (Snail) obj;
			total_straight += s.genes[0];
			total_right += s.genes[1] + s.genes[2] + s.genes[3];
			total_back += s.genes[4];
			total_left += s.genes[5] + s.genes[6] + s.genes[7];
		}
		System.out.print("" + snails.size() + " total Snails    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * snails.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * snails.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * snails.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * snails.size()) + "% left   ");
		System.out.println();
	}
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.BLACK; }
}
