package Kirby;

import jig.Collision;
import jig.Entity;
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
	private int cappyJumpTime = -1;
	private int poppyJumpTime = -1;
	private int sparkyDistance = 40;
	private int twisterDistance = 80;
	private int swordDistance = 100;
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
		//ResourceManager.getSound(KirbyGame.THE_SOUND_OF_DEATH).play();

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
		
		for (Tile t : bg.kirby.surroundingTiles(tileMap))
			t.render(g, xOffset, yOffset);
		
		bg.kirby.render(g, xOffset, yOffset);
		
		//stop deleting the tile shit just comment it out >:K
		for (MovingEnemy e : bg.enemies) { 
			//for (Tile t : e.surroundingTiles(tileMap)){
			//	t.render(g, xOffset, yOffset);
				e.render(g, xOffset, yOffset);
			//}
		}
		for (Brontoburt b : bg.brontoburt) {
			b.setMoving(bg);
		}
		/*for (WaddleDee wd : bg.waddledee) {
			wd.setMoving(bg);
		}*/
		
		for (Attack a : bg.attacks) {
			a.render(g, xOffset, yOffset);
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
		
		if (bg.kirby.getType() == bg.kirby.KCUTTER) {
			CutterKirby k = (CutterKirby) bg.kirby;
			if (k.b != null) {
				Collision c = k.b.collides(k);
				if (c != null && k.cutterTime < CutterKirby.CUTTER_TIME - 5) {
					bg.attacks.remove(k.b);
				}
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
		
		for (Attack a : bg.attacks) {
			for (MovingEnemy e : bg.enemies) {
				Collision c = e.collides(a);
				if (c != null) {
					bg.enemies.remove(e);
					bg.attacks.remove(a);
					break;
				}
			}
		}
		
		if (bg.kirby.getType() == bg.kirby.KTWISTER) {
			TwisterKirby k = (TwisterKirby) bg.kirby;
			if (k.getTwistState())
				enemyCollision(k, bg);
		} else if (bg.kirby.getType() == bg.kirby.KSPARKY) {
			SparkyKirby k = (SparkyKirby) bg.kirby;
			if (k.getSparkState())
				enemyCollision(k, bg);
		} else if (bg.kirby.getType() == bg.kirby.KSWORD) {
			SwordKirby k = (SwordKirby) bg.kirby;
			if (k.getSwordState())
				enemyCollision(k, bg);
		} else if (bg.kirby.getType() == bg.kirby.KBEAM) {
			BeamKirby k = (BeamKirby) bg.kirby;
			if (k.getBeamState())
				enemyCollision(k, bg);
		} else if (bg.kirby.getType() == bg.kirby.KHAMMER) {
			HammerKirby k = (HammerKirby) bg.kirby;
			if (k.getHammerState())
				enemyCollision(k, bg);
		} else if (bg.kirby.getType() == bg.kirby.KFIGHTER) {
			FighterKirby k = (FighterKirby) bg.kirby;
			if (k.getFighterState())
				enemyCollision(k, bg);
		} else if (bg.kirby.getType() == bg.kirby.KBOMB) {
			BombKirby k = (BombKirby) bg.kirby;
			if (k.b != null) {
				k.b.applyGravity(gravity * delta, tileMap);
		        float x = k.b.getX();
		        float y = k.b.getY();
				if (k.b.isOnGround(tileMap)) {
					k.b = null;
					bg.attacks.clear();
					bg.attacks.add(new Explosion(x, y, 1));
				}
			}
			//k.attack(bg);
		}
			
		
		keyPresses(input, bg, delta, move);
		checkLives(game, bg);
		bg.kirby.update(delta);

		System.out.println("kirby position ("+ bg.kirby.getPosition().getX() +", "+ bg.kirby.getPosition().getY()+")");

		//Bonkers movement updates
		for (Bonkers t : bg.bonkers){
			float distance = Math.abs(t.getPosition().getX() - bg.kirby.getPosition().getX());
			if (t.getVelocity().getY() == 0 && t.getVelocity().getX() == 0){
				t.setVelocity(new Vector(.07f, 0f)); //move right
			}
			if (t.sideCollision(tileMap)) {
				if (t.getVelocity().getX() < 0) {
					System.out.println("left Bonker collision");
					t.translate(new Vector(.2f, t.getVelocity().getY()).scale(delta));
					t.setVelocity(new Vector(.07f, 0f)); //move right
					if(distance <= 20){
						t.attack(bg);
					}
				}
				else if (t.getVelocity().getX() > 0){
					System.out.println("right Bonker collision");
					t.translate(new Vector(-.2f, t.getVelocity().getY()).scale(delta));
					t.setVelocity(new Vector(-.07f, 0f)); //move left
					if(distance <= 20){
						t.attack(bg);
					}
				}
			}
		}
		
		//cappy movement updates
		for (Cappy c : bg.cappy){
			//if (s.isOnGround(tileMap)){
			if (c.getVelocity().getX() == 0){
				c.setVelocity(new Vector(-.09f, c.getVelocity().getY())); //go left
			}
			if (c.getVelocity().getY() == 0 && c.getVelocity().getX() == 0){
				c.setVelocity(new Vector(c.getVelocity().getX(), -.1f));
				cappyJumpTime = 8;
			}
			if (cappyJumpTime == 0 && !c.isOnGround(tileMap)){
				c.setVelocity(new Vector(c.getVelocity().getX(), .1f));
			}
			if (c.isOnGround(tileMap)){
				c.setVelocity(new Vector(c.getVelocity().getX(), -.1f));
				cappyJumpTime = 8;
			}
			if (c.getPosition().getX() < 1394){
				c.setVelocity(new Vector(.09f, c.getVelocity().getY()));
			}
			if (c.getPosition().getX() > 1895){
				c.setVelocity(new Vector(-.09f, c.getVelocity().getY()));
			}
		}
		
		//Hot Head movement updates
		for (HotHead h : bg.hothead){
			if (h.getVelocity().getY() == 0 && h.getVelocity().getX() == 0){
				h.setVelocity(new Vector(.07f, 0f)); //move right
			}
			if (h.sideCollision(tileMap)) {
				if (h.getVelocity().getX() < 0) {
					h.translate(new Vector(.2f, h.getVelocity().getY()).scale(delta));
					h.setVelocity(new Vector(.07f, 0f)); //move right
				}
				else if (h.getVelocity().getX() > 0){
					h.translate(new Vector(-.2f, h.getVelocity().getY()).scale(delta));
					h.setVelocity(new Vector(-.07f, 0f)); //move left
				}
			}
		}
		
		//Knuckle Joe movement updates
		for (KnuckleJoe w : bg.knucklejoe){
			float distance = Math.abs(w.getPosition().getX() - bg.kirby.getPosition().getX());
			if (bg.kirby.collides(w) != null){
				w.setVelocity(new Vector(0f, 0f));
			}
			if(bg.kirby.getPosition().getX() < w.getPosition().getX() && bg.kirby.getPosition().getX() != w.getPosition().getX()){ //kirby is to the left of waddledoo
				w.setVelocity(new Vector(-.09f, 0f)); //move left
				if(distance <= 60){
					w.attack(bg);
				}
			}
			if (bg.kirby.getPosition().getX() > w.getPosition().getX() && bg.kirby.getPosition().getX() != w.getPosition().getX()){ //kirby is to the right of waddledoo
				w.setVelocity(new Vector(.09f, 0f)); //move right
				if(distance <= 60){
					w.attack(bg);
				}
			}
			
			if (w.sideCollision(tileMap)) {
				//System.out.println("waddledoo wall collision");			
				if (w.getVelocity().getX() < 0) {
					w.translate(new Vector(0f, -0.1f));
					//System.out.println("left waddledoo collision");
					w.setVelocity(new Vector(-.09f, -2f)); //jump
					w.translate(new Vector(0f, -2f));
				}
				if (w.getVelocity().getX() > 0) {
					w.translate(new Vector(0f, -0.1f));
					//System.out.println("RIGHT waddledoo collision");
					w.setVelocity(new Vector(.09f, -2f)); //jump
					w.translate(new Vector(0f, -2f));
				}
			}
			if (!w.isOnGround(tileMap) && !w.sideCollision(tileMap)){
				w.setVelocity(new Vector(w.getVelocity().getX(), .07f));
			}
						
//			if (w.getVelocity().getX() == 0){
//				w.setVelocity(new Vector(.09f, 0f)); //go right
//			}
//			if (w.getPosition().getX() < 1394){
//				w.setVelocity(new Vector(.09f, 0f));
//				if(distance <= 40){
//					w.attack(bg);
//				}
//			}
//			if (w.getPosition().getX() > 1865){
//				w.setVelocity(new Vector(-.09f, 0f));
//				if(distance <= 40){
//					w.attack(bg);
//				}
//			}
		}
		
		//Noddy movement updates
		for (Noddy w : bg.noddy){
			if (bg.kirby.collides(w) != null){
				w.setVelocity(new Vector(0f, 0f));
			}
			else if(bg.kirby.getPosition().getX() < w.getPosition().getX() && bg.kirby.getPosition().getX() != w.getPosition().getX()){ //kirby is to the left of waddledoo
				w.setVelocity(new Vector(-.05f, 0f)); //move left
			}
			else if (bg.kirby.getPosition().getX() > w.getPosition().getX() && bg.kirby.getPosition().getX() != w.getPosition().getX()){ //kirby is to the right of waddledoo
				w.setVelocity(new Vector(.05f, 0f)); //move right
			}
			if (w.sideCollision(tileMap)) {		
				if (w.getVelocity().getX() < 0) {
					w.translate(new Vector(0f, -0.1f));
					w.setVelocity(new Vector(-.05f, -2f)); //jump
					w.translate(new Vector(w.getVelocity().getX(), -2f));
				}
				if (w.getVelocity().getX() > 0) {
					w.translate(new Vector(0f, -0.1f));
					w.setVelocity(new Vector(.05f, -2f)); //jump
					w.translate(new Vector(0f, -2f));
				}
			}
			if (!w.isOnGround(tileMap) && !w.sideCollision(tileMap)){
				w.setVelocity(new Vector(w.getVelocity().getX(), .07f));
			}

		}
		
		//poppy jr movement updates
		for (PoppyJr s : bg.poppy){
			//if (s.isOnGround(tileMap)){
			if (s.getVelocity().getY() == 0 && s.getVelocity().getX() == 0){
				s.setVelocity(new Vector(0f, -.2f));
				poppyJumpTime = 8;
			}
			if (poppyJumpTime == 0 && !s.isOnGround(tileMap)){
				s.setVelocity(new Vector(0f, .15f));
			}
			if (s.isOnGround(tileMap)){
				s.setVelocity(new Vector(0f, -.2f));
				poppyJumpTime = 8;
			}
			if(bg.kirby.getPosition().getX() < s.getPosition().getX()){
				s.setFacing(LEFT);
			}
			else {
				s.setFacing(RIGHT);
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
		
		//Sir Kibble movement updates
		for (SirKibble s : bg.sirkibble){
			if (s.getVelocity().getY() == 0 && s.getVelocity().getX() == 0){
				s.setVelocity(new Vector(-.05f, 0f)); //move left
			}
			if (s.getPosition().getX() > 1086){
				s.setVelocity(new Vector(-.05f, 0f)); //move left
			}
			if (s.getPosition().getX() < 1018){
				s.setVelocity(new Vector(.05f, 0f)); //move right
			}
		}
		
		//Sparky movement updates
		for (Sparky s : bg.sparky){
			if(bg.kirby.getPosition().getX() < s.getPosition().getX()){
				s.setFacing(LEFT);
			}
			else {
				s.setFacing(RIGHT);
				//s.attack(bg);
			}
			if(Math.abs(bg.kirby.getPosition().getX() - s.getPosition().getX()) < sparkyDistance){
				if(Math.abs(bg.kirby.getPosition().getY() - s.getPosition().getY()) < sparkyDistance){
					s.attack(bg);
				}
			}
		}
		
		//Sword Knight movement updates
		for (SwordKnight sw : bg.swordknight){
			if (sw.getVelocity().getY() == 0 && sw.getVelocity().getX() == 0){
				sw.setVelocity(new Vector(-.04f, 0f)); //move left
			}
			if(Math.abs(bg.kirby.getPosition().getX() - sw.getPosition().getX()) < sparkyDistance){
				if(Math.abs(bg.kirby.getPosition().getY() - sw.getPosition().getY()) < sparkyDistance){
					if(bg.kirby.retFacing() == 1 && sw.retFacing() == 0){ //kirby facing left
						//sw.attack(bg);
						sw.setVelocity(new Vector(sw.getVelocity().getX()*2, 0f));
						sw.attack(bg);
					}
					else if(bg.kirby.retFacing() == 0 && sw.retFacing() == 1){ //kirby facing right
						sw.attack(bg);
						sw.setVelocity(new Vector(sw.getVelocity().getX()*2, 0f));
					}
				}
			}
			
			//return speed to normal after passing kirby
			if(Math.abs(bg.kirby.getPosition().getX() - sw.getPosition().getX()) > sparkyDistance){
				if(sw.getVelocity().getX() > .04f || sw.getVelocity().getX() < -.04f){
					sw.setVelocity(new Vector(sw.getVelocity().getX()/2, 0f));
				}
			}
			if (sw.sideCollision(tileMap)) {
				if (sw.getVelocity().getX() < 0) {
					sw.setPosition(2000,sw.getPosition().getY());
					sw.setVelocity(new Vector(.04f, 0f)); //move right
				}
				else if (sw.getVelocity().getX() > 0){
					sw.setPosition(2350,sw.getPosition().getY());
					sw.setVelocity(new Vector(-.04f, 0f)); //move left
				}
			}
			if (sw.getPosition().getX() > 2350){ //sword knight reached right end of screen
				sw.setPosition(2350,sw.getPosition().getY());
				sw.setVelocity(new Vector(-.04f, 0f)); //move left
			}
		}
		
		//twister movement updates
		for (Twister t : bg.twister){
			if (t.getVelocity().getY() == 0 && t.getVelocity().getX() == 0){
				t.setVelocity(new Vector(-.04f, 0f)); //move left
			}
			if(Math.abs(bg.kirby.getPosition().getX() - t.getPosition().getX()) < sparkyDistance){
				if(Math.abs(bg.kirby.getPosition().getY() - t.getPosition().getY()) < sparkyDistance){
					t.setVelocity(new Vector(t.getVelocity().getX()*2, 0f));
				}
			}
			if (t.sideCollision(tileMap)) {
				if (t.getVelocity().getX() < 0) {
					//System.out.println("left twister collision");
					t.setPosition(1160, t.getPosition().getY());
					t.setVelocity(new Vector(.04f, 0f)); //move right
				}
				else if (t.getVelocity().getX() > 0){
					//System.out.println("right twister collision");
					t.setPosition(1353, t.getPosition().getY());
					t.setVelocity(new Vector(-.04f, 0f)); //move left
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
		
		//waddledoo movement updates
		for (WaddleDoo w : bg.waddledoo){
			float distance = Math.abs(w.getPosition().getX() - bg.kirby.getPosition().getX());
			if (bg.kirby.collides(w) != null){
				w.setVelocity(new Vector(0f, 0f));
			}
			else if(bg.kirby.getPosition().getX() < w.getPosition().getX() && bg.kirby.getPosition().getX() != w.getPosition().getX()){ //kirby is to the left of waddledoo
				w.setVelocity(new Vector(-.09f, 0f)); //move left
				if(distance <= 60){
					w.attack(bg);
				}
			}
			else if (bg.kirby.getPosition().getX() > w.getPosition().getX() && bg.kirby.getPosition().getX() != w.getPosition().getX()){ //kirby is to the right of waddledoo
				w.setVelocity(new Vector(.09f, 0f)); //move right
				if(distance <= 60){
					w.attack(bg);
				}
			}
			if (w.sideCollision(tileMap)) {
				//System.out.println("waddledoo wall collision");			
				if (w.getVelocity().getX() < 0) {
					w.translate(new Vector(0f, -0.1f));
					//System.out.println("left waddledoo collision");
					w.setVelocity(new Vector(-.09f, -2f)); //jump
					w.translate(new Vector(0f, -2f));
				}
				if (w.getVelocity().getX() > 0) {
					w.translate(new Vector(0f, -0.1f));
					//System.out.println("RIGHT waddledoo collision");
					w.setVelocity(new Vector(.09f, -2f)); //jump
					w.translate(new Vector(0f, -2f));
				}
			}
			if (!w.isOnGround(tileMap) && !w.sideCollision(tileMap)){
				w.setVelocity(new Vector(w.getVelocity().getX(), .07f));
			}

		}

		for (MovingEnemy e : bg.enemies){
			e.update(delta);
			//System.out.println("brontoburt up time: " + waitTimeUp);
		}
		
		Attack toRem = null;
		for (Attack a : bg.attacks) {
			if (a.getAttackType() == Attack.EXPLOSION) {
				Explosion e = (Explosion) a;
				e.bombtime--;
				if (e.bombtime <= 0) {
					System.out.println(a);
					toRem = a;
				}
			}
		}
		if (toRem != null) {
			bg.attacks.clear();
		}
		
		for (Attack a : bg.attacks) {
			a.update(delta);
		}
		
		if (cappyJumpTime > 0){
			cappyJumpTime--;
		}
		if (poppyJumpTime > 0){
			poppyJumpTime--;
		}
		if (scarfyJumpTime > 0){
			scarfyJumpTime--;
		}
		if(waitTimeUp > 0){
			waitTimeUp--;
		}
		if(waitTimeDown > 0){
			waitTimeDown--;
		}
	}
	
	public void enemyCollision(Entity toCollide, KirbyGame bg) {
		for (MovingEnemy e : bg.enemies) {
			Collision c = e.collides(toCollide);
			if (c != null) {
				bg.enemies.remove(e);
				break;
			}
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
			bg.kirby.setVelocity(new Vector(0f, bg.kirby.getVelocity().getY()));
		}
		
		if (input.isKeyDown(Input.KEY_SPACE)) {
 			bg.kirby.jump(tileMap);
		}
		
		// z is succ
		// spitfire for fire kirby
		// tornado mode for twister
		if (input.isKeyDown(Input.KEY_Z)) {
			bg.kirby.setVelocity(new Vector(0f,bg.kirby.getVelocity().getY()));
			if (bg.kirby.getType() == bg.kirby.NONE) {
				int distApart = 50;
				bg.kirby.setSuck(true);
				MovingEnemy sucked = null;
				for (MovingEnemy e : bg.enemies) {
					if ((e.getX() < bg.kirby.getX() && !bg.kirby.facingRight() &&
							bg.kirby.getX() - e.getX() < distApart) ||
							(e.getX() > bg.kirby.getX() && bg.kirby.facingRight() &&
							e.getX() - bg.kirby.getX() < distApart)) {
						sucked = e;
						break;
					}
				}
	 			bg.kirby.succ(sucked, bg);
			} else if (bg.kirby.getType() == bg.kirby.FIRE) {
				FireKirby k = (FireKirby) bg.kirby;
				k.spitFire(bg);
				
			} else if (bg.kirby.getType() == bg.kirby.KTWISTER) {
				TwisterKirby k = (TwisterKirby) bg.kirby;
				k.attack(bg);
			} else if (bg.kirby.getType() == bg.kirby.KSPARKY) {
				SparkyKirby k = (SparkyKirby) bg.kirby;

				k.spark(bg);

			} else if (bg.kirby.getType() == bg.kirby.KSWORD) {
				SwordKirby k = (SwordKirby) bg.kirby;
				k.attack(bg);
			} else if (bg.kirby.getType() == bg.kirby.KBEAM) {
				BeamKirby k = (BeamKirby) bg.kirby;
				k.attack(bg);
			} else if (bg.kirby.getType() == bg.kirby.KHAMMER) {
				HammerKirby k = (HammerKirby) bg.kirby;
				k.attack(bg);
			} else if (bg.kirby.getType() == bg.kirby.KFIGHTER) {
				FighterKirby k = (FighterKirby) bg.kirby;
				k.attack(bg);
			} else if (bg.kirby.getType() == bg.kirby.KCUTTER) {
				CutterKirby k = (CutterKirby) bg.kirby;
				k.attack(bg);
			} else if (bg.kirby.getType() == bg.kirby.KBOMB) {
				BombKirby k = (BombKirby) bg.kirby;
				k.attack(bg);
			}
		} else {
			bg.kirby.setSuck(false);
			if (bg.kirby.getType() == bg.kirby.FIRE) {
				bg.attacks.clear();
			}
			if (bg.kirby.getType() == bg.kirby.KSPARKY) {
				SparkyKirby k = (SparkyKirby) bg.kirby;
				k.setSparkState(false);
			}
				
		}
		
		// up arrow is spit
		if (input.isKeyDown(Input.KEY_UP)) {
			if (bg.kirby.getType() == bg.kirby.NONE) {
				bg.kirby.spit(bg);
			} else {
				float xPos = bg.kirby.getX();
				float yPos = bg.kirby.getY();
				bg.kirby = new Kirby(xPos, yPos);
			}
		//down arrow is swallow
		} else if (input.isKeyDown(Input.KEY_DOWN)) { 
			System.out.println("swallow");
			bg.kirby.swallow(bg);
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