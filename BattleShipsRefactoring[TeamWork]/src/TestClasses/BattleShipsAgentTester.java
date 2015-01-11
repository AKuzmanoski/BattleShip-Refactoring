package TestClasses;

import org.junit.Test;

import Battleships.Agent;
import Battleships.Grid;
import Battleships.InfluenceMap;

public class BattleShipsAgentTester {
	@Test
	public void testcase1() {
		Agent agent = new Agent();
		Grid attackGrid = agent.placeShips();
		agent.placeShips();
		
		InfluenceMap map = new InfluenceMap();
		System.out.println(map.toString());
		
		agent.nextShot(map, attackGrid);
		
		System.out.println(agent.getI() + ", " + agent.getJ());
	}
}
