package Kirby;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
class PlayingState extends BasicGameState{
	
	public static final int GROUND = 0;
	public static final int AIR = 1;
	
	public static final int LEFT = 1;
	public static final int RIGHT = 0;
	
	public static final float gravity = 0.0015f;
	
	static int lives;
	Image background;
	Tile[][] tileMap;
	Set<Tile> groundTiles;
	Map<String, Tile> tileFetch;
	
	int topX;
	int topY;
	float yOffset;

	
	private KirbyClient client = null;
	static int port = 7777;
	
	ArrayList<Kirby> players;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		KirbyGame bg = (KirbyGame)game;
		
		//client = new KirbyClient("localhost", 7777);
		//client.connect();
		
		lives = 3;
		background = new Image("Kirby/resources/" + bg.map.getMapProperty("background", "grassy_mountains.png"));
		groundTiles = new HashSet<Tile>();
		tileFetch = new HashMap<String, Tile>();
		loadTiles(bg);
		
		

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(true);
		KirbyGame bg = (KirbyGame)game;
		
		float xOffset = getXOffset(bg);
		yOffset = getYOffset(bg);
		topX = (int)(-1 * (xOffset % 32));
		topY = (int)(-1 * (yOffset % 32));
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		KirbyGame bg = (KirbyGame)game;
		
		float xOffset = getXOffset(bg);
		
		//players = client.getKirbyPositions();
		
		//System.out.println("PlayingState: Rendering. ClientCount is " + KirbyServer.clientCount);
		
		
		topX = (int)(-1 * (xOffset % 32));
		topY = (int)(-1 * (yOffset % 32));
		
        background.draw(xOffset * (background.getWidth() - KirbyGame.SCREEN_WIDTH) / 
        		(bg.map.getWidth() * 32 - KirbyGame.SCREEN_WIDTH) * -1.f, 
        		yOffset * (background.getHeight() - KirbyGame.SCREEN_HEIGHT) / 
        		(bg.map.getHeight() * 32 - KirbyGame.SCREEN_HEIGHT) * -1.f);
		
		bg.map.render((int)(-1 * (xOffset % 32)), (int)(-1 * (yOffset % 32)), 
				(int)(xOffset / 32), (int)(yOffset / 32), bg.SCREEN_WIDTH / 32, bg.SCREEN_HEIGHT / 32);
		

		bg.kirby.render(g, xOffset, yOffset);

		/*for (Bonkers bonk : bg.bonkers)
			bonk.render(g, xOffset, yOffset);
		for (Brontoburt bronto : bg.brontoburt)
			bronto.render(g, xOffset, yOffset);
		for (Cappy cap : bg.cappy)
			cap.render(g, xOffset, yOffset);
		for (HotHead hot : bg.hothead)
			hot.render(g, xOffset, yOffset);
		for (KnuckleJoe knuckj : bg.knucklejoe)
			knuckj.render(g, xOffset, yOffset);
		for (Noddy ndy : bg.noddy)
			ndy.render(g, xOffset, yOffset);
		for (Noddy ndy : bg.noddy)
			ndy.render(g, xOffset, yOffset);
		for (PoppyJr popjr : bg.poppy)
			popjr.render(g, xOffset, yOffset);
		for (Scarfy scarf : bg.scarfy)
			scarf.render(g, xOffset, yOffset);
		for (SirKibble sirkib : bg.sirkibble)
			sirkib.render(g, xOffset, yOffset);
		for (Sparky spark : bg.sparky)
			spark.render(g, xOffset, yOffset);
		for (SwordKnight swordk : bg.swordknight)
			swordk.render(g, xOffset, yOffset);
		for (Twister twist : bg.twister)
			twist.render(g, xOffset, yOffset);

		
		for (WaddleDee wdee : bg.waddledee)
			wdee.render(g, xOffset, yOffset);
		for (WaddleDoo wdoo : bg.waddledoo)
			wdoo.render(g, xOffset, yOffset);*/
		
		for (MovingEnemy e : bg.enemies) { 
			e.render(g, xOffset, yOffset);
		}
		
		
		//for(int i = 0; i < players.size(); i++){
			//players.get(i).render(g, xOffset, yOffset);
			//bg.kirby.render(g, xOffset, yOffset);
		//}

		
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
		
		int move = -1;
		if (bg.kirby.sideCollision(tileMap)) {
			if (bg.kirby.getVelocity().getX() < 0) {
				System.out.println("left collision");
				move = LEFT;
			} else if (bg.kirby.getVelocity().getX() > 0){
				move = RIGHT;
				System.out.println("right collision");
			}
		}
		
