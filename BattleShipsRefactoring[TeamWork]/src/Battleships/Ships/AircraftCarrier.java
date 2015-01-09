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
		super.placeShipOnGrid(board, i, j, isHorizontal);
	}

	@Override
	public int shipGridValue() {
		return 5;
	}

	@Override
	public String printIsPlaced() {
		if(isPlaced){
			return "Aircraft Carrier has been placed";
		}
		return "Aircraft Carrier NOT placed";
	}

	@Override
	public String printIsSunk() {
		if(isSunk()){
			return "Aircraft Carrier is SUNK";
		}else{
			return "Aircraft Carrier is intact";
		}
	}

	

}