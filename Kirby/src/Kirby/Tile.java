package Kirby;

import org.newdawn.slick.Graphics;

import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {
	
	public static final int GROUND = 0;
	public static final int AIR = 1;
	
	int type;
	
	public Tile(int x, int y, int type) {
		super(x, y);
		this.type = type;
		addImageWithBoundingBox(ResourceManager
				.getImage(KirbyGame.TILE_IMG_RSC));
	}
	
	public int getType() {
		return type;
	}
	
	public void render(final Graphics g, float offsetX, float offsetY) {
		super.setPosition(super.getX() /*- 2*/ - offsetX, super.getY() /*- 2*/ - offsetY);   
		super.render(g);
		super.setPosition(super.getX() /*+ 2*/ + offsetX, super.getY() /*+ 2*/ + offsetY); 
	}
	
	public String toString() {
		String s = "{x: " + super.getX() + ", y: " + super.getY() + ", type: ";
		if (type == GROUND)
			s += "ground}";
		else
			s += "air}";
		return s;
	}
	
}