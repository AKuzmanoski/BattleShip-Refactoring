package Battleships;

import java.util.Observable;

/**
 * Once cell in the influence map
 * 
 */
public class InfluenceCell extends Observable {
	private static final int hitValue = 9;
	private static final int missValue = -5;
	private static int hotspotValue;
	private int i;
	private int j;
	private int value;

	/**
	 * Constructor with position values
	 * 
	 * @param i
	 *            the row index
	 * @param j
	 *            the column index
	 */
	public InfluenceCell(int i, int j) {
		this.i = i;
		this.j = j;
		this.value = 0;
	}

	/**
	 * 
	 * @return the row index of the cell
	 */
	public int getI() {
		return i;
	}

	/**
	 * 
	 * @return the column index of the cell
	 */
	public int getJ() {
		return j;
	}

	/**
	 * 
	 * @return value of the cell
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets value of the cell
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
		
		// Notify observers for change
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Answers to the question whether the cell is hitted
	 * 
	 * @return <code>true</code> if cell is hitted, <code>false</code> in every
	 *         other case
	 */
	public boolean isHit() {
		return value == InfluenceCell.hitValue;
	}

	/**
	 * Answers to the question whether the cell is missed
	 * 
	 * @return <code>true</code> if cell is missed, <code>false</code> in every
	 *         other case
	 */
	public boolean isMiss() {
		return this.value == InfluenceCell.missValue;
	}

	/**
	 * Tells whether the cell is hotspot
	 * 
	 * @return <code>true</code> if cell is hotspot, in every other case
	 *         <code>false</code>
	 */
	public boolean isHotspot() {
		return compareTo(getHotspotValue()) >= 0 && !isHit() && !equals(0);
	}

	/**
	 * Sets cell as missed cell
	 */
	public void miss() {
		this.setValue(InfluenceCell.missValue);
	}

	/**
	 * Sets cell ass hitted cell
	 */
	public void hit() {
		this.setValue(InfluenceCell.hitValue);
	}
	
	public void sunk(int value) {
		if (!isHit()) {
			if (isEven()) {
				subtract(value);
			}
			
			if (isOdd()) {
				subtract(getHitvalue());
			}
		}
	}

	/**
	 * Adds amount to the current cell value
	 * 
	 * @param amount
	 *            to be added to current value
	 */
	public void add(int amount) {
		this.setValue(this.getValue() + amount);
	}

	/**
	 * Subtracts amount to the current cell value
	 * 
	 * @param amount
	 *            to be subtracted to current value
	 */
	public void subtract(int amount) {
		this.setValue(this.getValue() - amount);
	}

	/**
	 * Compares value of the cell with given value
	 * 
	 * @param value
	 *            to be compared
	 * @return -1 if cell has lower value then given value, 0 if they are equal
	 *         or 1 cell value is bigger.
	 */
	public int compareTo(int value) {
		return (new Integer(this.value)).compareTo(new Integer(value));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfluenceCell other = (InfluenceCell) obj;
		if (value != other.value)
			return false;
		return true;
	}

	/**
	 * Compares value of the cell with the given value
	 * 
	 * @param value
	 * @return <code>true</code> if values are equal, <code>false</code> if they
	 *         are not
	 */
	public boolean equals(int value) {
		return this.value == value;
	}

	/**
	 * Tests the value of the cell
	 * @return <code>true</code> if value is odd, <code>false</code> if it is even
	 */
	private boolean isOdd() {
		return value % 2 == 1;
	}

	/**
	 * Tests the value of the cell
	 * @return <code>true</code> if value is even, <code>false</code> if it is odd
	 */
	private boolean isEven() {
		return value % 2 == 0;
	}

	@Override
	public String toString() {
		return value + "";
	}

	/**
	 * @return the value of the map that represents hitted cell
	 */
	public static int getHitvalue() {
		return hitValue;
	}

	/**
	 * @return the value of the map that represents missed cell
	 */
	public static int getMissvalue() {
		return missValue;
	}

	/**
	 * @return the value of the map that represents hotspot cell
	 */
	public static int getHotspotValue() {
		return hotspotValue;
	}

	/**
	 * Sets hotspot value
	 * @param hotspotValue
	 */
	public static void setHotspotValue(int hotspotValue) {
		InfluenceCell.hotspotValue = hotspotValue;
	}
}
