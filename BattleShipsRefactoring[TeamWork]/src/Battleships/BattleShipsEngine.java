package Battleships;

import javax.swing.JTextField;

import Battleships.Graphics.AttackPanel;
import Battleships.Graphics.HomePanel;
import Battleships.Graphics.InfluencePanel;


public class BattleShipsEngine {
	private static final int mineSunk = -6;
	private static final int destSunk = -1;
	private static final int subSunk = -5;
	private static final int battleSunk = -4;
	private static final int airSunk = -3;
	public AttackPanel attackPanel;
	public HomePanel homePanel;
	public InfluencePanel influenceMapPanel;
	public JTextField outText;
	public int i;
	public int j;
	public GameState gameState;
	public boolean agentWins;
	public boolean horiz;
	public boolean showMap;
	public boolean minePlaced;
	public boolean destPlaced;
	public boolean subPlaced;
	public boolean battlePlaced;
	
	public boolean agentMineSunk;
	public boolean agentDestSunk;
	public boolean agentSubSunk;
	public boolean agentAirSunk;
	public boolean playerMineSunk;
	public boolean paintMineSunk;
	public boolean paintDestSunk;
	public boolean paintSubSunk;
	public boolean paintBattleSunk;
	public boolean paintAirSunk;

	public BattleShipsEngine() {
	}
	

	public static void main (String args[])
	{
	
		GUI gui = new GUI(new GameState());
		Agent smith = new Agent();

		
		System.out.println("PlayerTurn " + gui.data.gameState.isPlayerTurn());
		System.out.println("Deployed " + gui.data.gameState.isBothPlayerAndAgentShipsDeployed());
		System.out.println("PlayerTurn " + gui.data.gameState.isPlayerTurn());	
		System.out.println("Deployed " + gui.data.gameState.isBothPlayerAndAgentShipsDeployed());
			
	
		while(!gui.data.gameState.getPlayerHomeGrid().allShipsPlaced())
		{
				//PlayerDeploymentPhase, wait for player to place all their ships
		}
		
		gui.data.gameState.addAgentShips(smith.getGrid());
		
		

		gui.data.gameState.setPlayerTurn();
		gui.data.outText.setText(gui.data.gameState.turnToString());

		
		while (!gui.getGameOver())
		{
			
			while (gui.data.gameState.isPlayerTurn() && !gui.getGameOver())
			{
				gui.data.gameState.setShipSunkStates();
				if(gui.data.gameState.areAllAgentShipsSunk())
				{
					System.out.println("All sunk");
					gui.data.gameState.SetGameOver();
					gui.data.gameState.PlayerIsTheWinner();
				}
			}
			gui.repaint();
		
			smith.setAttackGrid( gui.data.gameState.getCompAtt());
			smith.setMap(gui.data.gameState.getInfluenceMap());
			while(gui.data.gameState.isAgentTurn() && !gui.data.gameState.IsGameOver())
			{
			
				System.out.println("agent turn");
				smith.nextShot();
				gui.agentShot(smith.getI(),smith.getJ());
				System.out.println("shot at " + smith.getI() + " " +smith.getJ());
				System.out.println(gui.data.gameState.getCompAtt().toString());
				//if(gameState.playerHome.get(i,j
				
				
				
				determineIfShotSunkAShip(gui, smith);
				
				gui.data.gameState.setShipSunkStates();
				
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				if(g.getAgentShipsSunk())
				{
					g.setGameOver();
					g.setPlayerWins();
				}
				*/
				if(gui.data.gameState.getPlayerShipsSunk())
				{
					gui.setAgentWins();
					gui.data.gameState.SetGameOver();
					gui.data.gameState.setPlayerTurn();
					
				}			
			}
			

		}
		
		System.out.println("Game Over!");
		if(gui.data.gameState.isPlayerWinner())
		{
			System.out.println("Player Wins");
			gui.setOut("Game Over! You Win!");
		}
		else
		{
			System.out.println("Computer Wins");
			gui.setOut("Game Over! Agent Wins!");
		}
		
		}


	private static void determineIfShotSunkAShip(GUI gui, Agent smith) {
		System.out.println("Player Home board \n" +gui.data.gameState.getPlayerHomeGrid().toString());
		if(gui.checkMineSunk()&& !gui.getPaintMineSunk())
		{
				for (int i = 0; i < 10; i++) //change these to ROWS to use the default
				{
					for (int j = 0; j < 10; j++)//change this to CoLumns for default
					{
						if(gui.getGridValue(i,j) ==mineSunk)
						{
							smith.setSunk(i,j);
							gui.setPaintMineSunk();
						}
					}
				}
		}
		
		if(gui.checkDestSunk() && !gui.getPaintDestSunk())
		{
				for (int i = 0; i < 10; i++) //change these to ROWS to use the default
				{
					for (int j = 0; j < 10; j++)//change this to CoLumns for default
					{
						if(gui.getGridValue(i,j) ==destSunk)
						{
							smith.setSunk(i,j);
							gui.setPaintDestSunk();
						}
					}
				}
		}
		
		if(gui.checkSubSunk() && !gui.getPaintSubSunk())
		{
				for (int i = 0; i < 10; i++) //change these to ROWS to use the default
				{
					for (int j = 0; j < 10; j++)//change this to CoLumns for default
					{
						if(gui.getGridValue(i,j) ==subSunk)
						{
							smith.setSunk(i,j);
							gui.setPaintSubSunk();
						}
					}
				}
		}
		
		if(gui.checkBattleSunk() && !gui.getPaintBattleSunk())
		{
				for (int i = 0; i < 10; i++) //change these to ROWS to use the default
				{
					for (int j = 0; j < 10; j++)//change this to CoLumns for default
					{
						if(gui.getGridValue(i,j) ==battleSunk)
						{
							smith.setSunk(i,j);
							gui.setPaintBattleSunk();
						}
					}
				}
		}
		
		if(gui.checkAirSunk() && !gui.getPaintAirSunk())
		{
				for (int i = 0; i < 10; i++) //change these to ROWS to use the default
				{
					for (int j = 0; j < 10; j++)//change this to CoLumns for default
					{
						if(gui.getGridValue(i,j) ==airSunk)
						{
							smith.setSunk(i,j);
							gui.setPaintAirSunk();
						}
					}
				}
		}
	}
	public void resetPlaced(){
		this.minePlaced = false;
		this.destPlaced = false;
		this.subPlaced = false;
		this.battlePlaced = false;
	}
	public void resetSunk(){
		this.agentMineSunk= false;
		this.agentDestSunk= false;
		this.agentSubSunk= false;		
		this.playerMineSunk= false;
	}
	public void resetPaintSunk(){
		this.paintMineSunk= false;
		this.paintDestSunk= false;
		this.paintSubSunk= false;
		this.paintBattleSunk= false;
		this.paintAirSunk= false;
	}
	public void resetEngine(){
		this.i = 0;
		this.j = 0;
		
		this.gameState = new GameState();
		this.agentWins= false;

		 resetPlaced();
		 resetSunk();
		 			 			
		 this.showMap= true;
	}


	public void setState(GameState paramGameState) {
		resetPlaced();
		showMap = true;
		resetPaintSunk();
		gameState = paramGameState;
	}
}