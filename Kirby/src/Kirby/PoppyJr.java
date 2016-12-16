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

 class PoppyJr extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.POPPY_RIGHT,
			KirbyGame.POPPY_LEFT
		};
	
	public static final String[] attackingImages = 
		{
			//KirbyGame.POPPY_ATTACK_R,
			//KirbyGame.POPPY_ATTACK_L
				KirbyGame.POPPY_RIGHT,
				KirbyGame.POPPY_LEFT
		};
	
	public static final int BOMB_ATTACK = 6;
	public static final int BOMB_TIME = 40;
	public int bombTime;
	private boolean attackState;
	public Bomb b;

	public PoppyJr(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		bombTime = 0;
		b = null;
		attackState = false;
	}

	
	public void setMoving(KirbyGame bg) {
	}	
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (attackState){
			i = new Image(attackingImages[super.getFacing()]);
			i.draw(super.getX() - 40 - offsetX, super.getY() + 4 - offsetY);
		}
		else{
			i = new Image(facingImages[super.getFacing()]);
			i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
		}
	}
	
	public void attack(KirbyGame bg) {
		if (bombTime <= 0) {
			attackState = true;
			bombTime = BOMB_TIME;
			float xPos = 30;
			if (!super.facingRight()) //left
				xPos = -30;
			b = new Bomb(getX() + xPos, getY(), super.getFacing());
			bg.enemyAttacks.add(b);
		}
		
	}

	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (bombTime > 0) {
			bombTime--;
		} else {
			attackState = false;
		}
	}
	
	
	@Override
	public int getEnemyType() {
		return POPPYJR;
	}
}
