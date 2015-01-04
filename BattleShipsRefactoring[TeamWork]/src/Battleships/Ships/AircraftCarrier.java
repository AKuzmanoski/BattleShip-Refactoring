package Battleships.Ships;

/*
 * Author: Michael
 * Created: 08 December 2004 09:37:10
 * Modified: 08 December 2004 09:37:10
 */

import java.io.Serializable;

import Battleships.Grid;

public class AircraftCarrier extends Ship implements Serializable {
	

	public AircraftCarrier(Grid board, int i, int j, boolean isHorizontal) {
		super(5);
		super.placeShipOnGrid(board, i, j, isHorizontal, intactSegments);
	}

	@Override
	protected int shipGridValue(Ship ship) {
		return 5;
	}

	

}