package Kirby;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Flower extends Entity {

	/**
	 * Constructs the flower.
	 * @param x: position's x coordinate
	 * @param y: position's y coordinate
	 */
	public Flower(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(KirbyGame.FLOWER_IMG_RSC));
	}
}
