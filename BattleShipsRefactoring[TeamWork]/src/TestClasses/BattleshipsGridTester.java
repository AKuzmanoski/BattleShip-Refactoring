package TestClasses;
import org.junit.Test;
import org.junit.Assert;

import Battleships.Grid;

public class BattleshipsGridTester {
	
	@Test
	public void testcase1() {
		// Create grid
		Grid grid = new Grid(20, 20);
		
		// Add and check ships
		Assert.assertEquals(true, grid.addAir(1, 1, 1));
		Assert.assertEquals(true, grid.checkAirPlaced());
		
		Assert.assertEquals(true, grid.addBattle(7, 2, 0));
		Assert.assertEquals(false, grid.checkBattleSunk());
		
		Assert.assertEquals(true, grid.addSub(9, 2, 1));
		Assert.assertEquals(true, grid.checkSubPlaced());
		Assert.assertEquals(false, grid.checkSubSunk());
		
		Assert.assertEquals(true, grid.addDest(13, 17, 1));
		
		Assert.assertEquals(true, grid.addMine(5, 5, 0));
		
		// Shot
		Assert.assertEquals(true, grid.shot(5, 5));
		
		Assert.assertEquals(false, grid.checkMineSunk());
		
		// Check valid place for ship
		Assert.assertEquals(true, grid.isValidPlaceForAShip(5, 6));
		
		// Set air placed
		grid.setAirPlaced();
		
		
		// Test correctness with print methods
		//System.out.println(grid.printIsSunk());
		//Assert.assertEquals("Minesweeper is intact\nSubmarine is intact\nDestroyer is intact\nBattleship is intact\nAircraft Carrier is intact", grid.printIsSunk());
	}
	
	@Test
	public void testcase2() {
		// Create grid
		Grid grid = new Grid(30, 30);
		
		// Add ships
		Assert.assertEquals(false, grid.addAir(1, 28, 0)); // nadvor od mrezata desno
		
		Assert.assertEquals(true, grid.addBattle(7, 2, 0));
		Assert.assertEquals(true, grid.checkBattlePlaced());
		Assert.assertEquals(false, grid.checkBattleSunk());
		
		Assert.assertEquals(false, grid.addSub(6, 3, 1)); // nad drug brod
		
		Assert.assertEquals(true, grid.addDest(13, 17, 1));
		Assert.assertEquals(false, grid.checkDestSunk());
		
		Assert.assertEquals(false, grid.addMine(29, 5, 1)); // nadvor od mrezata dolu
		
		// Check valid place for ship
		Assert.assertEquals(false, grid.isValidPlaceForAShip(5, 5));
				
		// Shot
		Assert.assertEquals(false, grid.shot(5, 5));
		
		// Test correctness with print methods
		//System.out.println(grid.printIsPlaced());
		//Assert.assertEquals("Minesweeper NOT Placed\nDestroyer has been placed\nSubmarine NOT placed\nBattleship has been placed\nAircraft Carrier NOT placed", grid.printIsPlaced());
	}
	
	public static void main(String[] args) {
		
	}
}
