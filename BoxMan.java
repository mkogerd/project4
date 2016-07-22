package project4;
/*
 * BoxMans behave by turning at a 90 degree angle every other turn, always walking. Essentially,
 * they trace out a 3x3 square. They enjoy reproducing at a low energy of 50.
 */
public class BoxMan extends Critter {
	
	@Override
	public String toString() { return ""; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	private boolean turn;
	
	public BoxMan() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 50) {
			BoxMan child = new BoxMan();
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
		
		/* pick a new direction based on steps */
		if(turn)
			dir = (dir + 2) % 8;
		turn = !turn;
	}

	public static void runStats(java.util.List<Critter> boxmans) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : boxmans) {
			BoxMan c = (BoxMan) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + boxmans.size() + " total BoxMans    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * boxmans.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * boxmans.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * boxmans.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * boxmans.size()) + "% left   ");
		System.out.println();
	}
}
