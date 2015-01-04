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
		super.placeShipOnGrid(board, i, j, isHorizontal, intactSegments);
	}
	@Override
	protected int shipGridValue(Ship ship) {
		return 4;
	}

}
