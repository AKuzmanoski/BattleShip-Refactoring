package Battleships;

/*
 * Author: Michael
 * Created: 12 February 2005 14:57:34
 * Modified: 12 February 2005 14:57:34
 */

import java.io.Serializable;

public class InfluenceMap implements Serializable {
	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 8530256226538215227L;
	private int[][] map;
	private int maxValue = 0;
	private int numberOfTurns = 9999;
	private int hitValue = 9;
	String coOrds = "";

	/**
	 * Creates an influence map from a two dimensional array as a 10 by 10
	 * cells. All cell values are set to 0
	 */
	public InfluenceMap() {
		map = new int[10][10];

		for (int a = 0; a < 10; a++)
			for (int b = 0; b < 10; b++)
				map[a][b] = 0;
		numberOfTurns = 9999;
	}

	/**
	 * This method is used sets an element to a value on the influence map
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @param value
	 *            the value of the square
	 */
	public void set(int i, int j, int value) {
		if (i > 10 || j > 10)
			throw new IllegalArgumentException(
					"Number is bigger that the grid size");
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("Cordinate cannot be negative");
		map[i][j] = value;
	}

	/**
	 * Adds amount to the current cell value
	 * @param i the row index
	 * @param j the column index
	 * @param amount to be added to current value
	 */
	public void add(int i, int j, int amount) {
		set(i, j, getVal(i, j) + amount);
	}
	
	/**
	 * Subtracts amount to the current cell value
	 * @param i the row index
	 * @param j the column index
	 * @param amount to be subtracted to current value
	 */
	public void subtract(int i, int j, int amount) {
		set(i, j, getVal(i, j) - amount);
	}

	/**
	 * Gets the grid value at @param i, @param j
	 * 
	 * @returns the value of cell at the specified parameter
	 */
	public int getVal(int i, int j) {
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("Number cannot be negative");
		if (i > 10 || j > 10)
			throw new IllegalArgumentException(
					"Number is bigger that the grid size");
		return map[i][j];
	}

	/**
	 * Tells whether the cell is hotspot
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is hotspot, in every other case
	 *         <code>false</code>
	 */
	public boolean isHotspot(int i, int j) {
		return map[i][j] >= maxValue && map[i][j] != hitValue && map[i][j] != 0;
	}

