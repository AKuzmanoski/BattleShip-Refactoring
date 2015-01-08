package Battleships;

/**
 * @author Michael
 * @since 12 February 2005 14:57:34
 * @modified 12 February 2005 14:57:34
 */

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class InfluenceMap implements Serializable, Observer {

	/**
	 * Serial number
	 */
	private static final long serialVersionUID = 8530256226538215227L;
	private InfluenceCell[][] map;
	private int numberOfTurns = 9999;
	String coOrds = "";

	/**
	 * Creates an influence map from a two dimensional array as a 10 by 10
	 * cells. All cell values are set to 0
	 */
	public InfluenceMap() {
		map = new InfluenceCell[10][10];

		for (int a = 0; a < getHeight(); a++)
			for (int b = 0; b < getWidth(); b++) {
				map[a][b] = new InfluenceCell(a, b);
				map[a][b].addObserver(this);
			}
		numberOfTurns = 9999;
	}

	/**
	 * @return the actual width of the map
	 */
	public int getWidth() {
		if (getHeight() == 0)
			return 0;
		else
			return map[0].length;
	}

	/**
	 * @return the actual height of the map
	 */
	public int getHeight() {
		if (map == null)
			return 0;
		return map.length;
	}

	/**
	 * This method sets an element to a value on the influence map
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @param value
	 *            the value of the square
	 */
	public void set(int i, int j, int value) {
		if (i > getHeight() || j > getWidth())
			throw new IllegalArgumentException(
					"Number is bigger that the grid size");
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("Cordinate cannot be negative");
		map[i][j].setValue(value);
	}

	/**
	 * This method returns one cell of map
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return cell of the map that is on the given position
	 */
	protected InfluenceCell get(int i, int j) {
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("Number cannot be negative");
		if (i > getHeight() || j > getWidth())
			throw new IllegalArgumentException(
					"Number is bigger that the grid size");
		return map[i][j];
	}

	/**
	 * Gets the grid value at
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @returns the value of cell at the specified parameter
	 */
	public int getVal(int i, int j) {
		return get(i, j).getValue();
	}

	/**
	 * @return value of the cell or cells with the highest number on the
	 *         influence map.
	 */
	public int getMaxHotspotVal() {
		return InfluenceCell.getHotspotValue();
	}

	/**
	 * @return the number of cells on the influence map that have the maximum
	 *         influence value.
	 */
	public int getNumberOfHotspots() {
		int hs = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot()) {
					hs++;
				}
			}
		}
		return hs;
	}

	/**
	 * @return all the hotspot references as a single concatonated string, If
	 *         there is only one hotspot only i and j will be returned. If there
	 *         are x hotspots then an i and j reference pair will be returned
	 *         for each hotspot.
	 */
	public String getHotspots() {
		// concatanates the references of the hotspots into a string
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot())
					coOrds = coOrds + i + j;
			}
		}
		return coOrds;
	}

	/**
	 * @return an {@link Integer} array containing the hotspots
	 */
	public int[] getIntHotspots() {
		// TODO
		int hsNum = this.getNumberOfHotspots();
		int[] refs = new int[hsNum * 2];
		int ref1 = 0;
		int ref2 = 1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (get(i, j).isHotspot()) {
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
	 * @return the number of turns that this influence map took to achieve a win
	 *         condition Defaults to 9999 if the Influence map is new and
	 *         untested or if it hasn't won yet.
	 */
	public int getTurns() {
		return numberOfTurns;
	}

	/**
	 * Sets the Number of tunrs that an influence map object took to win a game
	 * 
	 * @param t
	 *            number of turns
	 */
	public void setTurns(int t) {
		numberOfTurns = t;
	}

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
			// eastern by 11
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
		try {
			InfluenceCell cell = left(get(i, j));
			if (!cell.isHit()) {
				return;
			}

			for (int p = 2; p <= 5; p++) {
				cell = right(cell);
				if (!(cell.isHit() || cell.isMiss())) {
					if (p > 3
							|| (get(i - 1, j + 1).isMiss() && get(i + 1, j + 1)
									.isMiss())) {
						cell.add(9);
						return;
					}
				}

				if (!cell.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	public void propagateMissLeft(int i, int j) {
		try {
			InfluenceCell cell = left(get(i, j));
			if (!cell.isHit()) {
				return;
			}

			for (int p = 2; p <= 5; p++) {
				cell = left(cell);
				if (!(cell.isHit() || cell.isMiss())) {
					if (p > 3
							|| (get(i - 1, j - 1).isMiss() && get(i + 1, j - 1)
									.isMiss())) {
						cell.add(9);
						return;
					}
				}

				if (!cell.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	public void propagateMissUp(int i, int j) {
		try {
			InfluenceCell cell = left(get(i, j));
			if (!cell.isHit()) {
				return;
			}

			for (int p = 2; p <= 5; p++) {
				cell = above(cell);
				if (!(cell.isHit() || cell.isMiss())) {
					if (p > 3
							|| (get(i - 1, j - 1).isMiss() && get(i - 1, j + 1)
									.isMiss())) {
						cell.add(9);
						return;
					}
				}

				if (!cell.isHit()) {
					return;
				}
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

	public void propagateMissDown(int i, int j) {
		try {
			InfluenceCell cell = left(get(i, j));
			if (!cell.isHit()) {
				return;
			}

			for (int p = 2; p <= 5; p++) {
				cell = below(cell);
				if (!(cell.isHit() || cell.isMiss())) {
					if (p > 3
							|| (get(i + 1, j - 1).isMiss() && get(i + 1, j + 1)
									.isMiss())) {
						cell.add(9);
						return;
					}
				}

				if (!cell.isHit()) {
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
		for (int a = 0; a < getHeight(); a++)
			for (int b = 0; b < getWidth(); b++)
				this.setDeadends(a, b);

	}

	public void setDeadends(int i, int j) {
		InfluenceCell cell = get(i, j);
		int Dave;

		// enters statement if it is in a corner
		// top left corner

		// while (!done)
		// {

		if (!cell.isMiss()) {
			if (i == 0 && j == 0) {
				if (below(cell).equals(-9) && right(cell).equals(-9)) {
					cell.subtract(7);
					return;

				}
			}

			// enters statement if it is in a corner
			// top right corner

			if (i == 0 && j == getWidth() - 1) {
				if (below(cell).equals(-9) && left(cell).equals(-9)) {
					cell.subtract(7);
					return;
				}
			}

			// enters statement if it is in a corner
			// bottom left corner

			if (i == getHeight() - 1 && j == 0) {
				if (above(cell).equals(-9) && right(cell).equals(-9)) {
					cell.subtract(7);
					return;
				}
			}

			// enters statement if it is in a corner
			// bottom right corner

			if (i == getHeight() - 1 && j == getWidth() - 1) {
				if (above(cell).equals(-9) && left(cell).equals(-9)) {
					cell.subtract(7);
					return;
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
				if (i + 1 != getHeight() && j + 1 != getWidth() && i - 1 != -1) {
					if (below(cell).equals(-9) && above(cell).equals(-9)
							&& right(cell).equals(-9)) {
						cell.subtract(7);
						return;
					}
				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// this is the right edge
			try {
				Dave = map[i][j + 1].getValue();
			} catch (ArrayIndexOutOfBoundsException e) {
				if (i + 1 != getHeight() && i - 1 != -1) {
					if (above(cell).equals(-9) && below(cell).equals(-9)
							&& left(cell).equals(-9)) {
						cell.subtract(7);
						return;
					}
				}
			}

			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// this is the top edge
			try {
				Dave = map[i - 1][j].getValue();
			} catch (ArrayIndexOutOfBoundsException e) {
				if (i + 1 != getHeight() && j + 1 != getWidth()) {
					if (below(cell).equals(-9) && right(cell).equals(-9)
							&& left(cell).equals(-9)) {
						cell.subtract(7);
						return;
					}
				}
			}
			// attempts to throw an ArrayIndexOutOfBoundsException, this will
			// mean the square is at an edge
			// bottom edge
			try {
				Dave = map[i + 1][j].getValue();
			} catch (ArrayIndexOutOfBoundsException e) {
				if (i + 1 != getHeight() && j + 1 != getWidth()) {
					if (above(cell).equals(-9) && right(cell).equals(-9)
							&& left(cell).equals(-9)) {
						cell.subtract(7);
						return;
					}
				}
			}
		}
	}

	/**
	 * @return the j of the cell with the highest influence value
	 */
	public int getHotspotJ() {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot()) {
					return j;
				}
			}
		}
		return 0;
	}

	/**
	 * @return the i of the cell with the highest influence value
	 */
	public int getHotspotI() {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot()) {
					return i;
				}
			}
		}
		return 0;
	}

	/**
	 * Adds two influence maps objects together by summing their elements
	 * 
	 * @param i
	 *            another map
	 */
	public void addMap(InfluenceMap i) {
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				if (get(x, y).compareTo(InfluenceCell.getHitvalue()) >= 0) {
					get(x, y).add(5);
				} else if (i.get(x, y).compareTo(InfluenceCell.getHitvalue()) >= 0) {
					get(x, y).add(5);
				} else {
					get(x, y).add(i.getVal(x, y));
				}
			}
		}
	}

	/**
	 * Sets all values in the influence map to 0
	 */
	public void clearAll() {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				get(i, j).setValue(0);
			}
		}
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
			for (int j = 0; j < 10; j++) {
				r = r + get(i, j).getValue();
			}
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

	/**
	 * Computes and sets the maximum value found in cells
	 */
	private void computeMaxHotspotValue() {
		int maxValue = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).compareTo(maxValue) >= 0 && !get(i, j).isHit()
						&& !get(i, j).equals(0)) {
					maxValue = get(i, j).getValue();
				}
			}
		}
		InfluenceCell.setHotspotValue(maxValue);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// One of the cells has changed, so recompute hotspot value
		computeMaxHotspotValue();
	}
}