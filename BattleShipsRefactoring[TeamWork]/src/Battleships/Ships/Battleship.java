package Battleships.Ships;
/*
 * Author: Michael
 * Created: 07 December 2004 23:01:02
 * Modified: 07 December 2004 23:01:02
 */

import java.io.Serializable;

import Battleships.Grid;
import Battleships.exception.PositionExceedsBoardException;
import Battleships.exception.PositionOccupiedException;

public class Battleship extends Ship implements Serializable
{
		
	public Battleship(Grid board ,int i, int j, boolean isHorizontal)
	{
		super(4);
		super.placeShipOnGrid(board, i, j, isHorizontal);
	}
	@Override
	public int shipGridValue() {
		return 4;
	}
	@Override
	public String printIsPlaced() {
		if(isPlaced){
			return "Battleship has been placed";
		}else{
			return "Battleship NOT placed";
		}
	}
	@Override
	public String printIsSunk() {
		if(isSunk()){
			return "Battleship is SUNK";
		}else{
			return "Battleship is intact";
		}
	}
	
}
