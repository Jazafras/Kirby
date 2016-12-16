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

 class WaddleDoo extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.WADDLEDOO_RIGHT,
			KirbyGame.WADDLEDOO_LEFT

		};
	
	public static final String[] attackingImages = 
		{
			KirbyGame.WADDLEDOO_ATTACK_R,
			KirbyGame.WADDLEDOO_ATTACK_L
			
		};
	

	private int attackTime;
	private boolean attackState;
	Random rand = new Random();

	public WaddleDoo(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		attackState = false;
	}

	
	public void setMoving(KirbyGame bg) {

	}	
	
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (attackState){
			i = new Image(attackingImages[super.getFacing()]);
			setVelocity(new Vector(0f, 0f));
			i.draw(super.getX() - 40 - offsetX, super.getY() + 4 - offsetY);
		}
		else{
			i = new Image(facingImages[super.getFacing()]);
			i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
		}
	}
	
	@Override
	public int getEnemyType() {
		return WADDLEDOO;
	}


	public void attack(KirbyGame bg) {
		attackState = true;
		attackTime = 20;
		
	}

	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (attackTime > 0) {
			attackTime--;
		} else {
			attackState = false;
		}
	}
	
}
