package Kirby;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

/**
 * This state is active when the Game is being played. In this state, sound is
 * turned on, the bounce counter begins at 0 and increases until 10 at which
 * point a transition to the Game Over state is initiated. The user can also
 * control the ball using the WAS & D keys.
 * Transitions From StartUpState
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	int lives;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		lives = 3;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		KirbyGame bg = (KirbyGame)game;
		
		if (bg.level == 2)
			bg.level2Setup();
		else if (bg.level == 3)
			bg.level3Setup();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		KirbyGame bg = (KirbyGame)game;
		
		g.drawImage(ResourceManager.getImage(KirbyGame.BACKGROUND_IMG_RSC),
				0, 0);
		
		for (Underbrush u : bg.underbrushes)
			u.render(g);
		bg.nest.render(g);
		for (Vertex v : bg.vertices)
			v.render(g);
		bg.tigress.render(g);
		bg.poacher.render(g);
		for (Cub c : bg.cubs) 
			c.render(g);
		
		g.drawString("Lives: " + lives, 10, 50);
		g.drawString("Level: " + bg.level, 10, 30);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		KirbyGame bg = (KirbyGame)game;
		
		// tigress collision with cubs
		Vector move = null;
		for (Underbrush u : bg.underbrushes) {
			Collision c = bg.tigress.collides(u);
			if (bg.tigress.collides(u) != null) {
				move = c.getMinPenetration();
				break;
			}
		}
		
		Collision tigressNest = bg.tigress.collides(bg.nest);
		if (tigressNest != null) {
			if (bg.tigress.holdingCub()) {
				bg.cubs.remove(bg.tigress.getRescueCub());
				bg.tigress.setRescueCub(null);
			}
			move = tigressNest.getMinPenetration();
		}
		
		keyPresses(input, bg, delta, move);
		
		for (Cub c : bg.cubs) {
			c.setMoving(bg);
			c.update(delta);
		}
		
		// tigress collision with cubs
		if (!bg.tigress.holdingCub()) {
			for (Cub c : bg.cubs) {
				if (bg.tigress.collides(c) != null) {
					bg.tigress.setRescueCub(c);
					break;
				}
			}
		}
		
		// poacher collision with tigress or cubs
		Collision poacherTigress = bg.tigress.collides(bg.poacher);
		Collision poacherCub = null;
		for (Cub c : bg.cubs) {
			Collision coll = bg.poacher.collides(c);
			if (coll != null && !c.isHeld()) {
				bg.tigress.setRescueCub(null);
				c.removeImage(ResourceManager.getImage(c.getCurImage()));
				bg.cubs.remove(c);
				poacherCub = coll;
				lives -= 1;
				break;
			}
		}
		
		if (poacherTigress != null) {
			lives -= 1;
			bg.tigress.setPosition(bg.ScreenWidth - 50, bg.ScreenHeight - 50);
			bg.tigress.setvPos(bg.ScreenWidth - 50, bg.ScreenHeight - 50);
			bg.poacher.setPosition(50, 50);
			bg.poacher.setReset(bg);
		}
		
		bg.tigress.update(delta);
		bg.tigress.setVertex(bg);
		bg.poacher.setMoving(bg);
		bg.poacher.update(delta);
		
		//ResourceManager.getSound(BounceGame.HITPADDLE_RSC).play();
		
		// Change levels
		if (bg.cubs.size() == 0) {
			bg.level++;
			if (bg.level == 4) {
				game.enterState(KirbyGame.GAMEOVERSTATE, new EmptyTransition(), new HorizontalSplitTransition());
			} else {
				game.enterState(KirbyGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition());
			}
		}

		checkLives(game, bg);
		
	}
	
	private void keyPresses(Input input, KirbyGame bg, int delta, Vector move) {		
		// Control user input
		if (input.isKeyDown(Input.KEY_LEFT) && (move == null || move.getX() <= 0)) 
			bg.tigress.setVelocity(new Vector(-.3f, 0));
		else if (input.isKeyDown(Input.KEY_RIGHT) && (move == null || move.getX() >= 0)) 
			bg.tigress.setVelocity(new Vector(.3f, 0f));
		else if (input.isKeyDown(Input.KEY_UP) && (move == null || move.getY() <= 0)) 
			bg.tigress.setVelocity(new Vector(0f, -.3f));
		else if (input.isKeyDown(Input.KEY_DOWN) && (move == null || move.getY() >= 0)) 
			bg.tigress.setVelocity(new Vector(0f, .3f));
		else 
			bg.tigress.setVelocity(new Vector(0f, 0f));
		
		// if space pressed, tigress drops cub
		/*if (input.isKeyDown(Input.KEY_SPACE) && bg.tigress.holdingCub()) 
			bg.tigress.dropCub();*/
		
	}
	
	private void checkLives(StateBasedGame game, KirbyGame bg) {
		// Game over state if no lives left
		if (lives <= 0) {
			//((GameOverState)game.getState(TigressGame.GAMEOVERSTATE)).setUserScore(bounces);
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