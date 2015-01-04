package Battleships.Ships;

/*
 * Author: Michael
 * Created: 07 December 2004 16:50:18
 * Modified: 07 December 2004 16:50:18
 */

import java.io.Serializable;

import Battleships.Grid;

public class Destroyer extends Ship implements Serializable {
	//remainingIntactCells renamed to Intactsegments


	public Destroyer(Grid board, int i, int j, boolean isHorizontal) {
		super(3);
		super.placeShipOnGrid(board, i, j, isHorizontal, intactSegments);
	}

	/**
	 * Reduces the number of undamaged segments of the ship by one
	 */
	public void scoreHit() {
		intactSegments--;

		if (intactSegments < 0)
			throw new IllegalArgumentException(
					"remainingIntactCells is less than 0");
	}
   
	@Override
	protected int shipGridValue(Ship ship) {
		return 7;
	}

}
