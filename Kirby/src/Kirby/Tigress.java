package Kirby;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Tigress extends MovingEntity {
	
	public static final String[] facingImages = 
		{
			TigressGame.TIGRESS_LEFTIMG_RSC,
			TigressGame.TIGRESS_RIGHTIMG_RSC,
			TigressGame.TIGRESS_FRONTIMG_RSC,
			TigressGame.TIGRESS_BACKIMG_RSC
		};
	
	public static final String[] facingHoldImages = 
		{
			TigressGame.TIGRESS_HOLDING_LEFTIMG_RSC,
			TigressGame.TIGRESS_HOLDING_RIGHTIMG_RSC,
			TigressGame.TIGRESS_HOLDING_FRONTIMG_RSC,
			TigressGame.TIGRESS_HOLDING_BACKIMG_RSC
		};
	
	private boolean flowered;
	private boolean eaten;
	private int powerTime;
	private Cub rescueCub;

	public Tigress(final float x, final float y) {
		super(x, y, facingImages, LEFT);
		setVelocity(new Vector(0, 0));
		flowered = false;
		eaten = false;
		powerTime = 0;
		rescueCub = null;
	}
	
	/**
	 * @return true if powered up with meat, false if not
	 */
	public boolean poweredUp() {
		return flowered || eaten;
	}
	
	/**
	 * @return powerTime: amount of time left for a tigress's power up
	 */
	public int getPowerTime() {
		return powerTime;
	}
	
	/**
	 * Sets the cub that the tigress is currently holding and marks her holdingCub
	 * flag as true.
	 * @param c: cub to be rescued
	 */
	public void setRescueCub(Cub c) {
		rescueCub = c;
		if (c != null) {
			c.setHeld(true);
			setFacingImages(facingHoldImages);
		} else {
			setFacingImages(facingImages);
		}
	}
	
	/**
	 * @return holdingCub: true if tigress is holding cub, false if not
	 */
	public boolean holdingCub() {
		return rescueCub != null;
	}
	
	/**
	 * @return rescueCub: the cub that the tigress is currently holding
	 */
	public Cub getRescueCub() {
		return rescueCub;
	}
	
	/**
	 * Makes the tigress drop the cub she is currently holding in front of her
	 */
	public void dropCub() {
		Cub c = getRescueCub();
		setRescueCub(null);
		c.setHeld(false);
		int xOffset = 0;
		int yOffset = 0;
		if (getFacing() == LEFT)
			xOffset = -70;
		else if (getFacing() == RIGHT)
			xOffset = 70;
		else if (getFacing() == FRONT)
			yOffset = 70;
		else
			yOffset = -70;
		c.setPosition(getPosition().getX() + xOffset, getPosition().getY() + yOffset);
	}
	
	public void setVertex(TigressGame bg) {
		int xRemain = (int)getX() % 50;
		int yRemain = (int)getY() % 50;
		int xDiff = xRemain > 25 ? 50 - xRemain : -1 * xRemain;
		int yDiff = yRemain > 25 ? 50 - yRemain : -1 * yRemain;
		Vertex v = new Vertex((int)getX() + xDiff, (int)getY() + yDiff);
		if (bg.vPos.containsKey(v.toString())){
			setvPos((int)getX() + xDiff, (int)getY() + yDiff);
		}
	}
	
}