	/**
	 * Returns value of the cell or cells with the highest number on the
	 * influence map.
	 */
	public int getMaxHotspotVal() {
		maxValue = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (isHotspot(i, j))

					maxValue = map[i][j];
			}
		}

		return maxValue;
	}

	/*
	 * /**Sets three hits consequtive emanating from the last hit element to
	 * sunk ship * public void subSunk(int i, int j) { try { //if two
	 * consequitive elements to the west are hits, set them all to sunk
	 * if(map[i+1][j] ==hit &&map[i+2][j] ==hit) { map[i+1][j] = map[i+1][j] +
	 * 3; }
	 * 
	 * // if western was also a hit then increment eastern by 15 if(map[i+1][j]
	 * ==hit) { map[i-1][j] = map[i-1][j] + 15; } } }
	 */
	/**
	 * Returns the number of cells on the influence map that have the maximum
	 * influence value.
	 */
	public int getNumberOfHotspots() {
		getMaxHotspotVal();
		int hs = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (isHotspot(i, j)) {
					hs++;
				}
			}
		}
		return hs;
	}

	/**
	 * Returns all the hotspot references as a single concatonated string, If
	 * there is only one hotspot only i and j will be returned. If there are x
	 * hotspots then an i and j reference pair will be returned for each
	 * hotspot.
	 */
	public String getHotspots() {

		// if(num > this.getNumberOfHotspots())
		// throw new IllegalArgumentException("too many hotspots");
		// concatanates the references of the hotspots into a string
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (isHotspot(i, j))
					coOrds = coOrds + i + j;
			}
		}
		return coOrds;
	}

	/** Returns an int array containing the hotspots */
	public int[] getIntHotspots() {
		int hsNum = this.getNumberOfHotspots();
		int[] refs = new int[hsNum * 2];
		// if(num > this.getNumberOfHotspots())
		// throw new IllegalArgumentException("too many hotspots");
		int ref1 = 0;
		int ref2 = 1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (isHotspot(i, j)) {
					for (int x = 0; x < hsNum; x++) {
						refs[ref1] = i;
						refs[ref2] = j;
					}
					ref1 = ref1 + 2;
					ref2 = ref2 + 2;
				}

			}
		}
		return refs;
	}

	/**
	 * Returns the number of turns that this influence map took to achieve a win
	 * condition Defaults to 9999 if the Influence map is new and untested or if
	 * it hasn't won yet.
	 */
	public int getTurns() {
		return numberOfTurns;
	}

	/** Sets the Number of tunrs that an influence map object took to win a game */
	public void setTurns(int t) {
		numberOfTurns = t;
	}

	/**
	 * Answers to the question whether the cell is hitted
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is hitted, <code>false</code> in every
	 *         other case
	 */
	public boolean isHit(int i, int j) {
		return map[i][j] == hitValue;
	}

	/*
	 * public int getHSref
	 * 
	 * String pair1 = "";
	 * 
	 * int pairs = (this.getNumberOfHotspots())*2;
	 * 
	 * int sub1 = 0; int sub2 = 0;
	 * 
	 * pair1 = coOrds.substring(0,1);
	 * 
	 * refs[0][0] int subPair1 = 0; int subPair2 = 0;
	 */

	/**
	 * Increases the value of the specified cell's northern, southern, eastern
	 * and western neighbour by one. The actual specified cell has it's value
	 * changed to 9. This method will not alter any cells who's value is already
	 * 9. If it is the second consquetive hit in a row or column the next
	 * element in that sequence will be weighted to an even higher value
	 */
	public void hit(int i, int j) {
		if (isHit(i, j)) {
			// throw new IllegalArgumentException("Hit already taken");
		}
		set(i, j, hitValue);

		try { // if southern is not a hit, increament it
			if (!isHit(i + 1, j)) {
				add(i + 1, j, 2);
			}

			// if southern was also a hit and the northern isn't then increment
			// eastern by 5
			if (isHit(i + 1, j) && !isHit(i - 1, j)) {
				add(i - 1, j, 11);
			}

		}

		catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

		try { // if northern is not a hit, increament it
			if (!isHit(i - 1, j)) {
				add(i - 1, j, 2);
			}

			// if northern is a hit and southern isn't then increment southern
			// by 8
			if (isHit(i - 1, j) && !isHit(i + 1, j)) {
				add(i + 1, j, 11);
			}
		}

		catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

		try { // if eastern is not a hit, increment it
			if (!isHit(i, j + 1)) {
				add(i, j + 1, 4);
			}

			// if eastern is a hit, and western isn't increment western by 11
			if (isHit(i, j + 1) && !isHit(i, j - 1)) {
				add(i, j - 1, 11);
			}
		}

		catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

		try { // western is not a hit increment it
			if (!isHit(i, j - 1))
				add(i, j - 1, 4);

			// western is a hit and eastern isn't increment eastern by 8
			if (isHit(i, j - 1) && !isHit(i, j + 1)) {
				add(i, j + 1, 11);
			}
		}

		catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}
	}

	/**
	 * Answers to the question whether the cell is on the left edge
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is on left edge, <code>false</code> in
	 *         every other case
	 */
	public boolean isLeft(int i, int j) {
		return j == 0 && i != 0 && i != 9;
	}

	/**
	 * Answers to the question whether the cell is on the right edge
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is on right edge, <code>false</code> in
	 *         every other case
	 */
	public boolean isRight(int i, int j) {
		return j == 9 && i != 0 && i != 9;
	}

	/**
	 * Answers to the question whether the cell is on the top edge
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is on top edge, <code>false</code> in
	 *         every other case
	 */
	public boolean isTop(int i, int j) {
		return i == 0;
	}

	/**
	 * Answers to the question whether the cell is on the bottom edge
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is on bottom edge, <code>false</code>
	 *         in every other case
	 */
	public boolean isBottom(int i, int j) {
		return i == 9;
	}

	/**
	 * Answers to the question whether the cell is on the edge
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is on the edge, <code>false</code> in
	 *         every other case
	 */
	public boolean isEdge(int i, int j) {
		return isLeft(i, j) || isRight(i, j) || isTop(i, j) || isBottom(i, j);
	}

	public void sunk(int i, int j) {
		if (isHit(i, j)) {
			
			try {
				// dec above if odd
				if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 1) {
					// if(map[i-1][j]==13)
					subtract(i - 1, j, 9);
				}
	
				// dec above if even
				if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 0) {
					subtract(i - 1, j, 2);
				}	
			} catch (Exception ex) { 
				
			}
				
				
			try {
				// dec below if odd
				if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 1) {
					subtract(i + 1, j, 9);
				}
	
				// dec below if even
				if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 0) {
					subtract(i + 1, j, 2);
				}
			} catch (Exception ex) { 
				
			}

			try {
				// dec left if even
				if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
					subtract(i, j - 1, 4);
				}
	
				// dec left if odd
				if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
					subtract(i, j - 1, 9);
				}
			} catch (Exception ex) { 
				
			}

			try {
				// dec right if even
				if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
					subtract(i, j + 1, 4);
				}
	
				// dec right if odd
				if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
					subtract(i, j + 1, 9);
				}
			} catch (Exception ex) { 
				
			}
		}
			/*// if the hit is not on an edge
			if (!isEdge(i, j)) {

				// dec above if odd
				if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 1) {
					// if(map[i-1][j]==13)
					subtract(i - 1, j, 9);
				}

				// dec above if even
				if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 0) {
					subtract(i - 1, j, 2);
				}

				// dec below if odd
				if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 1) {
					subtract(i + 1, j, 9);
				}

				// dec below if even
				if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 0) {
					subtract(i + 1, j, 2);
				}

				// dec left if even
				if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
					subtract(i, j - 1, 4);
				}

				// dec left if odd
				if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
					subtract(i, j - 1, 9);
				}

				// dec right if even
				if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
					subtract(i, j + 1, 4);
				}

				// dec right if odd
				if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
					subtract(i, j + 1, 9);
				}
			}

			// if hit is on left collumn but not top left or bottom left
			if (isLeft(i, j)) {
				// dec to right if even
				if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
					subtract(i, j + 1, 4);
				}

				// dec to right if odd
				if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
					subtract(i, j + 1, 9);
				}

				if (i != 0 && i != 9) {
					// dec above if even
					if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 0) {
						subtract(i - 1, j, 2);
					}

					// dec above if odd
					if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 1) {
						subtract(i - 1, j, 9);
					}

					// dec below of even
					if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 0) {
						subtract(i + 1, j, 2);
					}

					// dec below of odd
					if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 1) {
						subtract(i + 1, j, 9);
					}
				}
			}

			// if hit is on right most column but not top right or bottom right
			if (isRight(i, j)) {
				// dec to left if even
				if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
					subtract(i, j - 1, 4);
				}

				// dec to left if odd
				if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
					subtract(i, j - 1, 9);
				}

				if (i != 0 && i != 9) {
					// dec above if even
					if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 0) {
						subtract(i - 1, j, 2);
					}

					// dec above if odd
					if (!isHit(i - 1, j - 1) && map[i - 1][j] % 2 == 1) {
						subtract(i - 1, j, 9);
					}

					// dec below if even
					if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 0) {
						subtract(i + 1, j, 2);
					}

					// dec below if odd
					if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 1) {
						subtract(i + 1, j, 9);
					}
				}
			}

			// if hit on bottom row
			if (isBottom(i, j)) {
				// dec above if even
				if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 0) {
					subtract(i - 1, j, 2);
				}

				// dec above if odd
				if (!isHit(i - 1, j) && map[i - 1][j] % 2 == 1) {
					subtract(i - 1, j, 9);
				}

				if (j != 0 && j != 9) {
					// dec right if even
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
						subtract(i, j + 1, 4);
					}

					// dec right if odd
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
						subtract(i, j+ 1, 9);
					}

					// dec left if even
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
						subtract(i, j - 1, 4);
					}

					// dec left if odd
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
						subtract(i, j - 1, 9);
					}
				}

				// if in bottom left corner
				if (j == 0) {
					// dec right if even
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
						subtract(i, j + 1, 4);
					}

					// dec right if odd
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
						subtract(i, j + 1, 9);
					}
				}
				// if in bottom right corner
				if (j == 9) {
					// dec left if even
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
						subtract(i, j - 1, 4);
					}

					// dec left if odd
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
						subtract(i, j - 1, 9);
					}
				}
			}
			// if hit is on top row
			if (isTop(i, j)) {
				// dec below if even
				if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 0) {
					subtract(i + 1, j, 2);
				}

				// dec below if odd
				if (!isHit(i + 1, j) && map[i + 1][j] % 2 == 1) {
					subtract(i + 1, j, 9);
				}

				if (j != 0 && j != 9) {
					// dec right if even
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
						subtract(i, j + 1, 4);
					}

					// dec right if odd
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
						subtract(i, j + 1, 9);
					}

					// dec left if even
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
						subtract(i, j - 1, 4);
					}

					// dec left if odd
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
						subtract(i, j - 1, 9);
					}
				}

				// if in top left corner
				if (j == 0) {
					// dec right if even
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 0) {
						subtract(i, j + 1, 4);
					}

					// dec right if odd
					if (!isHit(i, j + 1) && map[i][j + 1] % 2 == 1) {
						subtract(i, j + 1, 9);
					}
				}
				// if in top right corner
				if (j == 9) {
					// dec left if even
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 0) {
						subtract(i, j - 1, 4);
					}

					// dec left if odd
					if (!isHit(i, j - 1) && map[i][j - 1] % 2 == 1) {
						subtract(i, j - 1, 9);
					}
				}
			}
		}*/
	}

	/**
	 * Marks on the influence map where a miss is.
	 */
	public void miss(int i, int j) {
		map[i][j] = -5;

		try { // if i,j is a hit and north east, and south east are all misses
				// then set east to +9.
			/*
			 * 0 0Xinc 0
			 *//* element to the right is not hit or miss */
			if (map[i - 1][j + 1] == -5/* above right a miss */
					&& map[i + 1][j + 1] == -5/* below right a miss */
					&& map[i][j + 1] == hitValue /* east is a hit */
					&& map[i][j + 2] != -5 /* east + 2 is not hit or miss */
					&& map[i][j + 2] != hitValue) {

				map[i][j + 2] = map[i][j + 2] + 9;

			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // north east and south east are both misses and east is a hit
				// then east +2 is set to +9.
			/*
			 * 0 0XXinc 0
			 */
			if (map[i - 1][j + 1] == -5/* above right is a miss */
					&& map[i + 1][j + 1] == -5/* below right is a miss */
					&& map[i][j + 2] == hitValue /* right is a hit */
					&& map[i][j + 3] != -5 /* east + 3 is not a hit or miss */
					&& map[i][j + 3] != -5) {
				map[i][j + 3] = map[i][j + 3] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // j+1 and j+2 and j+3 are hits then j +4 is set to +9.
			/*
			 * 0XXXinc
			 *//* element to the right is not hit or miss */
			if (map[i][j + 1] == hitValue /* j+1 is a hit */
					&& map[i][j + 2] == hitValue /*
												 * j + 2 is a hit
												 */
					&& map[i][j + 3] == hitValue /* j +3 is a hit */
					&& map[i][j + 4] != -5 /* j +4 is not a hit or a miss */
					&& map[i][j + 4] != hitValue) {
				map[i][j + 4] = map[i][j + 4] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // j+1 and j+2 and j+3 and j+4 is set to +9.
			/*
			 * 0XXXXinc
			 *//* element to the right is not hit or miss */
			if (map[i][j + 1] == hitValue /* east is a hit */
					&& map[i][j + 2] == hitValue /*
												 * east + 2 is a hit
												 */
					&& map[i][j + 3] == hitValue /* east +3 is a hit */
					&& map[i][j + 4] == hitValue /* east +4 is a hit */
					&& map[i][j + 5] != hitValue && /*
													 * east +5 is not a hit or a
													 * miss
													 */map[i][j + 5] != -5) {
				map[i][j + 5] = map[i][j + 5] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		/* western hits */

		try { // if i,j is a hit and north west, and south west are all misses
				// then set west to +9.
			/*
			 * 0 incX0 0 //*element to the left is not hit or miss
			 */
			if (map[i - 1][j - 1] == -5/* above left a miss */
					&& map[i + 1][j - 1] == -5/* below left a miss */
					&& map[i][j - 1] == hitValue /* right is a hit */
					&& map[i][j - 2] != -5 /* j - 2 is not hit or miss */
					&& map[i][j - 2] != hitValue) {

				map[i][j - 2] = map[i][j - 2] + 9;

			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // three hits to the left of a miss then east +2 is set to +9.
			/*
			 * 0 incXX0 0
			 */
			if (map[i - 1][j - 1] == -5/* above left is a miss */
					&& map[i + 1][j - 1] == -5/* below left is a miss */
					&& map[i][j - 2] == hitValue /* left is a hit */
					&& map[i][j - 3] != -5 /* east + 3 is not a hit or miss */
					&& map[i][j - 3] != -5) {
				map[i][j - 3] = map[i][j - 3] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // j-1 and j-2 and j-3 are hits then j -4 is set to +9.
			/*
			 * 
			 * incXXX0
			 */
			if (map[i][j - 1] == hitValue /* j+1 is a hit */
					&& map[i][j - 2] == hitValue /*
												 * j + 2 is a hit
												 */
					&& map[i][j - 3] == hitValue /* j +3 is a hit */
					&& map[i][j - 4] != -5 /* j +4 is not a hit or a miss */
					&& map[i][j - 4] != hitValue) {
				map[i][j - 4] = map[i][j - 4] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // j-1 and j-2 and j-3 and j-4 is set to +9.
			/*
			 * incXXXX0
			 */
			if (map[i][j - 1] == hitValue /* east is a hit */
					&& map[i][j - 2] == hitValue /*
												 * east + 2 is a hit
												 */
					&& map[i][j - 3] == hitValue /* east +3 is a hit */
					&& map[i][j - 4] == hitValue /* east +4 is a hit */
					&& map[i][j - 5] != hitValue && /*
													 * east +5 is not a hit or a
													 * miss
													 */map[i][j - 5] != -5) {
				map[i][j - 5] = map[i][j - 5] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		/* southern hits */

		try { // if i,j +1 is a hit and north, west, east are all misses then
				// set south to +9.
			/*
			 * 0< OX0 inc
			 */
			if (map[i + 1][j] == hitValue /* below is hit */
					&& map[i + 1][j - 1] == -5/* below left a miss */
					&& map[i + 1][j + 1] == -5 /* below right is a miss */
					&& map[i + 2][j] != -5 /* i + 2 is not hit or miss */
					&& map[i + 2][j] != hitValue) {
				map[i + 2][j] = map[i + 2][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // if i+1 is a hit and i+2 is a hit and north, west, east are all
				// misses then set i+3 to +9.
			/*
			 * 0< OX0 X inc
			 */
			if (map[i + 1][j] == hitValue /* below is a hit */
					&& map[i + 2][j] == hitValue /*
												 * i + 2 is a hit
												 */
					&& map[i + 1][j - 1] == -5/* below left a miss */
					&& map[i + 1][j + 1] == -5 /* below right is a miss */
					&& map[i + 3][j] != -5 /* i + 3 is not hit or miss */
					&& map[i + 3][j] != hitValue) {
				map[i + 3][j] = map[i + 3][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // if i+1 is a hit and i+2 is a hit and i +3 is a hit then set i+4
				// to +9.
			/*
			 * 0< X X X inc
			 */
			if (map[i + 1][j] == hitValue /* i+1 is a hit */
					&& map[i + 2][j] == hitValue /*
												 * i+ 2 is a hit
												 */
					&& map[i + 3][j] == hitValue /* i+ 3 is a hit */
					&& map[i + 4][j] != -5 /* i + 3 is not hit or miss */
					&& map[i + 4][j] != hitValue) {
				map[i + 4][j] = map[i + 4][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // if i+1 is a hit and i+2 is a hit and i +3 i+4 are hits then set
				// i + 5 to +9.
			/*
			 * 0< X X X X inc
			 */
			if (map[i + 1][j] == hitValue /* i+1 is a hit */
					&& map[i + 2][j] == hitValue /*
												 * i+ 2 is a hit
												 */
					&& map[i + 3][j] == hitValue /* i+ 3 is a hit */
					&& map[i + 4][j] == hitValue /* i+ 4 is a hit */
					&& map[i + 5][j] != -5 /* i + 3 is not hit or miss */
					&& map[i + 5][j] != hitValue) {
				map[i + 5][j] = map[i + 5][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		/* northern hits */

		try { // if i-1 is a hit and south, west, east are all misses then set
				// -2 to +9.
			/*
			 * inc OX0 0<
			 */
			if (map[i - 1][j] == hitValue /* above is hit */
					&& map[i - 1][j - 1] == -5/* above left a miss */
					&& map[i - 1][j + 1] == -5 /* above right is a miss */
					&& map[i - 2][j] != -5 /* i - 2 is not hit or miss */
					&& map[i - 2][j] != hitValue) {
				map[i - 2][j] = map[i - 2][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // if i-1 is a hit and i-2 is a hit and south, west, east are all
				// misses then set i-3 to +9.
			/*
			 * inc X OX0 0<
			 */
			if (map[i - 1][j] == hitValue /* i-1 is hit */
					&& map[i - 2][j] == hitValue /*
												 * i- 2 is hit
												 */
					&& map[i - 1][j - 1] == -5/* above left a miss */
					&& map[i - 1][j + 1] == -5 /* above right is a miss */
					&& map[i - 3][j] != -5 /* i - 3 is not hit or miss */
					&& map[i - 3][j] != hitValue) {
				map[i - 3][j] = map[i - 3][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // if i-1 is a hit and i-2 is a hit and i-3 is a hit then set i-4
				// to +9.
			/*
			 * inc X X X 0<
			 */
			if (map[i - 1][j] == hitValue /* i-1 is hit */
					&& map[i - 2][j] == hitValue /*
												 * i- 2 is hit
												 */
					&& map[i - 3][j] == hitValue /* i-3 is hit */
					&& map[i - 4][j] != -5 /* i - 4 is not hit or miss */
					&& map[i - 4][j] != hitValue) {
				map[i - 4][j] = map[i - 4][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

		try { // if i-1 is a hit and i-2 is a hit and i-3 and i-4 is a hit then
				// set i-5 to +9.
			/*
			 * inc X X X X 0<
			 */
			if (map[i - 1][j] == hitValue /* i-1 is hit */
					&& map[i - 2][j] == hitValue /*
												 * i- 2 is hit
												 */
					&& map[i - 3][j] == hitValue /* i-3 is hit */
					&& map[i - 4][j] == hitValue /* i-4 is hit */
					&& map[i - 5][j] != -5 /* i - 5 is not hit or miss */
					&& map[i - 5][j] != hitValue) {
				map[i - 5][j] = map[i - 5][j] + 9;
			}
		} catch (ArrayIndexOutOfBoundsException e) {/* do nothing */
		}

	}

	public void searchDeadends() {
		for (int a = 0; a < 10; a++)
			for (int b = 0; b < 10; b++)
				this.setDeadends(a, b);

	}

	public void setDeadends(int i, int j) {
		int Dave;
		boolean done = false;

		// enters statement if it is in a corner
		// top left corner

		// while (!done)
		// {

		if (map[i][j] != -5) {
			if (i == 0 && j == 0) {
				if (((!done) && (map[i + 1][j] == -9) && (map[i][j + 1] == -9))) {
					map[i][j] = -7;
					done = true;

				}
			}

			// enters statement if it is in a corner
			// top right corner

			if ((i == 0) && (j == 9)) {
				if ((!(done) && (map[i + 1][j] == -9) && (map[i][j - 1] == -9))) {
					map[i][j] = -7;
					done = true;

				}
			}

			// enters statement if it is in a corner
			// bottom left corner

			if ((i == 9) && (j == 0)) {
				if ((!(done) && (map[i - 1][j] == -9) && (map[i][j + 1] == -9))) {
					map[i][j] = -7;
					done = true;

				}
			}

			// enters statement if it is in a corner
			// bottom right corner

			if ((i == 9) && (j == 9)) {
				if ((!(done) && (map[i - 1][j] == -9) && (map[i][j - 1] == -9))) {
					map[i][j] = -7;
					done = true;

				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// in this case the left edge
			try {
				Dave = map[i][j - 1];
			}

			// checks around the Northern, Eastern and southern cells to check
			// if they are all misses
			// if they are it will reduce the influence map vlaue to the lowest
			// possible value.

			catch (ArrayIndexOutOfBoundsException e) {
				if ((i + 1 != 10) && j + 1 != 10 && (i - 1 != -1)) {
					if (((!done) && map[i + 1][j] == -9)
							&& (map[i - 1][j] == -9) && (map[i][j + 1] == -9)) {
						map[i][j] = -7;
						done = true;
					}
				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// this is the right edge
			try {
				Dave = map[i][j + 1];
			}

			catch (ArrayIndexOutOfBoundsException e) {
				if ((i + 1 != 10) && (i - 1 != -1)) {
					if (((!done) && map[i + 1][j] == -9)
							&& (map[i - 1][j] == -9) && (map[i][j - 1] == -9)) {
						map[i][j] = -7;
						done = true;
					}
				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// this is the top edge
			try {
				Dave = map[i - 1][j];
			}

			catch (ArrayIndexOutOfBoundsException e) {
				if ((i + 1 != 10) && (j + 1 != 10)) {
					if (((!done) && map[i + 1][j] == -9)
							&& (map[i][j + 1] == -9) && (map[i][j - 1] == -9)) {
						map[i][j] = -7;
						done = true;
					}
				}
			}
			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// bottom edge
			try {
				Dave = map[i + 1][j];
			}

			catch (ArrayIndexOutOfBoundsException e) {

				if ((i + 1 != 10) && (j + 1 != 10)) {
					if (((!done) && map[i - 1][j] == -9)
							&& (map[i][j + 1] == -9) && (map[i][j - 1] == -9)) {
						map[i][j] = -7;
						done = true;
					}
				}
			}

		}

	}

	/**
	 * Decreases the value of the specified cell's northern, southern, eastern
	 * and western neighbour by one. The actual specified cell has it's value
	 * changed to -8
	 * 
	 * public void cool(int i, int j) { map[i][j] = -8;
	 * 
	 * try { map[i+1][j] = map[i+1][j] - 1;
	 * 
	 * }
	 * 
	 * catch (ArrayIndexOutOfBoundsException e) { // do nothing }
	 * 
	 * try { map[i-1][j] = map[i-1][j] - 1; }
	 * 
	 * catch (ArrayIndexOutOfBoundsException e) { // do nothing }
	 * 
	 * try { map[i][j+1] = map[i][j+1] - 1; }
	 * 
	 * catch (ArrayIndexOutOfBoundsException e) { // do nothing }
	 * 
	 * try { map[i][j-1] = map[i][j-1] - 1; }
	 * 
	 * catch (ArrayIndexOutOfBoundsException e) { // do nothing } }
	 */

	/**
	 * Returns the j of the cell with the highest influence value
	 */
	public int getHotspotJ() {
		int x = 0;
		int val = 0;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {

				if (map[i][j] > val && map[i][j] != hitValue) {
					val = map[i][j];
					x = j;
				}
			}
		return x;

	}

	/**
	 * Returns the i of the cell with the highest influence value
	 */
	public int getHotspotI() {
		int y = 0;
		int val = 0;

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {

				if (map[i][j] > val && map[i][j] != hitValue) {
					val = map[i][j];
					y = i;
				}
			}
		return y;

	}

	/** Adds two influence maps objects together by summing their elements */
	public void addMap(InfluenceMap i) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (map[x][y] >= hitValue) {
					map[x][y] = map[x][y] + 5;
				}

				else if (i.getVal(x, y) >= hitValue) {
					map[x][y] = map[x][y] + 5;
				} else

					map[x][y] = map[x][y] + i.getVal(x, y);

			}
		}
	}

	/**
	 * Sets all values in the influence map to 0
	 */
	public void clearAll() {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				map[i][j] = 0;
	}

	/**
	 * Creates a string representation of the influence map
	 * 
	 * @return the string representation
	 */

	public String toString() {
		String r = "";
		for (int i = 0; i < 10; i++) // change these to ROWS to use the default
		{

			r = r + "|";
			for (int j = 0; j < 10; j++)
				r = r + map[i][j];
			r = r + "|\n";
		}
		return r;
	}

}

/**
 * old version does not include consequtive hits Increases the value of the
 * specified cell's northern, southern, eastern and western neighbour by one.
 * The actual specified cell has it's value changed to 8. This method will not
 * alter any cells who's value is already 8
 * 
 * /*public void hit(int i, int j) { if(map[i][j] == hit) { //throw new
 * IllegalArgumentException("Hit already taken"); } map[i][j] =hit;;
 * 
 * try { if(map[i+1][j] !=hit) map[i+1][j] = map[i+1][j] + 3; }
 * 
 * catch (ArrayIndexOutOfBoundsException e) { // do nothing }
 * 
 * try { if(map[i-1][j] !=hit) map[i-1][j] = map[i-1][j] + 3; }
 * 
 * catch (ArrayIndexOutOfBoundsException e) { // do nothing }
 * 
 * try { if(map[i][j+1] !=hit) map[i][j+1] = map[i][j+1] + 3; }
 * 
 * catch (ArrayIndexOutOfBoundsException e) { // do nothing }
 * 
 * try { if(map[i][j-1] !=hit) map[i][j-1] = map[i][j-1] + 3; }
 * 
 * catch (ArrayIndexOutOfBoundsException e) { // do nothing } }
 */
