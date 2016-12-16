package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Explosion extends Attack {
	 
	 public int bombtime;
	 
	public static final String[] facingImages = 
		{
			KirbyGame.EXPLOSION_RSC,
			KirbyGame.EXPLOSION_RSC,
		};

	public Explosion(final float x, final float y, int facingDir) {
		super(x, y, facingImages, 0);
		setVelocity(new Vector(0.f, 0.f));
		bombtime = 10;
	}
	
	@Override
	public int getAttackType() {
		return EXPLOSION;
	}
}
