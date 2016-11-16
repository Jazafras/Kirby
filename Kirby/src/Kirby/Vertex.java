package Kirby;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jig.Entity;
import jig.ResourceManager;

public class Vertex extends Entity {
	
	Map<Vertex, String> neighbors;
	private float x;
	private float y;

	/**
	 * Constructs the moving entity.
	 * @param x: position's x coordinate
	 * @param y: position's y coordinate
	 * @param facingImages: left, right, front, back images of entity
	 * @param facing: starting direction entity is facing
	 */
	public Vertex(final float x, final float y) {
		super(x, y);
		this.x = x;
		this.y = y;
		addImageWithBoundingBox(ResourceManager
				.getImage(TigressGame.VERTEX_IMG_RSC));
		neighbors = new HashMap<Vertex, String>();
	}
	
	public void addNeighbors(Vertex neighbor, String pos) {
		neighbors.put(neighbor, pos);
	}
	
	public Set<Vertex> getNeighbors() {
		return neighbors.keySet();
	}
	
	public boolean equals(Vertex other) {
		return this.x == other.x && this.y == other.y;
	}
	
	public String toString() {
		return x + ", " + y;
	}
	
}
