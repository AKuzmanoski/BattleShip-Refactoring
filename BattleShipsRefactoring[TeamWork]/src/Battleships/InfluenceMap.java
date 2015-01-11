package Battleships;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Michael
 * @since 12 February 2005 14:57:34
 * @modified 12 February 2005 14:57:34
 */
public class InfluenceMap implements Serializable, Observer {
	private static final long serialVersionUID = 8530256226538215227L;
	private InfluenceCell[][] map;
	private int numberOfTurns;

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
	protected int getWidth() {
		if (getHeight() == 0)
			return 0;
		else
			return map[0].length;
	}

	/**
	 * @return the actual height of the map
	 */
	protected int getHeight() {
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
					"Number is bigger than the grid size");
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
	public int getValue(int i, int j) {
		return get(i, j).getValue();
	}

	/**
	 * @return value of the cell or cells with the highest number on the
	 *         influence map.
	 */
	public int getHotspotValue() {
		return InfluenceCell.getHotspotValue();
	}

	/**
	 * @return the number of cells on the influence map that have the maximum
	 *         influence value.
	 */
	public int getNumberOfHotspots() {
		int numberOfHotspots = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot()) {
					numberOfHotspots++;
				}
			}
		}
		return numberOfHotspots;
	}

	/**
	 * @return all the hotspot references as a single concatonated string, If
	 *         there is only one hotspot only i and j will be returned. If there
	 *         are x hotspots then an i and j reference pair will be returned
	 *         for each hotspot.
	 */
	public String getHotspots() {
		// concatanates the references of the hotspots into a string
		String hotspotCoordinates = "";
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot()) {
					hotspotCoordinates += i;
					hotspotCoordinates += j;
				}
			}
		}
		return hotspotCoordinates;
	}

	/**
	 * @return an {@link Integer} array containing the hotspots
	 */
	public int[] getIntHotspots() {
		int[] hotspots = new int[getNumberOfHotspots() * 2];
		int index = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (get(i, j).isHotspot()) {
					hotspots[index] = i;
					hotspots[index + 1] = j;
					index += 2;
				}
			}
		}
		return hotspots;
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
		cell.hit();

		if (!isBottom(i, j)) {
			if (!below(cell).isHit()) {
				below(cell).add(2);
			} else if (!isTop(i, j) && !above(cell).isHit()) {
				above(cell).add(11);
			}
		}
		if (!isTop(i, j)) {
			if (!above(cell).isHit()) {
				above(cell).add(2);
			} else if (!isBottom(i, j) && !below(cell).isHit()) {
				below(cell).add(11);
			}
		}
		if (!isRight(i, j)) {
			if (!right(cell).isHit()) {
				right(cell).add(4);
			} else if (!isLeft(i, j) && !left(cell).isHit()) {
				left(cell).add(11);
			}
		}
		if (!isLeft(i, j)) {
			if (!left(cell).isHit()) {
				left(cell).add(4);
			} else if (!isRight(i, j) && !right(cell).isHit()) {
				right(cell).add(11);
			}
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
	protected boolean isLeft(int i, int j) {
		return j == 0;
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
	protected boolean isRight(int i, int j) {
		return j == getWidth() - 1;
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
	protected boolean isTop(int i, int j) {
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
	protected boolean isBottom(int i, int j) {
		return i == getHeight() - 1;
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
	protected boolean isEdge(int i, int j) {
		return isLeft(i, j) || isRight(i, j) || isTop(i, j) || isBottom(i, j);
	}

	/**
	 * Marks ship as sunk, and removes hotspots around the ship
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 */
	public void sunk(int i, int j) {
		InfluenceCell cell = get(i, j);
		if (cell.isHit()) {
			if (!isLeft(i, j)) {
				left(cell).sunk(4);
			}

			if (!isRight(i, j)) {
				right(cell).sunk(4);
			}

			if (!isTop(i, j)) {
				above(cell).sunk(2);
			}

			if (!isBottom(i, j)) {
				below(cell).sunk(2);
			}
		}
	}

	private void propagateMissRight(int i, int j) {
		if (isRight(i, j))
			return;
		InfluenceCell cell = right(get(i, j));
		if (!cell.isHit())
			return;
		for (int p = 2; p <= 5; p++) {
			if (isRight(cell.getI(), cell.getJ()) || right(cell).isMiss())
				return;
			cell = right(cell);
			if (!cell.isHit()) {
				if (p > 3
						|| (!isTop(cell.getI(), cell.getJ())
								&& above(right(cell)).isMiss()
								&& !isBottom(cell.getI(), cell.getJ()) && below(
									right(cell)).isMiss())) {
					cell.add(9);
					return;
				} else
					return;
			}
		}
	}

	private void propagateMissLeft(int i, int j) {
		if (isLeft(i, j))
			return;
		InfluenceCell cell = left(get(i, j));
		if (!cell.isHit())
			return;
		for (int p = 2; p <= 5; p++) {
			if (isLeft(cell.getI(), cell.getJ()) || left(cell).isMiss())
				return;
			cell = left(cell);
			if (!cell.isHit()) {
				if (p > 3
						|| (!isTop(cell.getI(), cell.getJ())
								&& above(left(cell)).isMiss()
								&& !isBottom(cell.getI(), cell.getJ()) && below(
									left(cell)).isMiss())) {
					cell.add(9);
					return;
				} else
					return;
			}
		}
	}

	private void propagateMissUp(int i, int j) {
		if (isTop(i, j))
			return;
		InfluenceCell cell = above(get(i, j));
		if (!cell.isHit())
			return;
		for (int p = 2; p <= 5; p++) {
			if (isTop(cell.getI(), cell.getJ()) || above(cell).isMiss())
				return;
			cell = above(cell);
			if (!cell.isHit()) {
				if (p > 3
						|| (!isLeft(cell.getI(), cell.getJ())
								&& above(left(cell)).isMiss()
								&& !isRight(cell.getI(), cell.getJ()) && above(
									right(cell)).isMiss())) {
					cell.add(9);
					return;
				} else
					return;
			}
		}
	}

	private void propagateMissDown(int i, int j) {
		if (isBottom(i, j))
			return;
		InfluenceCell cell = below(get(i, j));
		if (!cell.isHit())
			return;
		for (int p = 2; p <= 5; p++) {
			if (isBottom(cell.getI(), cell.getJ()) || below(cell).isMiss())
				return;
			cell = below(cell);
			if (!cell.isHit()) {
				if (p > 3
						|| (!isLeft(cell.getI(), cell.getJ())
								&& below(left(cell)).isMiss()
								&& !isRight(cell.getI(), cell.getJ()) && below(
									right(cell)).isMiss())) {
					cell.add(9);
					return;
				} else
					return;
			}
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

	/**
	 * Search for deadends and sets their values
	 */
	public void searchDeadends() {
		for (int a = 0; a < getHeight(); a++)
			for (int b = 0; b < getWidth(); b++)
				this.setDeadends(a, b);

	}

	/**
	 * Sets deadends
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 */
	private void setDeadends(int i, int j) {
		if (isDeadend(i, j)) {
			get(i, j).subtract(7);
		}
	}

	/**
	 * Answers whether the cell is deadend
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 * @return <code>true</code> if cell is deadend, <code>false</code> in every
	 *         other case
	 */
	private boolean isDeadend(int i, int j) {
		InfluenceCell cell = get(i, j);
		if (!cell.isMiss() && isEdge(i, j)
				&& (isLeft(i, j) || left(cell).equals(-9))
				&& (isRight(i, j) || right(cell).equals(-9))
				&& (isTop(i, j) || above(cell).equals(-9))
				&& (isBottom(i, j) || below(cell).equals(-9))) {
			return true;
		}
		return false;
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
	 * @param map
	 *            another map
	 */
	public void addMap(InfluenceMap map) {
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				if (get(x, y).compareTo(InfluenceCell.getHitvalue()) >= 0
						|| map.get(x, y).compareTo(InfluenceCell.getHitvalue()) >= 0) {
					get(x, y).add(5);
				} else {
					get(x, y).add(map.getValue(x, y));
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
		String result = "";
		for (int i = 0; i < getHeight(); i++) // change these to ROWS to use the
												// default
		{
			result = result + "|";
			for (int j = 0; j < getWidth(); j++) {
				result = result + get(i, j).getValue();
			}
			result = result + "|\n";
		}
		return result;
	}

	/**
	 * Returns cell that is above the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively above to the given cell
	 */
	protected InfluenceCell above(InfluenceCell cell) {
		return get(cell.getI() - 1, cell.getJ());
	}

	/**
	 * Returns cell that is below the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively below to the given cell
	 */
	protected InfluenceCell below(InfluenceCell cell) {
		return get(cell.getI() + 1, cell.getJ());
	}

	/**
	 * Returns cell that is left the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively left to the given cell
	 */
	protected InfluenceCell left(InfluenceCell cell) {
		return get(cell.getI(), cell.getJ() - 1);
	}

	/**
	 * Returns cell that is right the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link InfluenceCell} relatively right to the given cell
	 */
	protected InfluenceCell right(InfluenceCell cell) {
		return get(cell.getI(), cell.getJ() + 1);
	}

	/**
	 * Computes and sets the maximum value found in cells
	 */
	private void computeHotspotValue() {
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
		computeHotspotValue();
	}
}