package Battleships;

public class AxisResolver {
	public int resolveAxisCoOrdinate(int x) {	
		if(x>=200) return -1;
	    return x/20;
	}
}
