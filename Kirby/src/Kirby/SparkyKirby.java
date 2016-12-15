package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.ResourceManager;

public class SparkyKirby extends Kirby {

	public static final String[] sparkyKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTSPARKY,
			KirbyGame.KIRBY_LEFTSPARKY,
			KirbyGame.KIRBY_RIGHTSPARKY_SUCC,
			KirbyGame.KIRBY_LEFTSPARKY_SUCC,
			KirbyGame.KIRBY_RIGHTSPARKY_FLY,
			KirbyGame.KIRBY_LEFTSPARKY_FLY,
			KirbyGame.KIRBY_RIGHTSPARKY_ATTACK,
			KirbyGame.KIRBY_LEFTSPARKY_ATTACK,
		};
	
	public static final int SPARK_ATTACK = 6;
	private boolean sparkState;
	
	public SparkyKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(sparkyKirbyImages);
		System.out.println("sparkyKirby");
		super.setCurImage(sparkyKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		sparkState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (sparkState && getCurImage() != sparkyKirbyImages[SPARK_ATTACK + super.retFacing()]) {
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(sparkyKirbyImages[SPARK_ATTACK + super.retFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		} else if (!sparkState && getCurImage() == sparkyKirbyImages[SPARK_ATTACK + super.retFacing()]){
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(sparkyKirbyImages[super.getFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		}
		if (sparkState)
			i = new Image(sparkyKirbyImages[SPARK_ATTACK + super.retFacing()]);
		else
			i = new Image(sparkyKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		sparkState = true;
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
	}
	
	public boolean getSparkState() {
		return sparkState;
	}
	
	public void setSparkState(boolean b) {
		sparkState = b;
	}

	@Override
	public int getType() {
		return KSPARKY;
	}
	
}
