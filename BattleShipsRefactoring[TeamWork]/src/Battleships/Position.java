package Battleships;

public class Position {
	private int i;
	private int j;
	
	public Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
	/**
	 * Returns {@link Position} that is above the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link Position} relatively above to the given cell
	 */
	public Position above() {
		return new Position(getI() - 1, getJ());
	}
	
	/**
	 * Returns {@link Position} that is below the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link Position} relatively below to the given cell
	 */
	protected Position below() {
		return new Position(getI() + 1, getJ());
	}
	
	/**
	 * Returns {@link Position} that is left the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link Position} relatively left to the given cell
	 */
	protected Position left() {
		return new Position(getI(), getJ() - 1);
	}
	
	/**
	 * Returns {@link Position} that is right the given cell if it exists
	 * 
	 * @param cell
	 * @return {@link Position} relatively right to the given cell
	 */
	protected Position right() {
		return new Position(getI(), getJ() + 1);
	}
}
