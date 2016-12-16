package Kirby;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.ConvexPolygon;
import jig.ResourceManager;
import jig.Shape;


public class BeamKirby extends Kirby {

	public static final String[] waddleKirbyImages = 
		{
			KirbyGame.KIRBYWADDLE_RIGHT,
			KirbyGame.KIRBYWADDLE_LEFT,
			KirbyGame.KIRBYWADDLE_RIGHT_SUCC,
			KirbyGame.KIRBYWADDLE_LEFT_SUCC,
			KirbyGame.KIRBYWADDLE_RIGHT_FLY,
			KirbyGame.KIRBYWADDLE_LEFT_FLY,
			KirbyGame.KIRBYWADDLE_RIGHT_ATTACK,
			KirbyGame.KIRBYWADDLE_LEFT_ATTACK,
		};
	
	public static final int BEAM_ATTACK = 6;
	public static final int BEAM_TIME = 10;
	private int beamTime;
	private boolean beamState;
	
	public BeamKirby(float x, float y) {
		super(x, y);
		super.setFacingImages(waddleKirbyImages);
		super.setCurImage(waddleKirbyImages[super.getFacing()]);
		super.setPosition(super.getX(), super.getY());
		beamTime = 0;
		beamState = false;
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		removeShapes();
		int add = 0;
		if (beamState) {
			addImageWithBoundingBox(ResourceManager
					.getImage(waddleKirbyImages[BEAM_ATTACK + retFacing()]));
			i = new Image(waddleKirbyImages[BEAM_ATTACK + retFacing()]);
			if (retFacing() == 1) {
				add = -40;
			}
		} else {
			addImageWithBoundingBox(ResourceManager
					.getImage(waddleKirbyImages[super.getFacing()]));
			i = new Image(waddleKirbyImages[super.getFacing()]);
		}
		i.draw(super.getX() - 4 - offsetX + add, super.getY() - 4 - offsetY);
	}
	
	public void attack(KirbyGame bg) {
		beamTime = BEAM_TIME;
		beamState = true;
	}
	
	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (beamTime > 0) {
			beamTime--;
		} else {
			beamState = false;
		}
	}
	
	public boolean getBeamState() {
		return beamState;
	}

	@Override
	public int getType() {
		return KBEAM;
	}
	
}
