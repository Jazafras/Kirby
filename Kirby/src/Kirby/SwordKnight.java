

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

 class SwordKnight extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.SWORDKNIGHT_RIGHT,
			KirbyGame.SWORDKNIGHT_LEFT
		};
	
	public static final String[] attackingImages = 
		{
			KirbyGame.SWORDKNIGHT_ATTACK_R,
			KirbyGame.SWORDKNIGHT_ATTACK_L
			
		};
	
	private boolean inNest;
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTime;
	Random rand = new Random();
	
	private int attackTime;
	private boolean attackState;

	public SwordKnight(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		attackState = false;
	}

	
	public void setMoving(KirbyGame bg) {
	}	
	
	public int retFacing() {
    	return super.facingRight() ? 0 : 1;
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
	
	@Override
	public int getEnemyType() {
		return SWORDKNIGHT;
	}
	
	public void attack(KirbyGame bg) {
//		super.setFacingImages(attackingImages);
//		super.setCurImage(attackingImages[super.getFacing()]);
//		super.setPosition(super.getX(), super.getY());
		
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

