package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Attack_SpitFire extends Attack {
	public static final String[] facingImages = 
		{
			KirbyGame.RIGHTFIRE_ATTACK,
			KirbyGame.LEFTFIRE_ATTACK,
			KirbyGame.RIGHTFIRE_ATTACK,
			KirbyGame.LEFTFIRE_ATTACK,
			KirbyGame.RIGHTFIRE_ATTACK,
			KirbyGame.LEFTFIRE_ATTACK
		};

	public Attack_SpitFire(final float x, final float y, int facingDir) {
		super(x, y, facingImages, facingDir);
		super.setVelocity(new Vector(0.f, 0.f));
	}
	
	@Override
	public int getAttackType() {
		return SPITFIRE;
	}
}
