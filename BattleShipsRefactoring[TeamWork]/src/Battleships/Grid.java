package Battleships;
/*
 * Author: Michael Okarimia
 * Created: 10 November 2004 15:43:46
 * Modified: 10 November 2004 15:43:46
 * This program creates a grid that a game of 
 * Battleships can be played on
 * Improvements from code1 are:
 * 1: Destroyer ship now added
 * 2: Horizontal and vertical positioning is determined by int not char
 * 3: Grid is now Serializable
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.junit.validator.ValidateWith;

import Battleships.Ships.*;
import Battleships.exception.PositionExceedsBoardException;
import Battleships.exception.PositionOccupiedException;
	
	
public class Grid implements Serializable 
{	
	private int[][] board;// two dimensional array to hold the board data
	
	private int userRow;
	private int userColumn;
	
	
	
	private HashMap<String,Ship> ships;
	
	
		/**
		Constructs a two dimensional array which represent the game board. 
		All elements in the two dimensional array on the grid are set to (zero) 0, which represents an empty grid
		The board's dimensions are determined by the parameters i and j
		
		@param i the number of rows of the grid
		@param j the number of columns of the grid
	*/	
	public Grid(int i, int j)
	{
		ships=new HashMap<>();
		ships.put("Minesweeper", new Minesweeper());
		ships.put("Destroyer", new Destroyer());
		ships.put("AircraftCarrier" , new AircraftCarrier());
		ships.put("Submarine", new Submarine());
		ships.put("Battleship", new Battleship());
		
		userRow = i;
		userColumn = j;
		
		board = new int[userRow][userColumn];
		
		for (int a = 0; i< userRow; i++)
			for (int b = 0; j < userColumn; j++)
				board[a][b] = 0;
	}
	/**
		Returns the number of columns in the grid
	*/
	public int getWidth()
	{
		return userColumn;
	}
	
	/**
		Returns the number of rows in the grid
	*/
	
	public int getLength()
	{
		return userRow;
	}
	
	/**
		Checks the grid references and returns a boolean value if there is a ship on that spot
		
		@param i the column of the grid reference
		@param j the row of the grid reference
		
		@return a boolean value, true if the grid contains a ship and false if it contains either a miss or empty		
	*/
	private boolean validPlace(int index){
		return (index > 1 && index < 8 ) ;
	}
	public boolean isValidPlaceForAShip(int i, int j)
	{	
		return  (validPlace(this.getGridVal(i,j))) ;
		
	}
	
	/**
		Checks if all ships are sunk
	*/	
	
	public boolean allShipsSunk()
	{
		for(Ship s:ships.values()){
			if(!s.isSunk()) return false;
		}
		return true;
	}		
	
	public boolean checkMineSunk()
	{
		return ships.get("Minesweeper").isSunk();		
	}
	
	public boolean checkSubSunk()
	{
		return ships.get("Submarine").isSunk();
	}
	
	public boolean checkDestSunk()
	{
		
		return ships.get("Destroyer").isSunk();
	}
	
	public boolean checkBattleSunk()
	{
		
		return ships.get("Battleship").isSunk();
	}
	
	public boolean checkAirSunk()
	{	
		
		return ships.get("AircraftCarrier").isSunk();
	}
	
	
	
	/**
		Checks if the minesweeper has been placed
	*/
	
	public boolean checkMinePlaced()
	{
		if(!ships.containsKey("Minesweeper"))return false;
		return ships.get("Minesweeper").checkIsShipPlaced();
					
	}
	/**
		Sets minePlaced flag to true
	*/
	
	public void setMinePlacedTrue()
	{
		ships.get("Minesweeper").setShipAsPlaced();
	}
	
	public boolean addMine(int i, int j, int s)
	{
		boolean isHorizontal = (s == 0);
		
		try
		{
			if(!checkMinePlaced()){
			ships.put("Minesweeper", new Minesweeper(this, i, j, isHorizontal));
			}
		}
		
		catch (PositionOccupiedException Exception)
		{
			System.out.println(String.format("Cannot place %s Minesweeper here, position is occupied \n", (isHorizontal? "horizontal" : "vertical")));
		}
		
		catch (PositionExceedsBoardException Exception)
		{
			System.out.println(String.format("Cannot place %s Minesweeper here, ship will not fit on grid \n", (isHorizontal? "horizontal" : "vertical")));
		}

		return checkMinePlaced();
	}
	
	
	
	/**
		Checks if the Sub has been placed
	*/
	
	public boolean checkSubPlaced()
	{
		if(!ships.containsKey("Submarine"))return false;
		return ships.get("Submarine").checkIsShipPlaced();
	}
	/**
		Sets subPlaced flag to true
	*/
	
	public void setSubPlacedTrue()
	{
		ships.get("Submarine").setShipAsPlaced();
	}
	
	/**
	Adds an Air object to the grid
*/

