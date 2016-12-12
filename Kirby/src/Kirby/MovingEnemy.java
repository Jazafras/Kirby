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
	 
	public static final int BONKERS = 0;
	public static final int BRONTOBURT = 1;
	public static final int CAPPY = 2;
	public static final int HOTHEAD = 3;
	public static final int KNUCKLEJOE = 4;
	public static final int NODDY = 5;
	public static final int POPPYJR = 6;
	public static final int SCARFY = 7;
	public static final int SIRKIBBLE = 8;
	public static final int SPARKY = 9;
	public static final int SWORDKNIGHT = 10;
	public static final int TWISTER = 11;
	public static final int UFO = 12;
	public static final int WADDLEDEE = 13;
	public static final int WADDLEDOO = 14;
	public static final int STAR = 15;
	 
	// facing directions 
	public static final int LEFT_WALK = 0;
	public static final int RIGHT_WALK = 1;
	public static final int LEFT_ATTACK = 2;
	public static final int RIGHT_ATTACK = 3;
	
	private String[] facingImages;

	private Vector velocity;
	private int facing;
	private String curImage;
	protected Vertex vPos;
	private boolean onGround;

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
		this.facing = -1;
		this.facingImages = facingImages;
		setFacing(facing);
		vPos = new Vertex(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(facingImages[RIGHT_WALK]));
		onGround = true;
	}
	
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(curImage);
		
		i.draw(super.getX() + 8 - offsetX, super.getY() + 8 - offsetY);
	}
	
	/**
	 * Sets the current facing images for the entity.
	 * @param images: list of images to set as
	 */
	protected void setFacingImages(String[] images) {
		facingImages = images;
	}
	
	/**
	 * Sets the current image of the entity.
	 * @param image: image to set the entity as.
	 */
	protected void setCurImage(String image) {
		curImage = image;
	}
	
	/**
	 * @return curImage: current image of the entity
	 */
	protected String getCurImage() {
		return curImage;
	}

    public void setOnGround(boolean b){
        onGround = b;
    }
	
	/**
	 * Sets the velocity of the entity and updates what direction it's facing
	 * unless the velocity is 0 and it's not moving.
	 * @param v: velocity to set.
	 */
	public void setVelocity(final Vector v) {
		velocity = v;
		
		if (v.getX() != 0 || v.getY() != 0) {
			if (v.getX() < 0) 
				setFacing(LEFT_WALK);
			else if (v.getX() > 0) 
				setFacing(RIGHT_WALK);
			/*else if (v.getY() < 0) 
				setFacing(LEFT_ATTACK);
			else 
				setFacing(RIGHT_ATTACK);*/
		}
	}

	/**
	 * @return velocity: current velocity
	 */
	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * Sets the facing direction of the MovingEntity and updates the correct
	 * image for the entity.
	 * @param direction: direction entity will be facing 
	 */
	public void setFacing(final int direction) {
		if (facing != direction && direction != -1) {
			if (curImage != null) 
				removeImage(ResourceManager.getImage(curImage));
			addImageWithBoundingBox(ResourceManager
					.getImage(facingImages[direction]));
			curImage = facingImages[direction];
		}
		facing = direction;
	}

	/**
	 * @return facing: direction entity is currently facing
	 */
	public int getFacing() {
		return facing;
	}
	
	public Vertex getVertex() {
		return vPos;
	}
	
	public void setvPos(int x, int y) {
		vPos = new Vertex(x, y);
	}

	/**
	 * Update the MovingEntity's velocity and translates it.
	 * @param delta: number of milliseconds since the last update
	 */
	/**
	 * Update the MovingEntity's velocity and translates it.
	 * @param delta: number of milliseconds since the last update
	 */
	public void update(final int delta) {
		translate(velocity.scale(delta));
	}
	
	public Set<Tile> surroundingTiles(Tile[][] tiles) {
        Set<Tile> surTiles = new HashSet<Tile>();
        
        for (int i = 0; i < tiles.length; i++) {
        	for (int j = 0; j < tiles[i].length; j++) {
        		if (super.collides(tiles[i][j]) != null)
        			surTiles.add(tiles[i][j]);
        	}
        }
        return surTiles;
    }
	
	public Set<Tile> getGroundTiles(Tile[][] tiles) {
		Set<Tile> surTiles = surroundingTiles(tiles);
        super.setPosition(super.getX(), super.getY() + 15);
        Set<Tile> groundTiles = new HashSet<Tile>();
        Set<Tile> newSurTiles = surroundingTiles(tiles);
        for (Tile t : newSurTiles) {
        	if (t.getType() == Tile.GROUND && !surTiles.contains(t))
        		groundTiles.add(t);
        }
        super.setPosition(super.getX(), super.getY() - 15);   
        return groundTiles;
    }
	
	public boolean inAirSideCollision(Tile[][] tiles) {
		return sideCollision(tiles) && !isOnGround(tiles);
	}
	
	public boolean sideCollision(Tile[][] tiles) {
		Set<Tile> surTiles = surroundingTiles(tiles);
		Set<Tile> groundTiles = getGroundTiles(tiles);
		for (Tile t : surTiles) {
			if (t.getType() == Tile.GROUND && !groundTiles.contains(t))
				return true;
		}
		return false;	
	}
	
	public boolean isOnGround(Tile[][] mapTiles) {
		Set<Tile> tiles = getGroundTiles(mapTiles);
		super.setPosition(super.getX(), super.getY() + 15);
		for (Tile t : tiles) {
             if (t.collides(this) != null) {
            	super.setPosition(super.getX(), super.getY() - 15);
                return true;
             }
        }
        super.setPosition(super.getX(), super.getY() - 15);    
        return false;
    }

	public int getEnemyType() {
		return -1;
	}
	
	public void applyGravity(float gravity, Tile[][] tileMap){
        if (super.getVelocity().getY() < maximumFallSpeed) {
            setVelocity(new Vector(super.getVelocity().getX(), super.getVelocity().getY() + gravity));
            if (super.getVelocity().getY() > maximumFallSpeed) 
            	setVelocity(new Vector(super.getVelocity().getX(), maximumFallSpeed));
        }
    }
}

