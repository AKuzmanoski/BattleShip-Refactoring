package Battleships.Ships;
/*
 * Author: Michael
 * Created: 05 December 2004 18:57:44
 * Modified: 05 December 2004 18:57:44
 */
import java.io.Serializable;

import Battleships.Grid;

public class Minesweeper extends Ship implements Serializable
{

	
	public Minesweeper(Grid board, int i, int j, boolean isHorizontal)
	{
		super(2);
		super.placeShipOnGrid(board, i, j, isHorizontal, intactSegments);
	
	}
	@Override
	protected int shipGridValue(Ship ship) {
		return 2;
	}

}
