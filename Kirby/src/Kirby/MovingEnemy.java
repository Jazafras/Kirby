package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class MovingEnemy extends Entity {
	 
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
	public static final int RIGHT_WALK = 0;
	public static final int LEFT_WALK = 1;
	public static final int RIGHT_ATTACK = 2;
	public static final int LEFT_ATTACK = 3;
	
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
	public MovingEnemy(final float x, final float y, String[] facingImages,
			int facing) {
		super(x, y);
		this.facing = -1;
		this.facingImages = facingImages;
		setFacing(facing);
		vPos = new Vertex(x, y);
	}
	
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(curImage);
		i.draw(super.getX() - offsetX, super.getY() - 8);
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
	
	public boolean facingRight() {
		return (getFacing() % 2 == 0);
	}

	/**
	 * Update the MovingEntity's velocity and translates it.
	 * @param delta: number of milliseconds since the last update
	 */
	public void update(final int delta) {
		translate(velocity.scale(delta));
	}
	
	public int getEnemyType() {
		return -1;
	}
}

