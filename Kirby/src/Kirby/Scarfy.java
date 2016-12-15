package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Scarfy extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.SCARFY_RIGHT,
			KirbyGame.SCARFY_LEFT,
			//KirbyGame.TWISTER_ATTACK_R,
			//KirbyGame.TWISTER_ATTACK_L
		};
	
	private Vertex nextPos;
	private Vector movingDir;
	private String direction;
	private boolean firstPath;
	public int jumps;
	public int jumpTime;
	private static final int TIME_JUMP = 20;

	public Scarfy(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		jumps = 0;
		jumpTime = 0;
	}
	
	public void jump(Tile[][] tileMap) {
        jumpTime = TIME_JUMP;
        super.setVelocity(new Vector(super.getVelocity().getX(), -0.4f));
    }
	
	public void applyGravity(float gravity, Tile[][] tileMap){
        if(super.getVelocity().getY() < maximumFallSpeed) {
            setVelocity(new Vector(super.getVelocity().getX(), super.getVelocity().getY() + gravity));
            if (super.getVelocity().getY() > maximumFallSpeed) 
            	setVelocity(new Vector(super.getVelocity().getX(), maximumFallSpeed));
        }
    }
	
	public void setMoving(KirbyGame bg) {

	}	
	
	@Override
	public int getEnemyType() {
		return SCARFY;
	}
}
