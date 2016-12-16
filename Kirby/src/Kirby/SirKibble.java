package Kirby;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import jig.Collision;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class SirKibble extends MovingEnemy {
	public static final String[] facingImages = 
		{
			KirbyGame.KIBBLE_RIGHT,
			KirbyGame.KIBBLE_LEFT
		};
	
	public static final String[] attackingImages = 
		{
			KirbyGame.KIBBLE_ATTACK_R,
			KirbyGame.KIBBLE_ATTACK_L
		};

	public static final int CUTTER_ATTACK = 6;
	public static final int CUTTER_TIME = 40;
	public int attackTime;
	private boolean attackState;
	public Boomerang b;
	private boolean velChanged = false;
	private int kibblePause;
	
	public SirKibble(final float x, final float y) {
		super(x, y, facingImages, LEFT_WALK);
		setVelocity(new Vector(0, 0));
		attackTime = 0;
		attackState = false;
		b = null;
	}

	
	public void setMoving(KirbyGame bg) {
		if (getVelocity().getY() == 0 && getVelocity().getX() == 0 && kibblePause == 0){
			if(retFacing() == 0){ //facing right
				setVelocity(new Vector(.05f, 0f)); //move left
			}
			else{
				setVelocity(new Vector(-.05f, 0f)); //move left
			}
		}
		if (getPosition().getX() > 1086){
			setPosition(1085,getPosition().getY());
			setVelocity(new Vector(-.05f, 0f)); //move left
		}
		if (getPosition().getX() < 1050){
			setPosition(1051,getPosition().getY());
			setVelocity(new Vector(.05f, 0f)); //move right
		}
		if(Math.abs(bg.kirby.getPosition().getX() - getPosition().getX()) < 200){
			if(Math.abs(bg.kirby.getPosition().getY() - getPosition().getY()) < 40){
				if(bg.kirby.getPosition().getX() < 1066 && retFacing() == 1 && kibblePause == 0){ //left of kibble and kibble is facing left
					setVelocity(new Vector(0f, 0f)); 
					kibblePause = 35;
					attack(bg);
				}
				else if(bg.kirby.getPosition().getX() > 1066 && retFacing() == 0 && kibblePause == 0){ //right of kibble and kibble is facing right 
					setVelocity(new Vector(0f, 0f)); 
					kibblePause = 35;
					attack(bg);
				}

			}
			if (getPosition().getX() > 1086){
				setPosition(1085,getPosition().getY());
				setVelocity(new Vector(-.05f, 0f)); //move left
			}
			if (getPosition().getX() < 1050){
				setPosition(1051,getPosition().getY());
				setVelocity(new Vector(.05f, 0f)); //move right
			}
		}
		if (b != null) {
			Collision c = b.collides(bg.kirby);
			//kirby collides with boomerang
			if (c != null && attackTime < CutterKirby.CUTTER_TIME - 5) {
				bg.enemyAttacks.remove(b);
			}
			//kirby does not collide with boomerang
			else if (c == null && attackTime ==0){
				bg.enemyAttacks.remove(b);
			}
		}
		if (kibblePause > 0){
			kibblePause--;
		}
		
	}	
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) throws SlickException {
		Image i;
		if (attackState){
			i = new Image(attackingImages[super.getFacing()]);
			setVelocity(new Vector(0f, 0f));
			i.draw(super.getX() - 40 - offsetX, super.getY() + 4 - offsetY);
		}
		else{
			i = new Image(facingImages[super.getFacing()]);
			i.draw(super.getX() - 4 - offsetX, super.getY() + 4 - offsetY);
		}
	}
	
	public void attack(KirbyGame bg) {
		if (attackTime <= 0) {
			velChanged = false;
			attackState = true;
			attackTime = CUTTER_TIME;
			float xPos = 30;
			if (!super.facingRight()) //left
				xPos = -30;
			b = new Boomerang(getX() + xPos, getY(), super.getFacing());
			bg.enemyAttacks.add(b);
		}
		//attackTime = 20;
		
	}
	
	public int retFacing() {
    	return super.facingRight() ? 0 : 1;
    }

	@Override
	public void update(final int delta) {
		translate(super.getVelocity().scale(delta));
		if (b != null)	
			b.update(delta);
		
		if (attackTime > 0) {
			if (attackTime < CUTTER_TIME / 2 && !velChanged) {
				b.justSetVelocity(new Vector(-1.f * b.getVelocity().getX(), 0));
				velChanged = true;
			}
			attackTime--;
		} else {
			attackState = false;
		}
	}

	@Override
	public int getEnemyType() {
		return SIRKIBBLE;
	}
}
