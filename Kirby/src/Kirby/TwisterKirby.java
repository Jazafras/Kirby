package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TwisterKirby extends Kirby {

	public static final String[] twisterKirbyImages = 
		{
			KirbyGame.KIRBYTWIST_RIGHT,
			KirbyGame.KIRBYTWIST_LEFT,
			KirbyGame.KIRBYTWIST_RIGHT_SUCC,
			KirbyGame.KIRBYTWIST_LEFT_SUCC,
			KirbyGame.KIRBYTWIST_RIGHT_FLY,
			KirbyGame.KIRBYTWIST_LEFT_FLY,
			KirbyGame.KIRBYTWIST_ATTACK,
			KirbyGame.KIRBYTWIST_ATTACK,
		};
	
	public static final int TWIST_ATTACK = 6;
	public static final int TWIST_TIME = 60;
	private int twistedTime;
	private boolean twistState;
	
	public TwisterKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(twisterKirbyImages);
		System.out.println("Twisterkirby");
		super.setCurImage(twisterKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		twistedTime = 0;
		twistState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (twistState)
			i = new Image(twisterKirbyImages[TWIST_ATTACK]);
		else
			i = new Image(twisterKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		twistedTime = TWIST_TIME;
		twistState = true;
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (twistedTime > 0) {
			twistedTime--;
		} else {
			twistState = false;
		}
	}
	
	public boolean getTwistState() {
		return twistState;
	}

	@Override
	public int getType() {
		return KTWISTER;
	}
	
}
