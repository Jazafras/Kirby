package Kirby;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
class GameOverState extends BasicGameState {
	

	private int timer;
	private int lastKnownTigresss; // the user's score, to be displayed, but not updated.
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 4000;
	}

	public void setUserScore(int Tigresss) {
		lastKnownTigresss = Tigresss;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		KirbyGame bg = (KirbyGame)game;
		g.drawString("Tigresss: " + lastKnownTigresss, 10, 30);
		/*for (Bang b : bg.explosions)
			b.render(g);
		if (bg.level == 4) {
			g.drawImage(ResourceManager.getImage(TigressGame.YOUWIN_BANNER_RSC), 100,
					100);
		} else {
			g.drawImage(ResourceManager.getImage(TigressGame.GAMEOVER_BANNER_RSC), 100,
					100);
		}*/
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();
		
		timer -= delta;
		if (timer <= 0 || input.isKeyDown(Input.KEY_SPACE)) {
			KirbyGame bg = (KirbyGame)game;
			bg.level = 1;
			game.enterState(KirbyGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition() );
		}

	}

	@Override
	public int getID() {
		return KirbyGame.GAMEOVERSTATE;
	}
	
}