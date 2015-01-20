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
	private AttackPanel attackPanel;
	private HomePanel homePanel;
	private InfluencePanel influenceMapPanel;
	private JTextField outText;
	private int i;
	private int j;
	private GameState gameState;
	private boolean agentWins;
	private boolean horiz;
	private boolean showMap;
	private boolean minePlaced;
	private boolean destPlaced;
	private boolean subPlaced;
	private boolean battlePlaced;
	
	private boolean agentMineSunk;
	private boolean agentDestSunk;
	private boolean agentSubSunk;
	private boolean agentAirSunk;
	private boolean playerMineSunk;
	private boolean paintMineSunk;
	private boolean paintDestSunk;
	private boolean paintSubSunk;
	private boolean paintBattleSunk;
	private boolean paintAirSunk;

	public BattleShipsEngine() {
	}
	

	public static void main (String args[])
	{
	
		GUI gui = new GUI(new GameState());
		Agent smith = new Agent();

		
		System.out.println("PlayerTurn " + gui.data.getGameState().isPlayerTurn());
		System.out.println("Deployed " + gui.data.getGameState().isBothPlayerAndAgentShipsDeployed());
		System.out.println("PlayerTurn " + gui.data.getGameState().isPlayerTurn());	
		System.out.println("Deployed " + gui.data.getGameState().isBothPlayerAndAgentShipsDeployed());
			
	
		while(!gui.data.getGameState().getPlayerHomeGrid().allShipsPlaced())
		{
				//PlayerDeploymentPhase, wait for player to place all their ships
		}
		
		gui.data.getGameState().addAgentShips(smith.getGrid());
		
		

		gui.data.getGameState().setPlayerTurn();
		gui.data.getOutText().setText(gui.data.getGameState().turnToString());

		
		while (!gui.getGameOver())
		{
			
			while (gui.data.getGameState().isPlayerTurn() && !gui.getGameOver())
			{
				gui.data.getGameState().setShipSunkStates();
				if(gui.data.getGameState().areAllAgentShipsSunk())
				{
					System.out.println("All sunk");
					gui.data.getGameState().SetGameOver();
					gui.data.getGameState().PlayerIsTheWinner();
				}
			}
			gui.repaint();
		
			smith.setAttackGrid( gui.data.getGameState().getCompAtt());
			smith.setMap(gui.data.getGameState().getInfluenceMap());
			while(gui.data.getGameState().isAgentTurn() && !gui.data.getGameState().IsGameOver())
			{
			
				System.out.println("agent turn");
				smith.nextShot();
				gui.agentShot(smith.getI(),smith.getJ());
				System.out.println("shot at " + smith.getI() + " " +smith.getJ());
				System.out.println(gui.data.getGameState().getCompAtt().toString());
				//if(gameState.playerHome.get(i,j
				
				
				
				determineIfShotSunkAShip(gui, smith);
				
				gui.data.getGameState().setShipSunkStates();
				
			
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
				if(gui.data.getGameState().getPlayerShipsSunk())
				{
					gui.setAgentWins();
					gui.data.getGameState().SetGameOver();
					gui.data.getGameState().setPlayerTurn();
					
				}			
			}
			

		}
		
		System.out.println("Game Over!");
		if(gui.data.getGameState().isPlayerWinner())
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
		System.out.println("Player Home board \n" +gui.data.getGameState().getPlayerHomeGrid().toString());
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
		this.setMinePlaced(false);
		this.setDestPlaced(false);
		this.setSubPlaced(false);
		this.setBattlePlaced(false);
	}
	public void resetSunk(){
		this.setAgentMineSunk(false);
		this.setAgentDestSunk(false);
		this.setAgentSubSunk(false);		
		this.setPlayerMineSunk(false);
	}
	public void resetPaintSunk(){
		this.setPaintMineSunk(false);
		this.setPaintDestSunk(false);
		this.setPaintSubSunk(false);
		this.setPaintBattleSunk(false);
		this.setPaintAirSunk(false);
	}
	public void resetEngine(){
		this.i = 0;
		this.j = 0;
		
		this.setGameState(new GameState());
		this.setAgentWins(false);

		 resetPlaced();
		 resetSunk();
		 			 			
		 this.setShowMap(true);
	}


	public AttackPanel getAttackPanel() {
		return attackPanel;
	}


	public HomePanel getHomePanel() {
		return homePanel;
	}


	public InfluencePanel getInfluenceMapPanel() {
		return influenceMapPanel;
	}


	public void setState(GameState paramGameState) {
		resetPlaced();
		setShowMap(true);
		resetPaintSunk();
		setGameState(paramGameState);
	}


	public void setDataPanels(GUI gui) {
		// attack panel add listener
		attackPanel = new AttackPanel();
		attackPanel.addMouseListener(new AttackMousePressListener(
				attackPanel, gui));
	
		homePanel = new HomePanel();
		homePanel.addMouseListener(new HomeMousePressListener(
				homePanel, gui));
	
		influenceMapPanel = new InfluencePanel();
	}


	public GameState getGameState() {
		return gameState;
	}


	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}


	public JTextField getOutText() {
		return outText;
	}


	public void setOutText(JTextField outText) {
		this.outText = outText;
	}


	public boolean isAgentWins() {
		return agentWins;
	}


	public void setAgentWins(boolean agentWins) {
		this.agentWins = agentWins;
	}


	public boolean isHoriz() {
		return horiz;
	}


	public void setHoriz(boolean horiz) {
		this.horiz = horiz;
	}


	public boolean isShowMap() {
		return showMap;
	}


	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}


	public boolean isMinePlaced() {
		return minePlaced;
	}


	public void setMinePlaced(boolean minePlaced) {
		this.minePlaced = minePlaced;
	}


	public boolean isDestPlaced() {
		return destPlaced;
	}


	public void setDestPlaced(boolean destPlaced) {
		this.destPlaced = destPlaced;
	}


	public boolean isSubPlaced() {
		return subPlaced;
	}


	public void setSubPlaced(boolean subPlaced) {
		this.subPlaced = subPlaced;
	}


	public boolean isBattlePlaced() {
		return battlePlaced;
	}


	public void setBattlePlaced(boolean battlePlaced) {
		this.battlePlaced = battlePlaced;
	}


	public boolean isAgentMineSunk() {
		return agentMineSunk;
	}


	public void setAgentMineSunk(boolean agentMineSunk) {
		this.agentMineSunk = agentMineSunk;
	}


	public boolean isAgentDestSunk() {
		return agentDestSunk;
	}


	public void setAgentDestSunk(boolean agentDestSunk) {
		this.agentDestSunk = agentDestSunk;
	}


	public boolean isAgentSubSunk() {
		return agentSubSunk;
	}


	public void setAgentSubSunk(boolean agentSubSunk) {
		this.agentSubSunk = agentSubSunk;
	}


	public boolean isAgentAirSunk() {
		return agentAirSunk;
	}


	public void setAgentAirSunk(boolean agentAirSunk) {
		this.agentAirSunk = agentAirSunk;
	}


	public boolean isPlayerMineSunk() {
		return playerMineSunk;
	}


	public void setPlayerMineSunk(boolean playerMineSunk) {
		this.playerMineSunk = playerMineSunk;
	}


	public boolean isPaintMineSunk() {
		return paintMineSunk;
	}


	public void setPaintMineSunk(boolean paintMineSunk) {
		this.paintMineSunk = paintMineSunk;
	}


	public boolean isPaintDestSunk() {
		return paintDestSunk;
	}


	public void setPaintDestSunk(boolean paintDestSunk) {
		this.paintDestSunk = paintDestSunk;
	}


	public boolean isPaintSubSunk() {
		return paintSubSunk;
	}


	public void setPaintSubSunk(boolean paintSubSunk) {
		this.paintSubSunk = paintSubSunk;
	}


	public boolean isPaintBattleSunk() {
		return paintBattleSunk;
	}


	public void setPaintBattleSunk(boolean paintBattleSunk) {
		this.paintBattleSunk = paintBattleSunk;
	}


	public boolean isPaintAirSunk() {
		return paintAirSunk;
	}


	public void setPaintAirSunk(boolean paintAirSunk) {
		this.paintAirSunk = paintAirSunk;
	}
}