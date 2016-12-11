package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class HotHead extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.HOTHEAD_RIGHT,
			KirbyGame.HOTHEAD_LEFT,
			KirbyGame.HOTHEAD_ATTACK_R,
			KirbyGame.HOTHEAD_ATTACK_L
		};
	
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTime;
	Random rand = new Random();

	public HotHead(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		firstPath = true;
		waitTime = rand.nextInt(200);
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
}
