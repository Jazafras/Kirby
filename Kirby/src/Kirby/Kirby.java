package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Kirby extends MovingEntity {
	
	public static final String[] facingImages = 
		{
			KirbyGame.KIRBY_RIGHTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC
		};
	
	private static final int maxJump = 3;
	
	public boolean floating;
	private boolean startState;
	private boolean swordState;
	private boolean state2;
	private boolean state3;
	public int jumps;
	public float maximumFallSpeed = 1;

	public Kirby(final float x, final float y) {
		super(x, y, facingImages, RIGHT);
		setVelocity(new Vector(0, 0));
		startState = true;
		swordState = false;
		state2 = false;
		state3 = false;
		floating = false;
		jumps = 0;
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
		//super.render(g);
		Image i = new Image(facingImages[RIGHT]);
		i.draw(super.getX() - 2 - offsetX, super.getY() - 2 - offsetY);
		//sprites.get(facing).draw(x-2-offset_x, y-2-offset_y);
	}
	
	public void moveLeft(int delta, float speed){
		super.setPosition(super.getX() - (speed*delta), super.getY());
    }
 
    public void moveRight(int delta, float speed){
    	super.setPosition(super.getX() + (speed*delta), super.getY());
    }
	
	public void succ(){
		//return succ;
		
	}
	
	public void swallow(){
		//return swallow;
		//if entity = sword
		//switch to sword state
	}
	
	public void spit(){
		//return spit;
	}
	
	public void jump(Tile[][] tileMap) {
        if (jumps < maxJump) {
        	if (jumps == 1) {
    			floating = true;
    			maximumFallSpeed = .5f;
    		}
        	jumps++;
        	super.setVelocity(new Vector(super.getVelocity().getX(), -0.4f));
        }
    }
	 
	public String toString() {
		return "Kirby ~ x: " + super.getX() + ", y: " + super.getY();
	}
	
	public void applyGravity(float gravity, Tile[][] tileMap){
        if(super.getVelocity().getY() < maximumFallSpeed){
            setVelocity(new Vector(super.getVelocity().getX(), super.getVelocity().getY() + gravity));
            if(super.getVelocity().getY() > maximumFallSpeed){
            	setVelocity(new Vector(super.getVelocity().getX(), maximumFallSpeed));
            }
        }
    }

}
