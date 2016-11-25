package Kirby;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Kirby extends MovingEntity {
	
	public static final String[] facingImages = 
		{
			KirbyGame.KIRBY_LEFTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC,
			KirbyGame.KIRBY_LEFTIMG_RSC
		};
	
	private boolean flowered;
	private boolean eaten;

	public Kirby(final float x, final float y) {
		super(x, y, facingImages, LEFT);
		setVelocity(new Vector(0, 0));
		flowered = false;
		eaten = false;
	}
	
	/**
	 * @return true if powered up with meat, false if not
	 */
	public boolean poweredUp() {
		return flowered || eaten;
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
	
}
