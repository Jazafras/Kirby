package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Boomerang extends Attack {
	public static final String[] facingImages = 
		{
			KirbyGame.BOOMERANG_RSC,
			KirbyGame.BOOMERANG_RSC,
		};

	public Boomerang(final float x, final float y, int facingDir) {
		super(x, y, facingImages, 0);
		if (facingDir % 2 == 0) // right
			setVelocity(new Vector(.15f, 0));
		else
			setVelocity(new Vector(-.15f, 0));
	}
	
	@Override
	public int getAttackType() {
		return BOOMERANG;
	}
}
