package TestClasses;

import org.junit.Assert;
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

		agent.nextShot(map, attackGrid);

		Assert.assertEquals(true,
				map.getValue(agent.getI(), agent.getJ()) == map
						.getHotspotValue());

		agent.setSunk();

		Assert.assertEquals(true,
				map.getValue(agent.getI(), agent.getJ()) == map
						.getHotspotValue());

		Assert.assertEquals(
				"|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
				agent.getMap().toString());
	}

	@Test
	public void testcase2() {
		Agent agent = new Agent();
		Grid attackGrid = agent.placeShips();
		agent.placeShips();

		InfluenceMap map = new InfluenceMap();
		map.miss(3, 3);
		map.miss(5, 3);
		map.hit(4, 5);
		map.hit(4, 4);
		map.hit(4, 3);
		map.miss(4, 2);

		int i = map.getHotspotI();
		int j = map.getHotspotJ();

		agent.nextShot(map, attackGrid);

		if (attackGrid.getGridVal(i, j) == 0) {
			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|000-3220000|\n|00-599913000|\n|000-3220000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());

			Assert.assertEquals(true, agent.getI() == i);
			Assert.assertEquals(true, agent.getJ() == j);

			agent.setSunk();

			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|000-3000000|\n|00-59994000|\n|000-3000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());
		}

		if (attackGrid.getGridVal(i, j) != 0) {
			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|000-3220000|\n|00-59990000|\n|000-3220000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());

			agent.setSunk();

			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|000-3000000|\n|00-5999-4000|\n|000-3000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());
		}
	}

	@Test
	public void testcase3() {
		Agent agent = new Agent();
		Grid attackGrid = agent.placeShips();
		agent.placeShips();

		InfluenceMap map = new InfluenceMap();
		map.hit(4, 6);
		map.hit(4, 3);

		int i = map.getHotspotI();
		int j = map.getHotspotJ();

		agent.nextShot(map, attackGrid);

		if (attackGrid.getGridVal(i, j) == 0) {
			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|0002002000|\n|0049449400|\n|0002002000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());

			agent.setSunk();

			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0009009000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());
		}

		if (attackGrid.getGridVal(i, j) != 0) {
			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|0002002000|\n|0049449400|\n|0002002000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());

			agent.setSunk();

			Assert.assertEquals(
					"|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0009009000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n",
					agent.getMap().toString());
		}
	}
}
