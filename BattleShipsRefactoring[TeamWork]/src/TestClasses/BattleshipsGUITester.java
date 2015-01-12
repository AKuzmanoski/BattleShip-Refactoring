package TestClasses;
import org.junit.Test;
import org.junit.Test;
import org.junit.Assert;

import Battleships.GUI;
import Battleships.GameState;
import Battleships.Grid;

public class BattleshipsGUITester {
	
	@Test
	public void testcase1() {
		// Create grid
		GameState gameState=new GameState();
		GUI gui=new GUI(gameState);
		
		
		// Add and check ships
        gui.placeAir(0, 7);
        Assert.assertEquals(false,gui.submarineCanBePlaced());
		Assert.assertEquals("Aircraft Carrier Will Not Fit Here",gui.getOutText().getText());
		String result="|0000055555|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n";
		Assert.assertEquals(result, gui.placeAir(0, 5));
	    Assert.assertEquals("Air Placed", gui.getOutText().getText());
		Assert.assertEquals("", gui.placeDest(2,1));
	    Assert.assertEquals("Air Placed", gui.getOutText().getText());
	    String result2="not valid"+result; 
	   // System.out.println(result2);
	   // System.out.println(gui.placeBattle(0, 5));
	    Assert.assertEquals(result2,gui.placeBattle(0, 5));
	    Assert.assertEquals("Battleships Will Not Fit Here", gui.getOutText().getText());
	    Assert.assertEquals("|0000055555|\n|0000444400|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",gui.placeBattle(1, 4));
		Assert.assertEquals("Battleships Will Not Fit Here",gui.getOutText().getText());

		 Assert.assertEquals(false,gui.submarineCanBePlaced());
		//dest
	    Assert.assertEquals("not valid|0000055555|\n|0000444400|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",gui.placeDest(3, 9));
	    Assert.assertEquals("Destroyer Will Not Fit Here", gui.getOutText().getText());
	  
	    
	    Assert.assertEquals("|0000055555|\n|0000444400|\n|0000000000|\n|0000777000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",gui.placeDest(3,4));
	    Assert.assertEquals("Destroyer Placed", gui.getOutText().getText());
	    
	    //sub
	    Assert.assertEquals(true,gui.submarineCanBePlaced());
	    Assert.assertEquals("not valid|0000055555|\n|0000444400|\n|0000000000|\n|0000777000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",gui.placeSub(4, 9));
	    Assert.assertEquals("Submarine Will Not Fit Here", gui.getOutText().getText());
	    
	 
	    Assert.assertEquals("|0000055555|\n|0000444400|\n|0000000000|\n|0000777000|\n|3330000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",gui.placeSub(4,0));
	    Assert.assertEquals("Submarine Placed", gui.getOutText().getText());
	 
	    //mine
	    
	    Assert.assertEquals("not valid|0000055555|\n|0000444400|\n|0000000000|\n|0000777000|\n|3330000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",gui.placeMine(3,4));
	    Assert.assertEquals("Minesweeper Will Not Fit Here", gui.getOutText().getText());
	    
	 
	    Assert.assertEquals("|0000055555|\n|0000444400|\n|0000000000|\n|0000777000|\n|3330000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000022|\n|0000000000|\n",gui.placeMine(8,8));
	    Assert.assertEquals("Player turn, take a shot", gui.getOutText().getText());
	    
     
	 
	    
	}
	  @Test
			public void testcase2() {
		  GameState gameState=new GameState();
			GUI gui=new GUI(gameState);
			gui.rotate();
			gui.placeAir(8, 1);
			Assert.assertEquals("Ship Will Be Placed Vertically",gui.getOutText().getText());
			gui.placeAir(5, 0);
			Assert.assertEquals("Air Placed",gui.getOutText().getText());
			gui.placeBattle(3, 1);
			Assert.assertEquals("Battleship Placed",gui.getOutText().getText());
			gui.placeDest(3, 1);
			Assert.assertEquals("Battleship Placed",gui.getOutText().getText());
		 }
}