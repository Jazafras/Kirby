package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Hurt extends Entity {

	public Hurt(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(KirbyGame.HURT_RSC));
	}
	
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(KirbyGame.HURT_RSC);
		i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
	}
}
