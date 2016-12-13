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

/* order of image arrays should go:
 * left walk
 * right walk
 * left attack
 * right attack
 */

 class WaddleDee extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.WADDLEDEE_LEFT,
			KirbyGame.WADDLEDEE_RIGHT
			//KirbyGame.WADDLEDEE_ATTACK_R,
			//KirbyGame.WADDLEDEE_ATTACK_L
		};
	
	private boolean sucked;
	
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	private int waitTime;
	Random rand = new Random();
	Tile[][] tileMap;

	public WaddleDee(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		sucked = false;
		firstPath = true;
		waitTime = rand.nextInt(1);
	}
	

	public void setMoving(KirbyGame bg) {
			if (getVelocity().getY() == 0 && getVelocity().getX() == 0){
				setVelocity(new Vector(-.07f, 0f)); //move left
			}
			if (sideCollision(tileMap)) {
				System.out.println("waddledee wall collision");
				/*if (getVelocity().getX() < 0) {
					System.out.println("left waddledee collision");
					//wdee.translate(new Vector(.2f, wdee.getVelocity().getY()).scale(delta));
					//wdee.translate(new Vector(0f, 0f));
					setVelocity(new Vector(.07f, 0f)); //move right
				}
				else if (getVelocity().getX() > 0){
					System.out.println("right waddledee collision");
					//wdee.translate(new Vector(-.2f, wdee.getVelocity().getY()).scale(delta));
					setVelocity(new Vector(-.07f, 0f)); //move left
				}*/
			}
	}	

	@Override
	public int getEnemyType() {
		return WADDLEDEE;
	}
}
