package Kirby;

import jig.Entity;

public class Tile extends Entity {
	
	public static final int GROUND = 0;
	public static final int AIR = 1;
	
	int type;
	
	public Tile(int x, int y, int type) {
		super(x, y);
		this.type = type;
	}
	
}