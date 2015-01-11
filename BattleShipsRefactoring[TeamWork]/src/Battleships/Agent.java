package Battleships;

import java.util.Random;

/**
 * @author User Michael
 * @since 16 April 2005 12:39:10
 * @version 16 April 2005 12:39:10
 */
public class Agent {
	private InfluenceMap map;
	private Grid grid;
	private Grid attackGrid;
	private int i = -1;
	private int j = -1;
	private Random generator;

	/**
	 * Constructor that initializes Agent with map and grids
	 */
	public Agent() {
		generator = new Random();
		grid = new Grid(10, 10);
		attackGrid = new Grid(10, 10);
		map = new InfluenceMap();
		placeShips();
	}

	/**
	 * The row index of the last shot
	 * 
	 * @return the row index
	 */
	public int getI() {
		return i;
	}

	/**
	 * The column index of the last shot
	 * 
	 * @return the column index
	 */
	public int getJ() {
		return j;
	}

	/**
	 * Sunks all hitted cells
	 */
	public void setSunk() {
		for (int i = 0; i < map.getHeight(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				map.sunk(i, j);
			}
		}
	}

	/**
	 * Sunks cell on the given position
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 */
	public void setSunk(int i, int j) {
		map.sunk(i, j);
	}

	/**
	 * Generates next shot
	 */
	public void nextShot() {
		if (map.getNumberOfHotspots() == 0) {
			i = generator.nextInt(map.getHeight());
			j = generator.nextInt(map.getWidth());
		}
		if (map.getNumberOfHotspots() == 1) {
			i = map.getHotspotI();
			j = map.getHotspotJ();

			if (!attackGrid.isEmpty(i, j)) {
				map.set(i, j, 0);

				// create random numbers
				while (true) {
					i = generator.nextInt(10);
					j = generator.nextInt(10);
					// if co-ord is empty then set i,j to them
					if (attackGrid.isEmpty(i, j)) {
						break;
					}
				}
			}
		}
		if (map.getNumberOfHotspots() > 1) {
			if (attackGrid.getGridVal(map.getIntHotspots()[0],
					map.getIntHotspots()[1]) == 0) {
				i = map.getIntHotspots()[0];
				j = map.getIntHotspots()[1];
			} else {
				i = map.getIntHotspots()[map.getIntHotspots().length - 1];
				j = map.getIntHotspots()[map.getIntHotspots().length - 2];
			}
		}
	}

	/**
	 * Sets influence map
	 * 
	 * @param map
	 */
	public void setMap(InfluenceMap map) {
		this.map = map;
	}

	/**
	 * Sets grid which would be attacked
	 * 
	 * @param attackGrid
	 */
	public void setAttackGrid(Grid attackGrid) {
		this.attackGrid = attackGrid;
	}

	/**
	 * Places all ships
	 */
	private void placeShips() {
		grid = new Grid(10, 10);
		while (!grid.allShipsPlaced()) {
			grid.addSub(generator.nextInt(10), generator.nextInt(10),
					generator.nextInt(2));

			grid.addBattle(generator.nextInt(10), generator.nextInt(10),
					generator.nextInt(2));

			grid.addAir(generator.nextInt(10), generator.nextInt(10),
					generator.nextInt(2));

			grid.addMine(generator.nextInt(10), generator.nextInt(10),
					generator.nextInt(2));

			grid.addDest(generator.nextInt(10), generator.nextInt(10),
					generator.nextInt(2));
		}
	}

	/**
	 * Returns Agent's grid
	 * 
	 * @return {@link Grid}
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Returns Agent's map
	 * 
	 * @return {@link InfluenceMap}
	 */
	public InfluenceMap getMap() {
		return map;
	}
}
