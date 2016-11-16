package Tigress;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Cub extends MovingEntity {
	
	public static final String[] facingImages = 
		{
			TigressGame.CUB_IMG_RSC,
			TigressGame.CUB_IMG_RSC,
			TigressGame.CUB_IMG_RSC,
			TigressGame.CUB_IMG_RSC
		};
	
	private boolean held;
	private boolean inNest;
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTime;
	Random rand = new Random();

	public Cub(final float x, final float y) {
		super(x, y, facingImages, LEFT);
		setVelocity(new Vector(0, 0));
		held = false; 
		inNest = false;
		firstPath = true;
		waitTime = rand.nextInt(200);
	}
	
	/**
	 * Sets whether the cub is being held or not.
	 * @param val: value to set held as
	 */
	public void setHeld(boolean val) {
		if (!val) {
			setFacing(LEFT);
		} else if (getCurImage() != null) {
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(null);
			setFacing(-1);
			setvPos(-1, -1);
			setVelocity(new Vector(0f, 0f));
		}
		held = val;
	}
	
	public boolean isHeld() {
		return held;
	}
	
	/**
	 * Sets whether the cub is in the nest or not.
	 * @param val: true if in nest, false if not
	 */
	public void setNest(boolean val) {
		inNest = val;
	}
	
	public void setMoving(TigressGame bg) {
		if ((hasPassed() || firstPath) && waitTime <= 0 && !isHeld()) {
			if (firstPath)
				firstPath = false;
			//nextPos = path.get(0);
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
			/*System.out.println("currentpos: " + vPos.toString());
			System.out.println("nextpos: " + nextPos.toString());
			System.out.println("direction: " + direction);
			System.out.println("--------------------------------------------");*/
			waitTime = rand.nextInt(200);
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
