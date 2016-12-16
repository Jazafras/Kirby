package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Bomb extends Attack {
	public static final String[] facingImages = 
		{
			KirbyGame.BOMB_RSC,
			KirbyGame.BOMB_RSC,
		};

	public Bomb(final float x, final float y, int facingDir) {
		super(x, y, facingImages, 0);
		if (facingDir % 2 == 0) // right
			setVelocity(new Vector(.2f, -.4f));
		else
			setVelocity(new Vector(-.2f, -.4f));
	}
	
	@Override
	public int getAttackType() {
		return BOMB;
	}
}
