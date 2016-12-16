package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FireKirby extends Kirby {

	public static final String[] fireKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTFIRE,
			KirbyGame.KIRBY_LEFTFIRE,
			KirbyGame.KIRBY_RIGHTFIRE_SUCC,
			KirbyGame.KIRBY_LEFTFIRE_SUCC,
			KirbyGame.KIRBY_RIGHTFIRE_FLY,
			KirbyGame.KIRBY_LEFTFIRE_FLY,
			KirbyGame.KIRBY_RIGHTFIRE_ATTACK,
			KirbyGame.KIRBY_LEFTFIRE_ATTACK,
		};
	
	public FireKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(fireKirbyImages);
		System.out.println("Firekirby");
		super.setCurImage(fireKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 23);
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i = new Image(fireKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void spitFire(KirbyGame bg) {
		bg.attacks.clear();
		float xPos = 30;
		if (!super.facingRight()) //left
			xPos = -50;
		bg.attacks.add(new Attack_SpitFire(bg.kirby.getX() + xPos, bg.kirby.getY() + 10, super.retFacing()));
	}

	@Override
	public int getType() {
		return FIRE;
	}
	
}
