package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.ResourceManager;

public class FighterKirby extends Kirby {

	public static final String[] fighterKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTFIGHT,
			KirbyGame.KIRBY_LEFTFIGHT,
			KirbyGame.KIRBY_RIGHTFIGHT_SUCC,
			KirbyGame.KIRBY_LEFTFIGHT_SUCC,
			KirbyGame.KIRBY_RIGHTFIGHT_FLY,
			KirbyGame.KIRBY_LEFTFIGHT_FLY,
			KirbyGame.KIRBY_RIGHTFIGHT_ATTACK,
			KirbyGame.KIRBY_LEFTFIGHT_ATTACK,
		};
	
	public static final int FIGHTER_ATTACK = 6;
	public static final int FIGHTER_TIME = 10;
	private int fighterTime;
	private boolean fighterState;
	
	public FighterKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(fighterKirbyImages);
		System.out.println("Fighterkirby");
		super.setCurImage(fighterKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		fighterTime = 0;
		fighterState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		removeShapes();
		int add = 0;
		if (fighterState) {
			addImageWithBoundingBox(ResourceManager
					.getImage(fighterKirbyImages[FIGHTER_ATTACK + retFacing()]));
			i = new Image(fighterKirbyImages[FIGHTER_ATTACK + retFacing()]);
			if (retFacing() == 1) {
				add = -40;
			}
		} else {
			addImageWithBoundingBox(ResourceManager
					.getImage(fighterKirbyImages[super.getFacing()]));
			i = new Image(fighterKirbyImages[super.getFacing()]);
		}
		i.draw(super.getX() - 4 - offsetX + add, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		fighterTime = FIGHTER_TIME;
		fighterState = true;
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (fighterTime > 0) {
			fighterTime--;
		} else {
			fighterState = false;
		}
	}
	
	public boolean getFighterState() {
		return fighterState;
	}

	@Override
	public int getType() {
		return KFIGHTER;
	}
	
}
