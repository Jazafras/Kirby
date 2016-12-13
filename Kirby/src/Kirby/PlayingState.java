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
import java.util.Random;
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
	
	private int waitTimeUp = -1;
	private int waitTimeDown = -1;
	private int scarfyJumpTime = -1;
	Random rand = new Random();
	
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
				(int)(xOffset / 32), (int)(yOffset / 32), KirbyGame.SCREEN_WIDTH / 32, KirbyGame.SCREEN_HEIGHT / 32);
		
		for (Tile t : bg.kirby.getGroundTiles(tileMap))
			t.render(g, xOffset, yOffset);
		
		bg.kirby.render(g, xOffset, yOffset);
		
		for (MovingEnemy e : bg.enemies) { 
			for (Tile t : e.surroundingTiles(tileMap)){
				t.render(g, xOffset, yOffset);
				e.render(g, xOffset, yOffset);
			}
		}
		/*for (Brontoburt b : bg.brontoburt) {
			b.setMoving(bg);
		}*/
		/*for (WaddleDee wd : bg.waddledee) {
			wd.setMoving(bg);
		}*/
		
		//for(int i = 0; i < players.size(); i++){
			//players.get(i).render(g, xOffset, yOffset);
			//bg.kirby.render(g, xOffset, yOffset);
		//}
		
		g.drawString("Lives: " + lives, 10, 50);
		g.drawString("Level: " + bg.level, 10, 30);
	}
	
	private float getXOffset(KirbyGame bg) {
		float kXOffset = 0;
		float maxXOffset = (bg.map.getWidth() * 32) - (KirbyGame.SCREEN_WIDTH / 2);
		if (bg.kirby.getX() > maxXOffset)
			kXOffset = maxXOffset - (KirbyGame.SCREEN_WIDTH / 2.f);
		else if (bg.kirby.getX() >= KirbyGame.SCREEN_WIDTH / 2.f)
			kXOffset = bg.kirby.getX() - (KirbyGame.SCREEN_WIDTH / 2.f);
		return kXOffset;
	}
	
	private float getYOffset(KirbyGame bg) {
		float kYOffset = 0;
		float maxYOffset = (bg.map.getHeight() * 32) - (KirbyGame.SCREEN_HEIGHT / 2.f);
		if (bg.kirby.getY() > maxYOffset)
			kYOffset = maxYOffset - (KirbyGame.SCREEN_HEIGHT / 2.f);
		else if (bg.kirby.getY() >= KirbyGame.SCREEN_HEIGHT / 2.f)
			kYOffset = bg.kirby.getY() - (KirbyGame.SCREEN_HEIGHT / 2.f);
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
				move = LEFT;
			} else if (bg.kirby.getVelocity().getX() > 0){
				move = RIGHT;
			}
		}
		
		if (bg.kirby.jumps == 2 && !bg.kirby.floating) {
			bg.kirby.setFlying();
		}
		bg.kirby.jumpTime--;
		
		if (!bg.kirby.isOnGround(tileMap) || bg.kirby.getVelocity().getY() < 0) {
	     	bg.kirby.applyGravity(gravity * delta, tileMap);
		} else {
			bg.kirby.setVelocity(new Vector(bg.kirby.getVelocity().getX(), 0.f));
			bg.kirby.hitGround();
		}
		
		keyPresses(input, bg, delta, move);
		checkLives(game, bg);
		bg.kirby.update(delta);

		System.out.println("kirby position ("+ bg.kirby.getPosition().getX() +", "+ bg.kirby.getPosition().getY()+")");

		//brontoburt movement updates
		for (Brontoburt burt : bg.brontoburt){
			
			if (burt.getVelocity().getY() == 0 && burt.getVelocity().getX() == 0){
				burt.setVelocity(new Vector(-.07f, -.02f)); //move up and left
				waitTimeUp = 150;
				//System.out.println("brontoburt up time: " + waitTimeUp);
			}
			//System.out.println("brontoburt position: " + burt.getPosition().getX());
			if (burt.getPosition().getX() < 30){ //brontoburt reached left end of screen
				burt.setVelocity(new Vector(.07f, .02f)); //move down and right
				waitTimeDown = 150;
				waitTimeUp = 0;
			}
			if (burt.getPosition().getX() > 2350){ //brontoburt reached right end of screen
				burt.setVelocity(new Vector(-.07f, -.02f)); //move up and left
				waitTimeDown = 0;
				waitTimeUp = 150;
			}
			if (burt.getVelocity().getX() < 0){ //brontoburt is moving left
				if (waitTimeUp == 0){
					waitTimeUp = -1;
					burt.setVelocity(new Vector(-.07f, .02f)); //move down
					waitTimeDown = 150;
					//System.out.println("brontoburt down time: " + waitTimeDown);
				}
				else if (waitTimeDown == 0){
					waitTimeDown = -1;
					burt.setVelocity(new Vector(-.07f, -.02f)); //move up
					waitTimeUp = 150;
				}
			}
			else if (burt.getVelocity().getX() > 0) { //brontoburt is moving right
				if (waitTimeUp == 0){
					waitTimeUp = -1;
					burt.setVelocity(new Vector(.07f, .02f)); //move down
					waitTimeDown = 150;
					//System.out.println("brontoburt down time: " + waitTimeDown);
				}
				else if (waitTimeDown == 0){
					waitTimeDown = -1;
					burt.setVelocity(new Vector(.07f, -.02f)); //move up
					waitTimeUp = 150;
				}
			}
		}
		
		//scarfy movement updates
		for (Scarfy s : bg.scarfy){
			//if (s.isOnGround(tileMap)){
			if (s.getVelocity().getY() == 0 && s.getVelocity().getX() == 0){
				s.setVelocity(new Vector(0f, -.2f));
				scarfyJumpTime = 15;
			}
			if (scarfyJumpTime == 0 && !s.isOnGround(tileMap)){
				s.setVelocity(new Vector(0f, .15f));
			}
			if (s.isOnGround(tileMap)){
				s.setVelocity(new Vector(0f, -.2f));
				scarfyJumpTime = 15;
			}
			
		}
		
		//twister movement updates
		for (Twister t : bg.twister){
			if (t.getVelocity().getY() == 0 && t.getVelocity().getX() == 0){
				t.setVelocity(new Vector(-.07f, 0f)); //move left
			}
			if (t.sideCollision(tileMap)) {
				if (t.getVelocity().getX() < 0) {
					System.out.println("left twister collision");
					t.translate(new Vector(.2f, t.getVelocity().getY()).scale(delta));
					t.setVelocity(new Vector(.07f, 0f)); //move right
				}
				else if (t.getVelocity().getX() > 0){
					System.out.println("right twister collision");
					t.translate(new Vector(-.2f, t.getVelocity().getY()).scale(delta));
					t.setVelocity(new Vector(-.07f, 0f)); //move left
				}
			}
		}
		
		//waddledee movement updates
		for (WaddleDee wdee : bg.waddledee){
			if (wdee.getVelocity().getY() == 0 && wdee.getVelocity().getX() == 0){
				wdee.setVelocity(new Vector(-.07f, 0f)); //move left
			}
			if (wdee.sideCollision(tileMap)) {
				//System.out.println("waddledee wall collision");
				if (wdee.getVelocity().getX() < 0) {
					System.out.println("left waddledee collision");
					wdee.translate(new Vector(.2f, wdee.getVelocity().getY()).scale(delta));
					//wdee.translate(new Vector(0f, 0f));
					wdee.setVelocity(new Vector(.07f, 0f)); //move right
				}
				else if (wdee.getVelocity().getX() > 0){
					System.out.println("right waddledee collision");
					wdee.translate(new Vector(-.2f, wdee.getVelocity().getY()).scale(delta));
					wdee.setVelocity(new Vector(-.07f, 0f)); //move left
				}
			}
		}

		for (MovingEnemy e : bg.enemies){
			e.update(delta);
			//System.out.println("brontoburt up time: " + waitTimeUp);
		}
		waitTimeUp--;
		waitTimeDown--;
		if (scarfyJumpTime > 0){
			scarfyJumpTime--;
		}
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
		

		//move kirby back if he hits a wall
		if (move == LEFT){
			//client.update();
			bg.kirby.translate(new Vector(.2f, bg.kirby.getVelocity().getY()).scale(delta));
		}
			
		else if (move == RIGHT){
			//client.update();
			bg.kirby.translate(new Vector(-.2f, bg.kirby.getVelocity().getY()).scale(delta));
		}
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