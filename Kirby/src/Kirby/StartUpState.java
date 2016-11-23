package Kirby;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This state is active prior to the Game starting. In this state, sound is
 * turned off, and the bounce counter shows '?'. The user can only interact with
 * the game by pressing the SPACE key which transitions to the Playing State.
 * Otherwise, all game objects are rendered and updated normally.
 * Transitions From (Initialization), GameOverState
 * Transitions To PlayingState
 */
class StartUpState extends BasicGameState {

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		KirbyGame bg = (KirbyGame)game;
		
		g.drawImage(ResourceManager.getImage(KirbyGame.BACKGROUND_IMG_RSC),
				0, 0);	
		
		if (bg.level == 1) {
			g.drawImage(ResourceManager.getImage(KirbyGame.STARTUP_IMG_RSC),
					0, 0);	
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		Input input = container.getInput();
		KirbyGame bg = (KirbyGame)game;

		if (input.isKeyDown(Input.KEY_SPACE))
			bg.enterState(KirbyGame.PLAYINGSTATE);	
		
	}

	@Override
	public int getID() {
		return KirbyGame.STARTUPSTATE;
	}
	
}