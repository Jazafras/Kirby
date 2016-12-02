package Kirby;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

/**
 * This state is active when the Game is being played. 
 * Transitions From StartUpState
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	int lives;
	Image background;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		KirbyGame bg = (KirbyGame)game;
		lives = 3;
		background = new Image("Kirby/resources/" + bg.map.getMapProperty("background", "grassy_mountains.png"));
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		KirbyGame bg = (KirbyGame)game;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		KirbyGame bg = (KirbyGame)game;
		
		float xOffset = getXOffset(bg);
		float yOffset = getYOffset(bg);
		
        background.draw(xOffset * (background.getWidth() - KirbyGame.SCREEN_WIDTH) / 
        		(bg.map.getWidth() * 32 - KirbyGame.SCREEN_WIDTH) * -1.f, 
        		yOffset * (background.getHeight() - KirbyGame.SCREEN_HEIGHT) / 
        		(bg.map.getHeight() * 32 - KirbyGame.SCREEN_HEIGHT) * -1.f);
		
		bg.map.render((int)(-1 * (xOffset % 32)), (int)(-1 * (yOffset % 32)), 
				(int)(xOffset / 32), (int)(yOffset / 32), bg.SCREEN_WIDTH / 32, bg.SCREEN_HEIGHT / 32);
		
		
		System.out.println("xOffset: " + xOffset);
		System.out.println("yOffset: " + yOffset);
		System.out.println("x coord: " + (int)(-1 * (xOffset % 32)));
		System.out.println("y coord: " + (int)(-1 * (yOffset % 32)));
		System.out.println("x tile: " + (int)(xOffset / 32));
		System.out.println("y tile: " + (int)(yOffset / 32));
		System.out.println();
		
		bg.kirby.render(g, xOffset, yOffset);
		
		g.drawString("Lives: " + lives, 10, 50);
		g.drawString("Level: " + bg.level, 10, 30);
		
	}
	
	private float getXOffset(KirbyGame bg) {
		float kXOffset = 0;
		float maxXOffset = (bg.map.getWidth() * 32) - (bg.SCREEN_WIDTH / 2);
		if (bg.kirby.getX() > maxXOffset)
			kXOffset = maxXOffset - (bg.SCREEN_WIDTH / 2.f);
		else if (bg.kirby.getX() >= bg.SCREEN_WIDTH / 2.f)
			kXOffset = bg.kirby.getX() - (bg.SCREEN_WIDTH / 2.f);
		return kXOffset;
	}
	
	private float getYOffset(KirbyGame bg) {
		float kYOffset = 0;
		float maxYOffset = (bg.map.getHeight() * 32) - (bg.SCREEN_HEIGHT / 2.f);
		if (bg.kirby.getY() > maxYOffset)
			kYOffset = maxYOffset - (bg.SCREEN_HEIGHT / 2.f);
		else if (bg.kirby.getY() >= bg.SCREEN_HEIGHT / 2.f)
			kYOffset = bg.kirby.getY() - (bg.SCREEN_HEIGHT / 2.f);
		return kYOffset;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		KirbyGame bg = (KirbyGame)game;
		
		float kXOffset = getXOffset(bg);
		float kYOffset = getYOffset(bg);
		
		// kirby collision with cubs
		
		Vector move = null;
		for (Underbrush u : bg.underbrushes) {
			Collision c = bg.kirby.collides(u);
			if (bg.kirby.collides(u) != null) {
				move = c.getMinPenetration();
				break;
			}
		}
		
		/*Collision kirbyNest = bg.kirby.collides(bg.nest);
		if (kirbyNest != null) {
			if (bg.kirby.holdingCub()) {
				bg.cubs.remove(bg.kirby.getRescueCub());
				bg.kirby.setRescueCub(null);
			}
			move = kirbyNest.getMinPenetration();
		}*/
		
		keyPresses(input, bg, delta, move);
		
		for (Cub c : bg.cubs) {
			c.setMoving(bg);
			c.update(delta);
		}
		
		// kirby collision with cubs
		/*if (!bg.kirby.holdingCub()) {
			for (Cub c : bg.cubs) {
				if (bg.kirby.collides(c) != null) {
					bg.kirby.setRescueCub(c);
					break;
				}
			}
		}
		
		// poacher collision with kirby or cubs
		Collision poacherkirby = bg.kirby.collides(bg.poacher);
		Collision poacherCub = null;
		for (Cub c : bg.cubs) {
			Collision coll = bg.poacher.collides(c);
			if (coll != null && !c.isHeld()) {
				bg.kirby.setRescueCub(null);
				c.removeImage(ResourceManager.getImage(c.getCurImage()));
				bg.cubs.remove(c);
				poacherCub = coll;
				lives -= 1;
				break;
			}
		}
		
		if (poacherkirby != null) {
			lives -= 1;
			bg.kirby.setPosition(bg.SCREEN_WIDTH - 50, bg.SCREEN_HEIGHT - 50);
			bg.kirby.setvPos(bg.SCREEN_WIDTH - 50, bg.SCREEN_HEIGHT - 50);
			bg.poacher.setPosition(50, 50);
			bg.poacher.setReset(bg);
		}*/
		
		
		bg.kirby.update(delta);
		bg.kirby.setVertex(bg);
		//bg.poacher.setMoving(bg);
		bg.poacher.update(delta);
		
		// Change levels
		/*if (bg.cubs.size() == 0) {
			bg.level++;
			if (bg.level == 4) {
				game.enterState(kirbyGame.GAMEOVERSTATE, new EmptyTransition(), new HorizontalSplitTransition());
			} else {
				game.enterState(kirbyGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition());
			}
		}*/

		checkLives(game, bg);
		
	}
	
	private void keyPresses(Input input, KirbyGame bg, int delta, Vector move) {		
		// Control user input
		if (input.isKeyDown(Input.KEY_LEFT) && (move == null || move.getX() <= 0)) 
			bg.kirby.moveLeft(delta); //bg.kirby.setVelocity(new Vector(-.2f, 0));
		else if (input.isKeyDown(Input.KEY_RIGHT) && (move == null || move.getX() >= 0)) 
			bg.kirby.moveRight(delta); //bg.kirby.setVelocity(new Vector(.2f, 0f));
		/*else if (input.isKeyDown(Input.KEY_UP) && (move == null || move.getY() <= 0)) 
			bg.kirby.setVelocity(new Vector(0f, -.2f));
		else if (input.isKeyDown(Input.KEY_DOWN) && (move == null || move.getY() >= 0)) 
			bg.kirby.setVelocity(new Vector(0f, .2f));*/
		else 
			bg.kirby.setVelocity(new Vector(0f, 0f));
		
		// if space pressed, kirby drops cub
		/*if (input.isKeyDown(Input.KEY_SPACE) && bg.kirby.holdingCub()) 
			bg.kirby.dropCub();*/
		
	}
	
	private void checkLives(StateBasedGame game, KirbyGame bg) {
		// Game over state if no lives left
		if (lives <= 0) {
			//((GameOverState)game.getState(kirbyGame.GAMEOVERSTATE)).setUserScore(bounces);
			bg.level = 1;
			lives = 3;
			game.enterState(KirbyGame.GAMEOVERSTATE);
		}
	}

	@Override
	public int getID() {
		return KirbyGame.PLAYINGSTATE;
	}
	
}