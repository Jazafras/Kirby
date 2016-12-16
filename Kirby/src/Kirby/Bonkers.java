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

 class Bonkers extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.BONKERS_RIGHT,
			KirbyGame.BONKERS_LEFT
			
		};
	
	public static final String[] attackingImages = 
		{
			KirbyGame.BONKERS_ATTACK_R,
			KirbyGame.BONKERS_ATTACK_L
			
		};
	
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTime;
	private int attackTime;
	private boolean attackState;
	Random rand = new Random();

	public Bonkers(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		firstPath = true;
		waitTime = rand.nextInt(200);
		attackState = false;
	}

	
	public void setMoving(KirbyGame bg) {
		if ((hasPassed() || firstPath) && waitTime <= 0) {
			if (firstPath)
				firstPath = false;
			Vertex v = bg.vPos.get(vPos.toString());
			int r = rand.nextInt(v.neighbors.size());
			int c = 0;
			for (Vertex n : v.getNeighbors()) {
				if (c == r) nextPos = n;
				c++;
			}
			if (nextPos.getX() > vPos.getX()) {
				setVelocity(new Vector(.07f, 0f));
				direction = "right";
			} else if (nextPos.getX() < vPos.getX()) {
				setVelocity(new Vector(-.07f, 0f));
				direction = "left";
			} else if (nextPos.getY() > vPos.getY()) {
				setVelocity(new Vector(0f, .07f));
				direction = "below";
			} else {
				setVelocity(new Vector(0f, -.07f));
				direction = "above";
			}
			waitTime = rand.nextInt(1);
		}
		if (hasPassed()) {
			setVelocity(new Vector(0f, 0f));
			vPos = nextPos;
		}
		if (waitTime > 0)
			waitTime--;
	}	
	
	private boolean hasPassed() {
		if (direction != null && nextPos != null) {
			if (direction.equals("left"))
				return getPosition().getX() <= nextPos.getX();
			else if (direction.equals("right"))
				return getPosition().getX() >= nextPos.getX();
			else if (direction.equals("above"))
				return getPosition().getY() <= nextPos.getY();
			else
				return getPosition().getY() >= nextPos.getY();
		}
		return false;
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
	
	@Override
	public int getEnemyType() {
		return BONKERS;
	}
}
