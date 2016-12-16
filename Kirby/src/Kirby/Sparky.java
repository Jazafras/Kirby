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

 class Sparky extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.SPARKY_RIGHT,
			KirbyGame.SPARKY_LEFT,
			KirbyGame.SPARKY_ATTACK_R,
			KirbyGame.SPARKY_ATTACK_L
		};
	public static final int SPARK_ATTACK = 2;
	private boolean sparkState;
	private int attackTime;

	public Sparky(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		sparkState = false;
	}

	
	public void setMoving(KirbyGame bg) {
	}	
	
	public int retFacing() {
    	return super.facingRight() ? 0 : 1;
    }
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (getCurImage() != null) 
			removeImage(ResourceManager.getImage(getCurImage()));
		if (sparkState) {
			setCurImage(facingImages[SPARK_ATTACK + retFacing()]);
			i = new Image(facingImages[SPARK_ATTACK + retFacing()]);
		} else {
			setCurImage(facingImages[super.getFacing()]);
			i = new Image(facingImages[super.getFacing()]);
		}
		addImageWithBoundingBox(ResourceManager
				.getImage(getCurImage()));
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		sparkState = true;
		attackTime = 15;
	}
	
	@Override
	public void update(final int delta) {
		if (attackTime > 0) {
			attackTime--;
		} else {
			sparkState = false;
		}
	}
	
	@Override
	public int getEnemyType() {
		return SPARKY;
	}
}
