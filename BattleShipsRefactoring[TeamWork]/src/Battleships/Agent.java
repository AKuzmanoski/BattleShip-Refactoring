package Battleships;

/*
 * Author: Michael
 * Created: 16 April 2005 12:39:10
 * Modified: 16 April 2005 12:39:10
 */
import java.util.Random;

public class Agent {
	private InfluenceMap map = null;
	private Grid grid = null;
	private int i = -1;
	private int j = -1;
	private Random randomNumberGenerator;

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public InfluenceMap setSunk() {
		for (int i = 0; i < map.getHeight(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				map.sunk(i, j);
			}
		}
		return map;
	}

	public InfluenceMap setSunk(int i, int j) {
		map.sunk(i, j);
		return map;
	}

	public void nextShot(InfluenceMap m1, Grid Attackgrid) {
		map = m1;

		grid = Attackgrid;

		if (map.getNumberOfHotspots() == 0) {
			NumberGenerator Powergen = new NumberGenerator();

			// while(compAtt.getGridVal(i-
			i = Powergen.rand(10);
			j = Powergen.rand(10);
		}

		// if there is only one HS get it's co-ordinates on the IM

		if (map.getNumberOfHotspots() == 1) {
			int checki = map.getHotspotI();
			int checkj = map.getHotspotJ();

			if (!grid.isEmpty(checki, checkj)) {
				map.set(checki, checkj, 0);
			}

			// if the element on the attack grid has not been hit then set i,j
			// to it.
			if (grid.isEmpty(checki, checkj)) {
			}

			else // set i, j to a random that has not been hit
			{
				randomNumberGenerator = new Random();

				// create random numbers
				while (true) {
					checki = randomNumberGenerator.nextInt(10);
					checkj = randomNumberGenerator.nextInt(10);
					// if co-ord is empty then set i,j to them
					if (grid.isEmpty(checki, checkj)) {
						break;
					}
				}

			}
			
			i = checki;
			j = checkj;

		}

		// code to choose hotspots
		// pulls the first pair of co-ords from an array
		if (map.getNumberOfHotspots() > 1) {
			boolean noneFound = false;
			System.out.println("Target multiple hotspots");
			int[] refs = map.getIntHotspots();

			if (grid.getGridVal(refs[0], refs[1]) == 0) {
				i = refs[0];
				j = refs[1];
			}

			else {
				i = refs[refs.length - 1];
				j = refs[refs.length - 2];
			}

			int length = refs.length - 2;

			for (int z = 0; z < length; z++) {
				refs[z] = refs[z + 2];
				// refs[z+1] = z+2;
			}

		}

	}

	public Grid placeShips() {
		// boolean
		grid = new Grid(10, 10);

		while (!grid.allShipsPlaced()) {
			NumberGenerator gen = new NumberGenerator();
			int x = gen.rand(10);
			int y = gen.rand(10);
			int o = gen.rand(1);

			x = gen.rand(10);
			y = gen.rand(10);
			o = gen.rand(2);
			System.out.println("vertical sub x = " + x + "\n");
			System.out.println("vertical sub y = " + y + "\n");
			grid.addSub(x, y, o);

			x = gen.rand(10);
			y = gen.rand(10);
			o = gen.rand(2);
			System.out.println("vertical battle x = " + x + "\n");
			System.out.println("vertical battle y = " + y + "\n");
			grid.addBattle(x, y, o);

			x = gen.rand(10);
			y = gen.rand(10);
			o = gen.rand(2);
			System.out.println("vertical air x = " + x + "\n");
			System.out.println("vertical air y = " + y + "\n");
			grid.addAir(x, y, o);

			x = gen.rand(10);
			y = gen.rand(10);
			o = gen.rand(2);
			System.out.println("vertical mine x = " + x + "\n");
			System.out.println("vertical mine y = " + y + "\n");
			grid.addMine(x, y, o);

			x = gen.rand(10);
			y = gen.rand(10);
			o = gen.rand(2);
			System.out.println("horizontal dest x = " + x + "\n");
			System.out.println("horizontal dest y = " + y + "\n");
			grid.addDest(x, y, o);

		}

		System.out.println("agent grid");
		System.out.println(grid.toString());

		return grid;
	}

	public InfluenceMap getMap() {
		return map;
	}

}