public boolean addAir(int i, int j, int s)
{
	boolean isHorizontal = (s == 0);
	
	try
	{
		if(!checkAirPlaced()){
	      ships.put("AircraftCarrier", new AircraftCarrier(this, i, j, isHorizontal));
		}
	}
	
	catch (PositionOccupiedException Exception)
	{
		System.out.println(String.format("Cannot place %s Aircraft Carrier here, position is occupied \n", (isHorizontal? "horizontal" : "vertical")));
	}
	
	catch (PositionExceedsBoardException Exception)
	{
		System.out.println(String.format("Cannot place %s Aircraft Carrier here, ship will not fit on grid \n", (isHorizontal? "horizontal" : "vertical")));
	}
	

	 
	 return checkAirPlaced();
		
}

	
	
	/**
		Adds a Submarine object to the grid
	*/
	
	public boolean addSub(int i, int j, int s)
	{
		boolean isHorizontal = (s == 0);
		
		try{
		    if(!checkSubPlaced()){
			ships.put("Submarine", new Submarine(this, i, j, isHorizontal));
		    }
			
		}
		
		
		catch (PositionOccupiedException Exception)
		{
			System.out.println(String.format("Cannot place %s submarine here, position is occupied \n", (isHorizontal? "horizontal" : "vertical")));
		}
		
		catch (PositionExceedsBoardException Exception)
		{
			System.out.println(String.format("Cannot place %s submarine here, ship will not fit on grid \n", (isHorizontal? "horizontal" : "vertical")));
		}


		 return checkSubPlaced();
	}
	

	/**
		Checks if the Destroyer has been placed
	*/
	
	public boolean checkDestPlaced()
	{
		if(!ships.containsKey("Destroyer"))return false;
		return ships.get("Destroyer").checkIsShipPlaced();
	}
	/**
		Sets destPlaced flag to true
	*/
	
	public void setDestPlacedTrue()
	{
		 ships.get("Destroyer").setShipAsPlaced();
	}
	
	/**
		Adds a Destroyer object to the grid
	*/
	
	public boolean addDest(int i, int j, int s)
	{
		boolean isHorizontal = (s == 0);
		
		try
		{
			if(!checkDestPlaced()){
			 ships.put("Destroyer", new Destroyer(this, i, j, isHorizontal));
			}
		}
		
		catch (PositionOccupiedException Exception)
		{
			System.out.println(String.format("Cannot place %s destroyer here, position is occupied \n", (isHorizontal? "horizontal" : "vertical")));
		}
		
		catch (PositionExceedsBoardException Exception)
		{
			System.out.println(String.format("Cannot place %s destroyer here, ship will not fit on grid \n", (isHorizontal? "horizontal" : "vertical")));
		}
		
		return checkDestPlaced();
	}
		
	
	/**
		Checks if the Battleship has been placed
	*/
		public boolean checkBattlePlaced()
		{
			if(!ships.containsKey("Battleship"))return false;
			return ships.get("Battleship").checkIsShipPlaced();
		}
	/**
		Sets battlePlaced flag to true
	*/
	
	public void setBattlePlacedTrue()
	{
		ships.get("Battleship").setShipAsPlaced();
	}
	
	/**
		Adds a Battle object to the grid
	*/
	
	public boolean addBattle(int i, int j, int s)
	{
		boolean isHorizontal = (s == 0);
		
		try
		{
			if(!checkBattlePlaced()){
			ships.put("Battleship", new Battleship(this, i, j, isHorizontal));
			}
		}
		
		catch (PositionOccupiedException Exception)
		{
			System.out.println(String.format("Cannot place %s battleship here, position is occupied \n", (isHorizontal? "horizontal" : "vertical")));
		}
		
		catch (PositionExceedsBoardException Exception)
		{
			System.out.println(String.format("Cannot place %s battleship here, ship will not fit on grid \n", (isHorizontal? "horizontal" : "vertical")));
		}
			return checkBattlePlaced();
	}
	
	
	/**
		Checks if the aircraftCarrier has been placed
	*/
		public boolean checkAirPlaced()
	{
		if (isAirPlaced() == true)
			return true;
		
		else return false;
	}
	/**
		Sets airPlaced flag to true
	*/
	
	public void setAirPlacedTrue()
	{
		setAirPlaced(true);
	}
	
	/**Checks if all ships have been placed*/
	public boolean allShipsPlaced()
	{
		
		if((checkMinePlaced()&& checkSubPlaced()&& checkDestPlaced()&& checkBattlePlaced()&& checkAirPlaced() ))
		return true;
		else    
		return false;
		
	}	
	
	
	private boolean biggerThanGrid(int i,int j){
		return (i > userRow || j > userColumn);
	}
	private boolean positionNegative(int i,int j){
		return (i < 0 || j < 0);
	}
	
	/**
		This method is used by the ship classes to add the ships to the grid.
		Sets the value of a grid location to a specified integer. The grid location must be set to (zero) 0.
		@param i the row index
		@param j the column index
		@param value the value of the square 
	*/
	
	
	public void set(int i, int j, int value)
	{
		if(biggerThanGrid(i, j))
			throw new IllegalArgumentException("Number is bigger that the grid size");
		if(positionNegative(i, j) || value < 0) 
			throw new IllegalArgumentException("Number cannot be negative");
		if(board[i][j] != 0)
			throw new IllegalArgumentException("Initial Position occupied");
		if(value == 0)
			throw new IllegalArgumentException("Number cannot = 0");
		board[i][j] = value;
	}
	
	/**
		This method is used by the shot() method to update the grid.
		Sets the value of a grid location to a specified integer. The grid location must be set to (zero) 0.
		@param i the row index
		@param j the column index
		@param value the value of the square 
	*/
	public void update(int i, int j, int value)
	{
		if(biggerThanGrid(i, j))
			throw new IllegalArgumentException("Number is bigger that the grid size");
		if(positionNegative(i, j)) 
			throw new IllegalArgumentException("Number cannot be negative");
		if(value == 0)
			throw new IllegalArgumentException("Number cannot = 0");
		board[i][j] = value;
	}
	
	
	/**
		Returns the value of the given grid index
		@param i the row index
		@param j the column index
	*/
	public int getGridVal(int i, int j)
	{
		if(positionNegative(i, j))
			throw new IllegalArgumentException("Number cannot be negative");
		if(biggerThanGrid(i, j))
			throw new IllegalArgumentException("Number is bigger that the grid size");
		return board[i][j];
	}
	
	/**
		Fires a shot on the grid
	*/
	public boolean shot(int i, int j)
	{
		int sqr = this.getGridVal(i,j);
		
		//String output nikade ne se koristi pa ja izbrisav
			
		boolean hit = false;
		
		//ako e nekoja od vrednostite 2,3,4,5 ili 7
		for(Ship s:ships.values()){
			if(s.shipGridValue()==sqr){
				s.scoreHit();
				this.update(i,j,(sqr - 8)); 
				return true;
			}
		}
		//ako ne e vo sekoj dr slucaj ke vrati false, samo vo sqr=0 ke napravi plus update
		if(sqr==0){
			this.update(i,j,1); 
		}
	   return false;
	
	}
	
	/**
		Creates a string representation of the game board like so:
		|000|
		|050|
		|000|
		
		@return the string representation
	*/
	
	public String toString()
	{
		String r = "";
		for (int i = 0; i < userRow; i++) //change these to ROWS to use the default
		{
			
			r = r + "|";
			for (int j = 0; j < userColumn; j++)//change this to CoLumns for default
				r = r + board[i][j];
			r= r + "|\n";
		}
		return r;
	}
	

	public void setAirPlaced(boolean airPlaced) {
		ships.get("AircraftCarrier").setShipAsPlaced();
	}
	public boolean isAirPlaced() {
		if(!ships.containsKey("AircraftCarrier"))return false;
		return ships.get("AircraftCarrier").checkIsShipPlaced();
	}
	public boolean checkIsShipPlaced(Ship ship) {
		return ship.checkIsShipPlaced();	
	}
	public void setShipAsPlaced(Ship ship) {
		ship.setShipAsPlaced();
		
	}
	
	/**
	 * Answers to the question whether the grid cell is empty (no ship is placed on).
	 * @param i the row index
	 * @param j the column index
	 * @return <code>true</code> if cell is empty, <code>false</code> in every other case
	 */
	public boolean isEmpty(int i, int j) {
		return getGridVal(i, j) == 0;
	}
}