		if (bg.kirby.isOnGround(tileMap)) {
			bg.kirby.jumps = 0;
			bg.kirby.floating = false;
			bg.kirby.maximumFallSpeed = 1.f;
			bg.kirby.jumpTime = 0;
		}
		if (bg.kirby.jumps == 2) {
			bg.kirby.floating = true;
			bg.kirby.maximumFallSpeed = .09f;	
			if (bg.kirby.getVelocity().getY() > bg.kirby.maximumFallSpeed) 
				bg.kirby.setVelocity(new Vector(bg.kirby.getVelocity().getX(), bg.kirby.maximumFallSpeed));
		}
		bg.kirby.jumpTime--;
		
		if (!bg.kirby.isOnGround(tileMap) || bg.kirby.getVelocity().getY() < 0) {
	     	bg.kirby.applyGravity(gravity * delta, tileMap);
		} else {
			bg.kirby.setVelocity(new Vector(bg.kirby.getVelocity().getX(), 0.f));
		}
		
		keyPresses(input, bg, delta, move);
		checkLives(game, bg);
		bg.kirby.update(delta);
		for (MovingEnemy e : bg.enemies)
			e.update(delta);

	}
	

	private void keyPresses(Input input, KirbyGame bg, int delta, int move) {	
		// System.out.println(move);

		// Control user input
		if (input.isKeyDown(Input.KEY_LEFT) && move != LEFT) {
			//client.update();
			bg.kirby.setVelocity(new Vector(-.2f, bg.kirby.getVelocity().getY()));
		} else if (input.isKeyDown(Input.KEY_RIGHT) && move != RIGHT) { 
			//client.update();
			bg.kirby.setVelocity(new Vector(.2f, bg.kirby.getVelocity().getY()));
		} else {

			bg.kirby.setVelocity(new Vector(0.f, bg.kirby.getVelocity().getY()));
		}
		
		if (input.isKeyDown(Input.KEY_SPACE)) {
 			bg.kirby.jump(tileMap);
		}
		
		// z is succ
		if (input.isKeyDown(Input.KEY_Z)) {
			int distApart = 50;
			System.out.println("suck");
			bg.kirby.setSuck(true);
			MovingEnemy sucked = null;
			for (MovingEnemy e : bg.enemies) {
				if ((e.getX() < bg.kirby.getX() && bg.kirby.getFacing() == LEFT &&
						bg.kirby.getX() - e.getX() < distApart) ||
						(e.getX() > bg.kirby.getX() && bg.kirby.getFacing() == RIGHT &&
						e.getX() - bg.kirby.getX() < distApart)) {
					sucked = e;
					break;
				}
			}
 			bg.kirby.succ(sucked, bg);
		} else {
			bg.kirby.setSuck(false);
		}
		
		// up arrow is spit
		if (input.isKeyDown(Input.KEY_UP)) {
			bg.kirby.spit(bg);
		
		//down arrow is swallow
		} else if (input.isKeyDown(Input.KEY_RIGHT) && move != RIGHT) { 
			bg.kirby.swallow();
		}
		
		if (move == LEFT){
			//client.update();
			bg.kirby.translate(new Vector(.2f, bg.kirby.getVelocity().getY()).scale(delta));
		}
			
		else if (move == RIGHT){
			//client.update();
			bg.kirby.translate(new Vector(-.2f, bg.kirby.getVelocity().getY()).scale(delta));
		}
		// if space pressed, kirby drops cub
	}
	
	public static int amountLives(){
		return lives;
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
	
	private void loadTiles(KirbyGame bg) {
		tileMap = new Tile[bg.map.getWidth()][bg.map.getHeight()];
		int collisions = bg.map.getLayerIndex("TileMap");
		
		for (int i = 0; i < bg.map.getWidth(); i++) {
			for (int j = 0; j < bg.map.getHeight(); j++) {
				int type = bg.map.getTileId(i, j, collisions);
				Tile t = null;
				if (bg.map.getTileProperty(type, "tileType", "solid").equals("ground")) {
					t = new Tile(topX + i*32 + 8, topY + j*32 + 16, GROUND);
					groundTiles.add(t);
				} else {
					t = new Tile(topX + 8 + i*32, topY + j*32 + 16, AIR);
				}
				tileFetch.put(t.toString(), t);
				tileMap[i][j] = t;
			}
		}
	}

	@Override
	public int getID() {
		return KirbyGame.PLAYINGSTATE;
	}
	
}