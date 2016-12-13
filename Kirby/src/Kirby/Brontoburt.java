package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

/* order of image arrays should go:
 * left walk
 * right walk
 * left attack
 * right attack
 */

 class Brontoburt extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.BRONTOBURT_LEFT,
			KirbyGame.BRONTOBURT_RIGHT,
			//KirbyGame.BRONTOBURT_ATTACK_R,
			//KirbyGame.BRONTOBURT_ATTACK_L
		};
	
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTimeUp = -1, waitTimeDown = -1;
	Random rand = new Random();

	public Brontoburt(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		firstPath = true;
		//waitTime = rand.nextInt(200);
	}

	
	public void setMoving(KirbyGame bg) {
		//brontoburt movement updates
			if (getVelocity().getY() == 0 && getVelocity().getX() == 0){
				setVelocity(new Vector(-.07f, -.02f)); //move up and left
				waitTimeUp = 200;
				//System.out.println("brontoburt up time: " + waitTimeUp);
			}
			//System.out.println("brontoburt position: " + burt.getPosition().getX());
			if (getPosition().getX() < 30){ //brontoburt reached left end of screen
				setVelocity(new Vector(.07f, -.02f)); //move up and right
				waitTimeUp = 200;
			}
			if (getPosition().getX() > 2350){ //brontoburt reached right end of screen
				setVelocity(new Vector(-.07f, .02f)); //move down and left
				waitTimeUp = 200;
			}
			if (getVelocity().getX() < 0){ //brontoburt is moving left
				if (waitTimeUp == 0){
					waitTimeUp = -1;
					setVelocity(new Vector(-.07f, .02f)); //move down
					waitTimeDown = 200;
					//System.out.println("brontoburt down time: " + waitTimeDown);
				}
				else if (waitTimeDown == 0){
					waitTimeDown = -1;
					setVelocity(new Vector(-.07f, -.02f)); //move up
					waitTimeUp = 200;
				}
			}
			else if (getVelocity().getX() > 0) { //brontoburt is moving right
				if (waitTimeUp == 0){
					waitTimeUp = -1;
					setVelocity(new Vector(.07f, .02f)); //move down
					waitTimeDown = 200;
					//System.out.println("brontoburt down time: " + waitTimeDown);
				}
				else if (waitTimeDown == 0){
					waitTimeDown = -1;
					setVelocity(new Vector(.07f, -.02f)); //move up
					waitTimeUp = 200;
				}
			}
	}	
	
	@Override
	public int getEnemyType() {
		return BRONTOBURT;
	}
}
