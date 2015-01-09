package TestClasses;

import org.junit.Test;
import org.junit.Assert;

import Battleships.InfluenceMap;

public class BattleShipInfluenceMapTester {
	@Test
	public void testcase1() {
		InfluenceMap influenceMap = new InfluenceMap();
		// set
		influenceMap.set(8, 3, 13);
		influenceMap.set(5, 5, 9);

		// getVal
		Assert.assertEquals(13, influenceMap.getValue(8, 3));
		Assert.assertEquals(0, influenceMap.getValue(6, 5));

		// setTurns
		influenceMap.setTurns(9999);

		// hit
		influenceMap.hit(4, 3);
		influenceMap.hit(5, 0);
		influenceMap.hit(4, 0);

		// miss
		influenceMap.miss(4, 2);
		influenceMap.miss(3, 3);
		influenceMap.miss(5, 3);

		// sunk
		influenceMap.sunk(4, 3);
		influenceMap.sunk(4, 0);

		Assert.assertEquals(4, influenceMap.getValue(3, 0));

		// getters
		Assert.assertEquals(13, influenceMap.getHotspotValue());
		Assert.assertEquals(1, influenceMap.getNumberOfHotspots());
		Assert.assertEquals("83", influenceMap.getHotspots());
		int[] expectedHotspots = { 8, 3 };
		int[] actualHotspots = influenceMap.getIntHotspots();
		for (int i = 0; i < actualHotspots.length; i++) {
			Assert.assertEquals(expectedHotspots[i], actualHotspots[i]);
		}
		Assert.assertEquals(9999, influenceMap.getTurns());
		Assert.assertEquals(8, influenceMap.getHotspotI());
		Assert.assertEquals(3, influenceMap.getHotspotJ());

		// mapFittings
		influenceMap.searchDeadends();

		// Test
		Assert.assertEquals(
				"|0000000000|\n|0000000000|\n|0000000000|\n|400-5000000|\n|90-59000000|\n|940-5090000|\n|2000000000|\n|0000000000|\n|00013000000|\n|0000000000|\n",
				influenceMap.toString());
	}

	@Test
	public void testcase2() {
		// MapFittings
		InfluenceMap influenceMap2 = new InfluenceMap();
		influenceMap2.miss(3, 5);
		influenceMap2.miss(5, 7);
		influenceMap2.hit(4, 4);
		influenceMap2.miss(4, 3);
		influenceMap2.hit(4, 5);
		influenceMap2.hit(4, 6);
		influenceMap2.sunk(4, 4);

		InfluenceMap influenceMap = new InfluenceMap();

		// mapFittings
		influenceMap.addMap(influenceMap2);

		// set
		try {
			influenceMap.set(10, 9, 13);
			influenceMap.set(5, 5, 9);
		} catch (Exception e) {
			// do nothing
		}

		// getVal
		Assert.assertEquals(0, influenceMap.getValue(7, 3));
		Assert.assertEquals(2, influenceMap.getValue(5, 5));

		// setTurns
		influenceMap.setTurns(-10);

		// hit
		influenceMap.hit(9, 3);
		influenceMap.hit(9, 3);

		// miss
		influenceMap.miss(4, 2);
		influenceMap.miss(3, 3);
		influenceMap.miss(5, 3);

		// sunk
		influenceMap.sunk(9, 3);

		// getters
		Assert.assertEquals(5, influenceMap.getHotspotValue());
		Assert.assertEquals(4, influenceMap.getNumberOfHotspots());
		Assert.assertEquals("44454647", influenceMap.getHotspots());
		int[] expectedHotspots = { 4, 4, 4, 5, 4, 6, 4, 7 };
		int[] actualHotspots = influenceMap.getIntHotspots();
		for (int i = 0; i < actualHotspots.length; i++) {
			Assert.assertEquals(expectedHotspots[i], actualHotspots[i]);
		}
		Assert.assertEquals(-10, influenceMap.getTurns());
		Assert.assertEquals(4, influenceMap.getHotspotI());
		Assert.assertEquals(4, influenceMap.getHotspotJ());

		// Test
		Assert.assertEquals(
				"|0000000000|\n|0000000000|\n|0000000000|\n|000-50-32000|\n|00-5-5555500|\n|000-5022-500|\n|0000000000|\n|0000000000|\n|0002000000|\n|0049400000|\n",
				influenceMap.toString());
	}

	@Test
	public void testcase3() {
		InfluenceMap influenceMap = new InfluenceMap();
		// set
		influenceMap.set(8, 3, 13);
		influenceMap.set(5, 5, 9);

		// getVal

		try {
			Assert.assertEquals(13, influenceMap.getValue(8, 3));
			Assert.assertEquals(0, influenceMap.getValue(10, 5));
		} catch (Exception ex) {
			// do nothing
		}

		// setTurns
		influenceMap.setTurns(9999);

		// mapFittings
		influenceMap.clearAll();

		// hit
		influenceMap.hit(4, 3);
		influenceMap.hit(7, 9);

		// miss
		influenceMap.miss(4, 2);
		influenceMap.miss(3, 3);
		influenceMap.miss(5, 3);

		// sunk
		influenceMap.sunk(7, 9);

		// getters
		Assert.assertEquals(4, influenceMap.getHotspotValue());
		Assert.assertEquals(1, influenceMap.getNumberOfHotspots());
		Assert.assertEquals("44", influenceMap.getHotspots());
		int[] expectedHotspots = { 4, 4 };
		int[] actualHotspots = influenceMap.getIntHotspots();
		for (int i = 0; i < actualHotspots.length; i++) {
			Assert.assertEquals(expectedHotspots[i], actualHotspots[i]);
		}
		Assert.assertEquals(9999, influenceMap.getTurns());
		Assert.assertEquals(4, influenceMap.getHotspotI());
		Assert.assertEquals(4, influenceMap.getHotspotJ());

		// Test
		Assert.assertEquals(
				"|0000000000|\n|0000000000|\n|0000000000|\n|000-5000000|\n|00-59400000|\n|000-5000000|\n|0000000000|\n|0000000009|\n|0000000000|\n|0000000000|\n",
				influenceMap.toString());
	}

	@Test
	public void testcase4() {
		InfluenceMap influenceMap = new InfluenceMap();

		influenceMap.set(1, 0, -9);
		influenceMap.set(0, 1, -9);

		influenceMap.set(3, 0, -9);
		influenceMap.set(2, 1, -9);

		influenceMap.set(0, 3, -9);
		influenceMap.set(1, 2, -9);

		influenceMap.set(0, 8, -9);
		influenceMap.set(1, 9, -9);

		influenceMap.set(3, 9, -9);
		influenceMap.set(2, 8, -9);

		influenceMap.set(8, 0, -9);
		influenceMap.set(9, 1, -9);

		influenceMap.set(9, 8, -9);
		influenceMap.set(8, 9, -9);

		influenceMap.set(9, 3, -9);
		influenceMap.set(8, 2, -9);

		influenceMap.searchDeadends();

		Assert.assertEquals(
				"|-7-9-7-90000-9-7|\n|-90-9000000-9|\n|-7-9000000-9-7|\n|-900000000-9|\n|0000000000|\n|0000000000|\n|0000000000|\n|0000000000|\n|-90-9000000-9|\n|-7-9-7-90000-9-7|\n",
				influenceMap.toString());
	}
}
