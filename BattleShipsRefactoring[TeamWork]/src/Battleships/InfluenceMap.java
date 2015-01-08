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
	private InfluenceCell[][] map;
	private int maxValue = 0;
	private int numberOfTurns = 9999;
	private int hitValue = 9;
	private int missValue = -5;
	String coOrds = "";

	/**
	 * Creates an influence map from a two dimensional array as a 10 by 10
	 * cells. All cell values are set to 0
	 */
	public InfluenceMap() {
		map = new InfluenceCell[10][10];

		for (int a = 0; a < 10; a++)
			for (int b = 0; b < 10; b++)
				map[a][b] = new InfluenceCell(a, b);
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
		map[i][j].setValue(value);
	}

	public InfluenceCell get(int i, int j) {
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("Number cannot be negative");
		if (i > 10 || j > 10)
			throw new IllegalArgumentException(
					"Number is bigger that the grid size");
		return map[i][j];
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
		return map[i][j].getValue();
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
		return map[i][j].compareTo(maxValue) >= 0 && !map[i][j].isHit()
				&& !map[i][j].equals(0);
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
					maxValue = map[i][j].getValue();
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
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 */
	public void hit(int i, int j) {
		InfluenceCell cell = get(i, j);
		if (cell.isHit()) {
			// throw new IllegalArgumentException("Hit already taken");
		}
		cell.hit();

		try { // if southern is not a hit, increament it
			if (!below(cell).isHit()) {
				below(cell).add(2);
			}

			// if southern was also a hit and the northern isn't then increment
			// eastern by 5
			if (below(cell).isHit() && !above(cell).isHit()) {
				above(cell).add(11);
			}

		}

		catch (Exception e) {
			// do nothing
		}

		try { // if northern is not a hit, increament it
			if (!above(cell).isHit()) {
				above(cell).add(2);
			}

			// if northern is a hit and southern isn't then increment southern
			// by 8
			if (above(cell).isHit() && !below(cell).isHit()) {
				below(cell).add(11);
			}
		}

		catch (Exception e) {
			// do nothing
		}

		try { // if eastern is not a hit, increment it
			if (!right(cell).isHit()) {
				right(cell).add(4);
			}

			// if eastern is a hit, and western isn't increment western by 11
			if (right(cell).isHit() && !left(cell).isHit()) {
				left(cell).add(11);
			}
		}

		catch (Exception e) {
			// do nothing
		}

		try { // western is not a hit increment it
			if (!left(cell).isHit())
				left(cell).add(4);

			// western is a hit and eastern isn't increment eastern by 8
			if (left(cell).isHit() && !right(cell).isHit()) {
				right(cell).add(11);
			}
		}

		catch (Exception e) {
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
		InfluenceCell cell = get(i, j);
		if (cell.isHit()) {
			if (!isEdge(i, j)) {
				if (!above(cell).isHit() && above(cell).isOdd()) {
					// if(map[i-1][j]==13)
					above(cell).subtract(9);
				}

				// dec above if even
				if (!above(cell).isHit() && above(cell).isEven()) {
					above(cell).subtract(2);
				}

				// dec below if odd
				if (!below(cell).isHit() && below(cell).isOdd()) {
					below(cell).subtract(9);
				}

				// dec below if even
				if (!below(cell).isHit() && below(cell).isEven()) {
					below(cell).subtract(2);
				}

				// dec left if even
				if (!left(cell).isHit() && left(cell).isEven()) {
					left(cell).subtract(4);
				}

				// dec left if odd
				if (!left(cell).isHit() && left(cell).isOdd()) {
					left(cell).subtract(9);
				}

				// dec right if even
				if (!right(cell).isHit() && right(cell).isEven()) {
					right(cell).subtract(4);
				}

				// dec right if odd
				if (!right(cell).isHit() && right(cell).isOdd()) {
					right(cell).subtract(9);
				}
			} else {
				try {
					// dec above if even
					if (!above(cell).isHit() && above(cell).isEven()) {
						above(cell).subtract(2);
					}

					if (!above(cell).isHit() && above(cell).isOdd()) {
						// if(map[i-1][j]==13)
						above(cell).subtract(9);
					}
				} catch (Exception ex) {
					// do nothing
				}

				// dec below if odd
				try {
					// dec below if even
					if (!below(cell).isHit() && below(cell).isEven()) {
						below(cell).subtract(2);
					}

					if (!below(cell).isHit() && below(cell).isOdd()) {
						below(cell).subtract(9);
					}
				} catch (Exception ex) {
					// do nothing
				}

				try {
					// dec left if even
					if (!left(cell).isHit() && left(cell).isEven()) {
						left(cell).subtract(4);
					}

					// dec left if odd
					if (!left(cell).isHit() && left(cell).isOdd()) {
						left(cell).subtract(9);
					}
				} catch (Exception ex) {
					// do nothing
				}

				try {
					// dec right if even
					if (!right(cell).isHit() && right(cell).isEven()) {
						get(i, j + 1).subtract(4);
					}

					// dec right if odd
					if (!right(cell).isHit() && right(cell).isOdd()) {
						right(cell).subtract(9);
					}
				} catch (Exception ex) {
					// do nothing
				}
			}
		}
	}

	public void propagateMissRight(int i, int j) {
		InfluenceCell current = right(get(i, j));
		try {
			if (!current.isHit()) {
				return;
			}

			for (int p = 2; p <= p; p++) {
				current = right(current);
				if (!(current.isHit() || current.isMiss())) {
					if (p > 3
							|| (get(i - 1, j + 1).isMiss() && get(i + 1, j + 1)
									.isMiss())) {
						current.add(9);
						return;
					}
				}

				if (!current.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	public void propagateMissLeft(int i, int j) {
		InfluenceCell current = left(get(i, j));
		try {
			if (!current.isHit()) {
				return;
			}

			for (int p = 2; p <= p; p++) {
				current = left(current);
				if (!(current.isHit() || current.isMiss())) {
					if (p > 3
							|| (get(i - 1, j - 1).isMiss() && get(i + 1, j - 1)
									.isMiss())) {
						current.add(9);
						return;
					}
				}

				if (!current.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	public void propagateMissUp(int i, int j) {
		InfluenceCell current = above(get(i, j));
		try {
			if (!current.isHit()) {
				return;
			}

			for (int p = 2; p <= p; p++) {
				current = above(current);
				if (!(current.isHit() || current.isMiss())) {
					if (p > 3
							|| (get(i - 1, j - 1).isMiss() && get(i - 1, j + 1)
									.isMiss())) {
						current.add(9);
						return;
					}
				}

				if (!current.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	public void propagateMissDown(int i, int j) {
		InfluenceCell current = below(get(i, j));
		try {
			if (!current.isHit()) {
				return;
			}

			for (int p = 2; p <= p; p++) {
				current = below(current);
				if (!(current.isHit() || current.isMiss())) {
					if (p > 3
							|| (get(i + 1, j - 1).isMiss() && get(i + 1, j + 1)
									.isMiss())) {
						current.add(9);
						return;
					}
				}

				if (!current.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	/**
	 * Marks on the influence map where a miss is.
	 */
	public void miss(int i, int j) {
		get(i, j).miss();

		propagateMissLeft(i, j);
		propagateMissRight(i, j);
		propagateMissDown(i, j);
		propagateMissUp(i, j);

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

		if (map[i][j].getValue() != -5) {
			if (i == 0 && j == 0) {
				if (((!done) && (map[i + 1][j].getValue() == -9) && (map[i][j + 1]
						.getValue() == -9))) {
					map[i][j].subtract(7);
					done = true;

				}
			}

			// enters statement if it is in a corner
			// top right corner

			if ((i == 0) && (j == 9)) {
				if ((!(done) && (map[i + 1][j].getValue() == -9) && (map[i][j - 1]
						.getValue() == -9))) {
					map[i][j].subtract(7);
					done = true;

				}
			}

			// enters statement if it is in a corner
			// bottom left corner

			if ((i == 9) && (j == 0)) {
				if ((!(done) && (map[i - 1][j].getValue() == -9) && (map[i][j + 1]
						.getValue() == -9))) {
					map[i][j].subtract(7);
					done = true;

				}
			}

			// enters statement if it is in a corner
			// bottom right corner

			if ((i == 9) && (j == 9)) {
				if ((!(done) && (map[i - 1][j].getValue() == -9) && (map[i][j - 1]
						.getValue() == -9))) {
					map[i][j].subtract(7);
					done = true;

				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// in this case the left edge
			try {
				Dave = map[i][j - 1].getValue();
			}

			// checks around the Northern, Eastern and southern cells to check
			// if they are all misses
			// if they are it will reduce the influence map vlaue to the lowest
			// possible value.

			catch (ArrayIndexOutOfBoundsException e) {
				if ((i + 1 != 10) && j + 1 != 10 && (i - 1 != -1)) {
					if (((!done) && map[i + 1][j].getValue() == -9)
							&& (map[i - 1][j].getValue() == -9)
							&& (map[i][j + 1].getValue() == -9)) {
						map[i][j].subtract(7);
						done = true;
					}
				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// this is the right edge
			try {
				Dave = map[i][j + 1].getValue();
			}

			catch (ArrayIndexOutOfBoundsException e) {
				if ((i + 1 != 10) && (i - 1 != -1)) {
					if (((!done) && map[i + 1][j].getValue() == -9)
							&& (map[i - 1][j].getValue() == -9)
							&& (map[i][j - 1].getValue() == -9)) {
						map[i][j].subtract(7);
						done = true;
					}
				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// this is the top edge
			try {
				Dave = map[i - 1][j].getValue();
			}

			catch (ArrayIndexOutOfBoundsException e) {
				if ((i + 1 != 10) && (j + 1 != 10)) {
					if (((!done) && map[i + 1][j].getValue() == -9)
							&& (map[i][j + 1].getValue() == -9)
							&& (map[i][j - 1].getValue() == -9)) {
						map[i][j].subtract(7);
						done = true;
					}
				}
			}
			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// bottom edge
			try {
				Dave = map[i + 1][j].getValue();
			}

			catch (ArrayIndexOutOfBoundsException e) {

				if ((i + 1 != 10) && (j + 1 != 10)) {
					if (((!done) && map[i - 1][j].getValue() == -9)
							&& (map[i][j + 1].getValue() == -9)
							&& (map[i][j - 1].getValue() == -9)) {
						map[i][j].subtract(7);
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

				if (map[i][j].compareTo(val) >= 0 && !map[i][j].isHit()) {
					val = map[i][j].getValue();
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
				if (map[i][j].compareTo(val) >= 0 && !map[i][j].isHit()) {
					val = map[i][j].getValue();
					y = i;
				}
			}
		return y;

	}

	/** 
	 * Adds two influence maps objects together by summing their elements 
	 * @param i another map
	 */
	public void addMap(InfluenceMap i) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (map[x][y].compareTo(hitValue) >= 0) {
					map[x][y].add(5);
				}

				else if (i.get(x, y).compareTo(hitValue) >= 0) {
					map[x][y].add(5);
				} else

					map[x][y].add(i.getVal(x, y));

			}
		}
	}

	/**
	 * Sets all values in the influence map to 0
	 */
	public void clearAll() {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				map[i][j] = new InfluenceCell(i, j);
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

	/**
	 * Returns cell that is above the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively above to the given cell
	 */
	private InfluenceCell above(InfluenceCell cell) {
		return get(cell.getI() - 1, cell.getJ());
	}

	/**
	 * Returns cell that is below the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively below to the given cell
	 */
	private InfluenceCell below(InfluenceCell cell) {
		return get(cell.getI() + 1, cell.getJ());
	}

	/**
	 * Returns cell that is left the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively left to the given cell
	 */
	private InfluenceCell left(InfluenceCell cell) {
		return get(cell.getI(), cell.getJ() - 1);
	}

	/**
	 * Returns cell that is right the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively right to the given cell
	 */
	private InfluenceCell right(InfluenceCell cell) {
		return get(cell.getI(), cell.getJ() + 1);
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
