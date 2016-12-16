package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.ResourceManager;

public class SwordKirby extends Kirby {

	public static final String[] swordKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTSWORD,
			KirbyGame.KIRBY_LEFTSWORD,
			KirbyGame.KIRBY_RIGHTSWORD_SUCC,
			KirbyGame.KIRBY_LEFTSWORD_SUCC,
			KirbyGame.KIRBY_RIGHTSWORD_FLY,
			KirbyGame.KIRBY_LEFTSWORD_FLY,
			KirbyGame.KIRBY_RIGHTSWORD_ATTACK,
			KirbyGame.KIRBY_LEFTSWORD_ATTACK,
		};
	
	public static final int SWORD_ATTACK = 6;
	public static final int SWORD_TIME = 10;
	private int swordTime;
	private boolean swordState;
	
	public SwordKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(swordKirbyImages);
		System.out.println("Swordkirby");
		super.setCurImage(swordKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		swordTime = 0;
		swordState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		removeShapes();
		int add = 0;
		if (swordState) {
			addImageWithBoundingBox(ResourceManager
					.getImage(swordKirbyImages[SWORD_ATTACK + retFacing()]));
			i = new Image(swordKirbyImages[SWORD_ATTACK + retFacing()]);
			if (retFacing() == 1) {
				add = -20;
			}
		} else {
			addImageWithBoundingBox(ResourceManager
					.getImage(swordKirbyImages[super.getFacing()]));
			i = new Image(swordKirbyImages[super.getFacing()]);
		}
		i.draw(super.getX() - 4 - offsetX + add, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		swordTime = SWORD_TIME;
		swordState = true;
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (swordTime > 0) {
			swordTime--;
		} else {
			swordState = false;
		}
	}
	
	public boolean getSwordState() {
		return swordState;
	}

	@Override
	public int getType() {
		return KSWORD;
	}
	
}
