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
	 
	 // kirby clone ability types
	 public static final int NONE = 0;
	 public static final int FIRE = 1;
	 public static final int KTWISTER = 2; 
	 public static final int KSPARKY = 3;
	 public static final int KSWORD = 4;
	 public static final int KBEAM = 5;
	 public static final int KHAMMER = 6;
	 public static final int KFIGHTER = 7;
	 public static final int KCUTTER = 8;
	 public static final int KBOMB = 9;
	 
	 // kirby states for images
	 public static final int NORMAL = 0;
	 public static final int SUCKING = 1;
	 public static final int FLYING = 2;
	 
	 
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
			KirbyGame.KIRBY_RIGHT_FULL,
			KirbyGame.KIRBY_LEFT_FULL
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
	public boolean hurt;
	
	public int health = 5;

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
		hurt = false;
		jumpTime = 0;
		sucking = false;
		enemySucking = null;
	}
	
	@Override
	public void setVelocity(final Vector v) {
		if (floating) {
			super.justSetVelocity(v);
			if (v.getX() < 0) {
				setFacing(LEFT_FLY);
			} else if (v.getX() > 0) {
				setFacing(RIGHT_FLY);
			}
		} else {
			super.setVelocity(v);
		}
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
		Image i;
		if (enemySucking != null)
			i = new Image(defaultKirbyImages[8 + retFacing()]);
		else
			i = new Image(defaultKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
	}
	
	public void moveLeft(int delta, float speed){
		//facing = Facing.LEFT;
		super.setPosition(super.getX() - (speed*delta), super.getY());
    }
 
    public void moveRight(int delta, float speed){
    	super.setPosition(super.getX() + (speed*delta), super.getY());
    }
    
    public int retFacing() {
    	return super.facingRight() ? 0 : 1;
    }
    
    public void setSuck(boolean b) {
    	actionImage = b ? SUCKING : NORMAL;
    	if (b && super.facingRight())
    		super.setFacing(RIGHT_SUCC);
    	else if (b)
    		super.setFacing(LEFT_SUCC);
		super.setCurImage(defaultKirbyImages[(2*actionImage) + retFacing()]);
    	sucking = b;
    }
    
    public void hitGround() {
    	jumps = 0;
		floating = false;
		maximumFallSpeed = 1.f;
		jumpTime = 0;
		super.setCurImage(defaultKirbyImages[retFacing()]);
    }
    
    public void setFlying() {
    	if (enemySucking == null) {
			floating = true;
			maximumFallSpeed = .09f;	
			if (getVelocity().getY() > maximumFallSpeed) {
				setVelocity(new Vector(getVelocity().getX(), maximumFallSpeed));
			}
			super.setCurImage(defaultKirbyImages[2*FLYING+retFacing()]);
    	}
    }
	
	public void succ(MovingEnemy sucked, KirbyGame bg) {
		if (sucked != null) {
			enemySucking = sucked;
			actionImage = MOUTHFUL;
			bg.enemies.remove(enemySucking);
		}
	}
	
	public void swallow(KirbyGame bg) { 
		if (enemySucking != null) {
			int enemyType = enemySucking.getEnemyType();
			float xPos = super.getX();
			float yPos = super.getY();
			if (enemyType == HOTHEAD) {
				bg.kirby = new FireKirby(xPos, yPos);
			} else if (enemyType == TWISTER) {
				bg.kirby = new TwisterKirby(xPos, yPos);
			} else if (enemyType == SPARKY) {
				bg.kirby = new SparkyKirby(xPos, yPos);
			} else if (enemyType == SWORDKNIGHT) {
				bg.kirby = new SwordKirby(xPos, yPos);
			} else if (enemyType == WADDLEDOO) {
				bg.kirby = new BeamKirby(xPos, yPos);
			} else if (enemyType == BONKERS) {
				bg.kirby = new HammerKirby(xPos, yPos);
			} else if (enemyType == KNUCKLEJOE) {
				bg.kirby = new FighterKirby(xPos, yPos);
			} else if (enemyType == SIRKIBBLE) {
				bg.kirby = new CutterKirby(xPos, yPos);
			} else if (enemyType == POPPYJR) {
				bg.kirby = new BombKirby(xPos, yPos);
			}
			enemySucking = null;
		}
	}
	
	public void spit(KirbyGame bg) {
		if (enemySucking != null) {
			float xPos = 30;
			if (!super.facingRight()) //left
				xPos = -30;
			bg.attacks.add(new Star(bg.kirby.getX() + xPos, bg.kirby.getY(), super.getFacing()));
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
	
	public int getType() {
		return NONE;
	}
	
	//Sleepy Kirby is a special case with only one image
	//Kirby loses if he switches to this state
	public static final String[] noddyKirbyImages = 
		{
			KirbyGame.KIRBY_SLEEP
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

}
