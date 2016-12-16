package Kirby;

import java.util.Iterator;

import jig.ResourceManager;

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
 * This state is active when the Game is over. In this state, the ball is
 * neither drawn nor updated; and a gameover banner is displayed. A timer
 * automatically transitions back to the StartUp State.
 * 
 * Transitions From PlayingState
 * 
 * Transitions To StartUpState
 */
class TransitionState extends BasicGameState {
	

	private int timer;
	Image background;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		KirbyGame bg = (KirbyGame)game;
		background = new Image("Kirby/resources/" + bg.map.getMapProperty("background", "grassy_mountains2.png"));
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 40;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		KirbyGame bg = (KirbyGame)game;
		background.draw((background.getWidth() - KirbyGame.SCREEN_WIDTH) / 
        		(bg.map.getWidth() * 32 - KirbyGame.SCREEN_WIDTH) * -1.f, 
        		(background.getHeight() - KirbyGame.SCREEN_HEIGHT) / 
        		(bg.map.getHeight() * 32 - KirbyGame.SCREEN_HEIGHT) * -1.f);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();
		
		timer -= delta;
		if (timer <= 0) {
			KirbyGame bg = (KirbyGame)game;
			game.enterState(KirbyGame.PLAYINGSTATE, new EmptyTransition(), new HorizontalSplitTransition() );
		}

	}

	@Override
	public int getID() {
		return KirbyGame.TRANSITIONSTATE;
	}
	
}