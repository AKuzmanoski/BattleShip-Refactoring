package Battleships;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Battleships.Graphics.HomePanel;

public class HomeMousePressListener extends MouseAdapter
{
	
	private HomePanel a;
	private GUI gui;
	private AxisResolver resolver;
	
	public HomeMousePressListener(HomePanel p, GUI gui2)
	{
		a=p;
		gui=gui2;
		resolver=new AxisResolver();
	}
	
	
			public void mousePressed(MouseEvent event)
			{
				
				Graphics g = a.getGraphics();
				
				int x = event.getX();
				int y = event.getY();
			
				int gridj= resolver.resolveAxisCoOrdinate(x);
				int gridi= resolver.resolveAxisCoOrdinate(y);
				
				//gui.
				
				//gui.data.gameState.deployVessel(gridi, gridj);
				//System.out.println(gui.data.gameState.playerDeployStateToString();
				
				if(!gui.data.getGameState().isBothPlayerAndAgentShipsDeployed())
				{
					gui.deploy(gridi,gridj);
					
				}
				System.out.println("Element corresponds to " + gridi + gridj);
				//repaint();
			}
		

}

	