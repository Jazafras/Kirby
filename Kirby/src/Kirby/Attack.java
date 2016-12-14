package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Attack extends MovingEntity {
	
	public static final int STAR = 0;
	public static final int SPITFIRE = 1;

	public Attack(float x, float y, String[] facingImages, int facing) {
		super(x, y, facingImages, facing);
	}
	
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(super.getCurImage());
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}

	public int getAttackType() {
		return -1;
	}
	
}
