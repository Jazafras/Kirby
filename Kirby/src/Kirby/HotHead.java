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

 class HotHead extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.HOTHEAD_RIGHT,
			KirbyGame.HOTHEAD_LEFT
		};
	public static final String[] attackingImages = 
		{
			KirbyGame.HOTHEAD_ATTACK_R,
			KirbyGame.HOTHEAD_ATTACK_L
		};
	
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTime;
	private int attackTime;
	private boolean attackState;
	Random rand = new Random();

	public HotHead(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		firstPath = true;
		waitTime = rand.nextInt(200);
	}

	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (attackState){
			i = new Image(attackingImages[super.getFacing()]);
			i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
		}
		else{
			i = new Image(facingImages[super.getFacing()]);
			i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
		}
	}
	
	public void attack(KirbyGame bg) {

		attackState = true;
		attackTime = 20;
		
	}
	
	public void spitFire(KirbyGame bg) {
		bg.enemyAttacks.clear();
		float xPos = 30;
		if (!super.facingRight()) //left
			xPos = -50;
		for(HotHead h: bg.hothead){
			bg.enemyAttacks.add(new Attack_SpitFire(h.getX() + xPos, h.getY() + 10, super.getFacing()));
		}
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
	@Override
	public int getEnemyType() {
		return HOTHEAD;
	}
}
