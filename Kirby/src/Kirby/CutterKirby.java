package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

public class CutterKirby extends Kirby {
	// I am CutterKirby after this class

	public static final String[] cutterKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTBOOMERANG,
			KirbyGame.KIRBY_LEFTBOOMERANG,
			KirbyGame.KIRBY_RIGHTBOOMERANG_SUCC,
			KirbyGame.KIRBY_LEFTBOOMERANG_SUCC,
			KirbyGame.KIRBY_RIGHTBOOMERANG_FLY,
			KirbyGame.KIRBY_LEFTBOOMERANG_FLY,
			KirbyGame.KIRBY_RIGHTBOOMERANG_ATTACK,
			KirbyGame.KIRBY_LEFTBOOMERANG_ATTACK,
		};
	
	public static final int CUTTER_ATTACK = 6;
	public static final int CUTTER_TIME = 40;
	public int cutterTime;
	private boolean cutterState;
	public Boomerang b;
	private boolean velChanged = false;
	
	public CutterKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(cutterKirbyImages);
		System.out.println("Cutterkirby");
		super.setCurImage(cutterKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		cutterTime = 0;
		b = null;
		cutterState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (cutterState && getCurImage() != cutterKirbyImages[CUTTER_ATTACK + super.retFacing()]) {
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(cutterKirbyImages[CUTTER_ATTACK + super.retFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		} else if (!cutterState && getCurImage() == cutterKirbyImages[CUTTER_ATTACK + super.retFacing()]){
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(cutterKirbyImages[super.getFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		}
		if (cutterState)
			i = new Image(cutterKirbyImages[CUTTER_ATTACK + retFacing()]);
		else
			i = new Image(cutterKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		if (cutterTime <= 0) {
			velChanged = false;
			cutterTime = CUTTER_TIME;
			float xPos = 30;
			if (!super.facingRight()) //left
				xPos = -30;
			b = new Boomerang(bg.kirby.getX() + xPos, bg.kirby.getY(), super.getFacing());
			bg.kirbyAttacks.add(b);
		}
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (b != null)	
			b.update(delta);
		
		if (cutterTime > 0) {
			if (cutterTime < CUTTER_TIME / 2 && !velChanged) {
				b.justSetVelocity(new Vector(-1.f * b.getVelocity().getX(), 0));
				velChanged = true;
			}
			cutterTime--;
		} else {
			cutterState = false;
		}
	}
	
	public boolean getCutterState() {
		return cutterState;
	}

	@Override
	public int getType() {
		return KCUTTER;
	}
	
}
