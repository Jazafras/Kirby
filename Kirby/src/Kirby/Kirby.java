package Kirby;

import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Kirby extends MovingEntity {
	
	/* order of image arrays should go:
	 * right walk
	 * left walk
	 * right absorb
	 * left absorb
	 * right flying
	 * left flying
	 * right attack
	 * left attack
	 */
	 
	 public static final int NORMAL = 0;
	 public static final int FLYING = 1;
	 
	 public static final int SUCKING = 2;
	 public static final int MOUTHFUL = 3;
	 public static final int SPITTING = 4;
	 public static final int SWALLOWING = 5;
	 
	public static final String[] defaultKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC,
			KirbyGame.KIRBY_RIGHT_SUCC,
			KirbyGame.KIRBY_LEFT_SUCC,
			KirbyGame.KIRBY_RIGHT_FLY,
			KirbyGame.KIRBY_LEFT_FLY,
			KirbyGame.KIRBY_RIGHT_SUCC,
			KirbyGame.KIRBY_LEFT_SUCC,
		};
	
	public static final String[] boomerangKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTBOOMERANG,
			KirbyGame.KIRBY_LEFTBOOMERANG,
			KirbyGame.KIRBY_RIGHTBOOMERANG_SUCC,
			KirbyGame.KIRBY_LEFTBOOMERANG_SUCC,
			KirbyGame.KIRBY_RIGHTBOOMERANG_FLY,
			KirbyGame.KIRBY_LEFTBOOMERANG_FLY,
			KirbyGame.KIRBY_RIGHTBOOMERANG_ATTACK,
			KirbyGame.KIRBY_LEFTBOOMERANG_ATTACK,
		};
	
	public static final String[] fighterKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTFIGHT,
			KirbyGame.KIRBY_LEFTFIGHT,
			KirbyGame.KIRBY_RIGHTFIGHT_SUCC,
			KirbyGame.KIRBY_LEFTFIGHT_SUCC,
			KirbyGame.KIRBY_RIGHTFIGHT_FLY,
			KirbyGame.KIRBY_LEFTFIGHT_FLY,
			KirbyGame.KIRBY_RIGHTFIGHT_ATTACK,
			KirbyGame.KIRBY_LEFTFIGHT_ATTACK,
		};
	
	public static final String[] fireKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTFIRE,
			KirbyGame.KIRBY_LEFTFIRE,
			KirbyGame.KIRBY_RIGHTFIRE_SUCC,
			KirbyGame.KIRBY_LEFTFIRE_SUCC,
			KirbyGame.KIRBY_RIGHTFIRE_FLY,
			KirbyGame.KIRBY_LEFTFIRE_FLY,
			KirbyGame.KIRBY_RIGHTFIRE_ATTACK,
			KirbyGame.KIRBY_LEFTFIRE_ATTACK,
		};
	
	public static final String[] hammerKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTHAMMER,
			KirbyGame.KIRBY_LEFTHAMMER,
			KirbyGame.KIRBY_RIGHTHAMMER_SUCC,
			KirbyGame.KIRBY_LEFTHAMMER_SUCC,
			KirbyGame.KIRBY_RIGHTHAMMER_FLY,
			KirbyGame.KIRBY_LEFTHAMMER_FLY,
			KirbyGame.KIRBY_RIGHTHAMMER_ATTACK,
			KirbyGame.KIRBY_LEFTHAMMER_ATTACK,
		};
	
	//Sleepy Kirby is a special case with only one image
	//Kirby loses if he switches to this state
	public static final String[] noddyKirbyImages = 
		{
			KirbyGame.KIRBY_SLEEP
		};
	
	public static final String[] poppyKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTPOPPY,
			KirbyGame.KIRBY_LEFTPOPPY,
			KirbyGame.KIRBY_RIGHTPOPPY_SUCC,
			KirbyGame.KIRBY_LEFTPOPPY_SUCC,
			KirbyGame.KIRBY_RIGHTPOPPY_FLY,
			KirbyGame.KIRBY_LEFTPOPPY_FLY,
			KirbyGame.KIRBY_RIGHTPOPPY_ATTACK,
			KirbyGame.KIRBY_LEFTPOPPY_ATTACK,
		};
	
	public static final String[] sparkyKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTSPARKY,
			KirbyGame.KIRBY_LEFTSPARKY,
			KirbyGame.KIRBY_RIGHTSPARKY_SUCC,
			KirbyGame.KIRBY_LEFTSPARKY_SUCC,
			KirbyGame.KIRBY_RIGHTSPARKY_FLY,
			KirbyGame.KIRBY_LEFTSPARKY_FLY,
			KirbyGame.KIRBY_RIGHTSPARKY_ATTACK,
			KirbyGame.KIRBY_LEFTSPARKY_ATTACK,
		};
	
	public static final String[] swordKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTSWORD,
			KirbyGame.KIRBY_LEFTSWORD,
			KirbyGame.KIRBY_RIGHTSWORD_SUCC,
			KirbyGame.KIRBY_LEFTSWORD_SUCC,
			KirbyGame.KIRBY_RIGHTSWORD_FLY,
			KirbyGame.KIRBY_LEFTSWORD_FLY,
			KirbyGame.KIRBY_RIGHTSWORD_ATTACK,
			KirbyGame.KIRBY_LEFTSWORD_ATTACK,
		};
	
	public static final String[] twisterKirbyImages = 
		{
			KirbyGame.KIRBYTWIST_RIGHT,
			KirbyGame.KIRBYTWIST_LEFT,
			KirbyGame.KIRBYTWIST_RIGHT_SUCC,
			KirbyGame.KIRBYTWIST_LEFT_SUCC,
			KirbyGame.KIRBYTWIST_RIGHT_FLY,
			KirbyGame.KIRBYTWIST_LEFT_FLY,
			KirbyGame.KIRBYTWIST_ATTACK,
			KirbyGame.KIRBYTWIST_ATTACK,
		};
	
	//UFO is a special case with different controls
	//Kirby cannot absorb other enemies in this state, only attack
	//if Kirby wants to switch out of UFO state, the player must drop the state first
	public static final String[] ufoKirbyImages = 
		{
			KirbyGame.KIRBYUFO_RIGHT,
			KirbyGame.KIRBYUFO_LEFT,
			//KirbyGame.KIRBY_RIGHTSWORD_SUCC,
			//KirbyGame.KIRBY_LEFTSWORD_SUCC,
			//KirbyGame.KIRBY_RIGHTSWORD_FLY,
			//KirbyGame.KIRBY_LEFTSWORD_FLY,
			KirbyGame.KIRBY_RIGHTUFO_ATTACK,
			KirbyGame.KIRBY_LEFTUFO_ATTACK,
		};
	
	public static final String[] waddleKirbyImages = 
		{
			KirbyGame.KIRBYWADDLE_RIGHT,
			KirbyGame.KIRBYWADDLE_LEFT,
			KirbyGame.KIRBYWADDLE_RIGHT_SUCC,
			KirbyGame.KIRBYWADDLE_LEFT_SUCC,
			KirbyGame.KIRBYWADDLE_RIGHT_FLY,
			KirbyGame.KIRBYWADDLE_LEFT_FLY,
			KirbyGame.KIRBYWADDLE_RIGHT_ATTACK,
			KirbyGame.KIRBYWADDLE_LEFT_ATTACK,
		};
	
	private static final int MAX_JUMP = 5;
	private static final int TIME_JUMP = 20;
	
	public boolean floating;
	public boolean sucking;
	public MovingEnemy enemySucking;
	private boolean startState;
	private boolean swordState;
	private boolean state2;
	private boolean state3;
	public int jumps;
	public int jumpTime;
	public float maximumFallSpeed = 1;
	public int actionImage;

	public Kirby(final float x, final float y) {
		super(x, y, defaultKirbyImages, RIGHT_WALK);
		setVelocity(new Vector(0, 0));
		actionImage = NORMAL;
		startState = true;
		swordState = false;
		state2 = false;
		state3 = false;
		floating = false;
		jumps = 0;
		jumpTime = 0;
		sucking = false;
		enemySucking = null;
	}
	
	public void setVertex(KirbyGame bg) {
		int xRemain = (int)getX() % 50;
		int yRemain = (int)getY() % 50;
		int xDiff = xRemain > 25 ? 50 - xRemain : -1 * xRemain;
		int yDiff = yRemain > 25 ? 50 - yRemain : -1 * yRemain;
		Vertex v = new Vertex((int)getX() + xDiff, (int)getY() + yDiff);
		if (bg.vPos.containsKey(v.toString())){
			setvPos((int)getX() + xDiff, (int)getY() + yDiff);
		}
	}
	
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(defaultKirbyImages[RIGHT_WALK]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void moveLeft(int delta, float speed){
		//facing = Facing.LEFT;
		super.setPosition(super.getX() - (speed*delta), super.getY());
    }
 
    public void moveRight(int delta, float speed){
    	super.setPosition(super.getX() + (speed*delta), super.getY());
    }
    
    public void setSuck(boolean b) {
    	actionImage = b ? SUCKING : NORMAL;
    	sucking = b;
    }
	
	public void succ(MovingEnemy sucked, KirbyGame bg) {
		if (sucked != null) {
			enemySucking = sucked;
			actionImage = MOUTHFUL;
			bg.enemies.remove(enemySucking);
		}
	}
	
	public void swallow() { 
		if (enemySucking != null) {
			// PUT KIRBY STATE CHANGE SHIT HERE
		}
	}
	
	public void spit(KirbyGame bg) {
		if (enemySucking != null) {
			float newX = (super.getFacing() % 2 == 0) ? 50.f : -50.f;
			System.out.println(super.getY());
			enemySucking.setPosition(super.getX() + newX, super.getY());
			bg.enemies.add(enemySucking);
			enemySucking = null;
		}
	}
	
	public void jump(Tile[][] tileMap) {
        if (jumps < MAX_JUMP && jumpTime <= 0) {
        	jumpTime = TIME_JUMP;
        	jumps++;
        	super.setVelocity(new Vector(super.getVelocity().getX(), -0.4f));
        }
    }
	 
	public String toString() {
		return "Kirby ~ x: " + super.getX() + ", y: " + super.getY();
	}
	
	public void applyGravity(float gravity, Tile[][] tileMap){
        if(super.getVelocity().getY() < maximumFallSpeed) {
            setVelocity(new Vector(super.getVelocity().getX(), super.getVelocity().getY() + gravity));
            if (super.getVelocity().getY() > maximumFallSpeed) 
            	setVelocity(new Vector(super.getVelocity().getX(), maximumFallSpeed));
        }
    }

}
