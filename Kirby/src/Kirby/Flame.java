package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Flame extends Attack {
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

	public Flame(final float x, final float y, int facingDir) {
		super(x, y, facingImages, 0);
		System.out.println("STAR: "+facingDir);
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
