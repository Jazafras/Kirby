package Kirby;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Meat extends Entity {

	/**
	 * Constructs the flower.
	 * @param x: position's x coordinate
	 * @param y: position's y coordinate
	 */
	public Meat(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(TigressGame.MEAT_IMG_RSC));
	}
}
