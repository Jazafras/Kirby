package Kirby;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class MovingEnemy extends MovingEntity {
	 
	// facing directions 
	public static final int RIGHT_WALK = 0;
	public static final int LEFT_WALK = 1;
	public static final int RIGHT_ATTACK = 2;
	public static final int LEFT_ATTACK = 3;

	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int RIGHTA = 2;
	public static final int LEFTA = 3;
	
	/**
	 * Constructs the moving entity.
	 * @param x: position's x coordinate
	 * @param y: position's y coordinate
	 * @param facingImages: left, right, front, back images of entity
	 * @param facing: starting direction entity is facing
	 */
	public MovingEnemy(final float x, final float y, String[] facingImages,
			int facing) {
		super(x, y, facingImages, facing);
	}
	
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(super.getCurImage());
		i.draw(super.getX() + 8 - offsetX, super.getY() + 8 - offsetY);
	}

	public int getEnemyType() {
		return -1;
	}
	
	public void applyGravity(float gravity, Tile[][] tileMap) {
        if (super.getVelocity().getY() < maximumFallSpeed) {
            setVelocity(new Vector(super.getVelocity().getX(), super.getVelocity().getY() + gravity));
            if (super.getVelocity().getY() > maximumFallSpeed) 
            	setVelocity(new Vector(super.getVelocity().getX(), maximumFallSpeed));
        }
    }
}

