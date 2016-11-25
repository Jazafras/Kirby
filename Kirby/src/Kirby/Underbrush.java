package Kirby;

import jig.Entity;
import jig.ResourceManager;

public class Underbrush extends Entity {

	/**
	 * Constructs the moving entity.
	 * @param x: position's x coordinate
	 * @param y: position's y coordinate
	 * @param facingImages: left, right, front, back images of entity
	 * @param facing: starting direction entity is facing
	 */
	public Underbrush(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(KirbyGame.UNDERBRUSH_IMG_RSC));
	}
	
}
