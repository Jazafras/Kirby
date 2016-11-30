package Kirby;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class MovingEntity extends Entity {
	 
	// facing directions 
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int FRONT = 2;
	public static final int BACK = 3;
	
	private String[] facingImages;

	private Vector velocity;
	private int facing;
	private String curImage;
	protected Vertex vPos;

	/**
	 * Constructs the moving entity.
	 * @param x: position's x coordinate
	 * @param y: position's y coordinate
	 * @param facingImages: left, right, front, back images of entity
	 * @param facing: starting direction entity is facing
	 */
	public MovingEntity(final float x, final float y, String[] facingImages,
			int facing) {
		super(x, y);
		this.facing = -1;
		this.facingImages = facingImages;
		setFacing(facing);
		vPos = new Vertex(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(facingImages[RIGHT]));
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

	/**
	 * Sets the velocity of the entity and updates what direction it's facing
	 * unless the velocity is 0 and it's not moving.
	 * @param v: velocity to set.
	 */
	public void setVelocity(final Vector v) {
		velocity = v;
		
		if (v.getX() != 0 || v.getY() != 0) {
			if (v.getX() < 0) 
				setFacing(LEFT);
			else if (v.getX() > 0) 
				setFacing(RIGHT);
			else if (v.getY() < 0) 
				setFacing(BACK);
			else 
				setFacing(FRONT);
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
	public void update(final int delta) {
		translate(velocity.scale(delta));
	}
}
