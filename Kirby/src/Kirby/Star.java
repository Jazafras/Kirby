package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Star extends Attack {
	public static final String[] facingImages = 
		{
			KirbyGame.STAR_IMG_RSC,
			KirbyGame.STAR_IMG_RSC,
			KirbyGame.STAR_IMG_RSC,
			KirbyGame.STAR_IMG_RSC,
			KirbyGame.STAR_IMG_RSC,
			KirbyGame.STAR_IMG_RSC,
			KirbyGame.STAR_IMG_RSC,
		};

	public Star(final float x, final float y, int facingDir) {
		super(x, y, facingImages, 0);
		if (facingDir % 2 == 0) // right
			setVelocity(new Vector(.5f, 0));
		else
			setVelocity(new Vector(-.5f, 0));
	}
	
	@Override
	public int getAttackType() {
		return STAR;
	}
}
