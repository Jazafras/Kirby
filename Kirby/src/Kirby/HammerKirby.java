package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.ResourceManager;

public class HammerKirby extends Kirby {

	public static final String[] hammerKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTHAMMER,
			KirbyGame.KIRBY_LEFTHAMMER,
			KirbyGame.KIRBY_RIGHTHAMMER_SUCC,
			KirbyGame.KIRBY_LEFTHAMMER_SUCC,
			KirbyGame.KIRBY_RIGHTHAMMER_FLY,
			KirbyGame.KIRBY_LEFTHAMMER_FLY,
			KirbyGame.KIRBY_RIGHTHAMMER_ATTACK,
			KirbyGame.KIRBY_LEFTHAMMER_ATTACK,
		};
	
	public static final int HAMMER_ATTACK = 6;
	public static final int HAMMER_TIME = 10;
	private int hammerTime;
	private boolean hammerState;
	
	public HammerKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(hammerKirbyImages);
		System.out.println("Hammerkirby");
		super.setCurImage(hammerKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		hammerTime = 0;
		hammerState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		/*Image i;
		if (hammerState && getCurImage() != hammerKirbyImages[HAMMER_ATTACK + super.retFacing()]) {
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(hammerKirbyImages[HAMMER_ATTACK + super.retFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		} else if (!hammerState && getCurImage() == hammerKirbyImages[HAMMER_ATTACK + super.retFacing()]){
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(hammerKirbyImages[super.getFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		}
		if (hammerState)
			i = new Image(hammerKirbyImages[HAMMER_ATTACK + retFacing()]);
		else
			i = new Image(hammerKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
		
		*/
		Image i;
		removeShapes();
		int add = 0;
		if (hammerState) {
			addImageWithBoundingBox(ResourceManager
					.getImage(hammerKirbyImages[HAMMER_ATTACK + retFacing()]));
			i = new Image(hammerKirbyImages[HAMMER_ATTACK + retFacing()]);
			if (retFacing() == 1) {
				add = -40;
			}
		} else {
			addImageWithBoundingBox(ResourceManager
					.getImage(hammerKirbyImages[super.getFacing()]));
			i = new Image(hammerKirbyImages[super.getFacing()]);
		}
		i.draw(super.getX() - 4 - offsetX + add, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		hammerTime = HAMMER_TIME;
		hammerState = true;
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (hammerTime > 0) {
			hammerTime--;
		} else {
			hammerState = false;
		}
	}
	
	public boolean getHammerState() {
		return hammerState;
	}

	@Override
	public int getType() {
		return KHAMMER;
	}
	
}
