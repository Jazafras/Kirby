package Kirby;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

public class BombKirby extends Kirby {
	// I am BombKirby after this class

	public static final String[] bombKirbyImages = 
		{
			KirbyGame.KIRBY_RIGHTPOPPY,
			KirbyGame.KIRBY_LEFTPOPPY,
			KirbyGame.KIRBY_RIGHTPOPPY_SUCC,
			KirbyGame.KIRBY_LEFTPOPPY_SUCC,
			KirbyGame.KIRBY_RIGHTPOPPY_FLY,
			KirbyGame.KIRBY_LEFTPOPPY_FLY,
			KirbyGame.KIRBY_RIGHTPOPPY_ATTACK,
			KirbyGame.KIRBY_LEFTPOPPY_ATTACK,
		};
	
	public static final int BOMB_ATTACK = 6;
	public static final int BOMB_TIME = 40;
	public int bombTime;
	private boolean bombState;
	public Bomb b;
	
	public BombKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(bombKirbyImages);
		System.out.println("Bombkirby");
		super.setCurImage(bombKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY() - 10);
		bombTime = 0;
		b = null;
		bombState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (bombState && getCurImage() != bombKirbyImages[BOMB_ATTACK + super.retFacing()]) {
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(bombKirbyImages[BOMB_ATTACK + super.retFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		} else if (!bombState && getCurImage() == bombKirbyImages[BOMB_ATTACK + super.retFacing()]){
			removeImage(ResourceManager.getImage(getCurImage()));
			setCurImage(bombKirbyImages[super.getFacing()]);
			addImageWithBoundingBox(ResourceManager
					.getImage(getCurImage()));
		}
		if (bombState)
			i = new Image(bombKirbyImages[BOMB_ATTACK + retFacing()]);
		else
			i = new Image(bombKirbyImages[super.getFacing()]);
		i.draw(super.getX() - 4 - offsetX, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		if (bombTime <= 0) {
			bombTime = BOMB_TIME;
			float xPos = 30;
			if (!super.facingRight()) //left
				xPos = -30;
			b = new Bomb(bg.kirby.getX() + xPos, bg.kirby.getY(), super.getFacing());
			bg.kirbyAttacks.add(b);
		}
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		
		if (bombTime > 0) {
			bombTime--;
		} else {
			bombState = false;
		}
	}
	
	public boolean getBombState() {
		return bombState;
	}

	@Override
	public int getType() {
		return KBOMB;
	}
	
}
