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

	public Destroyer() {
		super(3);
	}

	public Destroyer(Grid board, int i, int j, boolean isHorizontal) {
		super(3);
		super.placeShipOnGrid(board, i, j, isHorizontal);
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
	public int shipGridValue() {
		return 7;
	}

	@Override
	public String printIsPlaced() {
		if(isPlaced){
			return "Destroyer has been placed";
		}
		return "Destroyer NOT Placed";
	}

	@Override
	public String printIsSunk() {
		if(isSunk()){
			return "Destroyer is SUNK";
		}else{
			return "Destroyer is intact";
		}
	}
	
}
