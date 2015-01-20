package Battleships;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Battleships.Graphics.AttackPanel;

public class AttackMousePressListener extends MouseAdapter{
	private AttackPanel a;
	private GUI gui;
	private AxisResolver resolver;
	
	public AttackMousePressListener(AttackPanel p, GUI gui2)
	{
		a=p;
		gui=gui2;
		resolver=new AxisResolver();
	}
	  
	
			public void mousePressed(MouseEvent event)
			{
				if(gui.data.getGameState().IsAcceptingPlayerInput())
				{
					Graphics g = a.getGraphics();
					int x = event.getX();
					int y = event.getY();
				
					int gridj= resolver.resolveAxisCoOrdinate(x);
					int gridi= resolver.resolveAxisCoOrdinate(y);
				
					Graphics attackPanelGraphics = a.getGraphics();
					
	                String acceptPlayerShotString = 
	                	gui.data.getGameState().acceptPlayerShot(gridi,gridj, attackPanelGraphics, gui.data.getOutText());
	                
	                //gui.gameState.updatePlayerClick(gridi, gridj, gui);
	                
	                
	                
					System.out.println(acceptPlayerShotString);
					System.out.println("Element corresponds to " + gridi + gridj);
					
					
				}
			}

}
