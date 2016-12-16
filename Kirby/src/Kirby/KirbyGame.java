package Kirby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class KirbyGame extends StateBasedGame {
	
	public static final int BONKERS = 0;
	public static final int BRONTOBURT = 1;
	public static final int CAPPY = 2;
	public static final int HOTHEAD = 3;
	public static final int KNUCKLEJOE = 4;
	public static final int NODDY = 5;
	public static final int POPPYJR = 6;
	public static final int SCARFY = 7;
	public static final int SIRKIBBLE = 8;
	public static final int SPARKY = 9;
	public static final int SWORDKNIGHT = 10;
	public static final int TWISTER = 11;
	public static final int UFO = 12;
	public static final int WADDLEDEE = 13;
	public static final int WADDLEDOO = 14;
	public static final int STAR = 15;
	
	public static final String THE_SOUND_OF_DEATH = "Kirby/resources/iwanttodie.wav";
	
	// states of the game
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int TRANSITIONSTATE = 3;
	public static final int YOUWINSTATE = 4;
	
	// background
	public static final String BACKGROUND_IMG_RSC = "Kirby/resources/background.png";
	public static final String STARTUP_IMG_RSC = "Kirby/resources/startup.png";
	public static final String GAMEOVER_IMG_RSC = "Kirby/resources/GAMEOVER.png";
	public static final String YOUWIN_IMG_RSC = "Kirby/resources/YOUWIN.png";
	
	/***Kirby images***/
	//default
	public static final String KIRBY_LEFTIMG_RSC = "Kirby/resources/kirby_regular_left.png";
	public static final String KIRBY_RIGHTIMG_RSC = "Kirby/resources/kirby_regular_right.png";
	public static final String KIRBY_RIGHT_SUCC = "Kirby/resources/kirby_right_succ.png";
	public static final String KIRBY_LEFT_SUCC = "Kirby/resources/kirby_left_succ.png";
	public static final String KIRBY_RIGHT_FLY = "Kirby/resources/kirby_right_fly.png";
	public static final String KIRBY_LEFT_FLY = "Kirby/resources/kirby_left_fly.png";
	public static final String KIRBY_LEFT_FULL = "Kirby/resources/kirby_full_left.png";
	public static final String KIRBY_RIGHT_FULL = "Kirby/resources/kirby_full_right.png";
	public static final String KIRBY_LEFT_HURT = "Kirby/resources/kirby_hurt_left.png";
	public static final String KIRBY_RIGHT_HURT = "Kirby/resources/kirby_hurt_right.png";
	public static final String KIRBY_BEHIND = "Kirby/resources/kirby_behind.png";
	
	//boomerang kirby
	public static final String KIRBY_LEFTBOOMERANG = "Kirby/resources/boomerangkirby/kirby_left.png";
	public static final String KIRBY_RIGHTBOOMERANG = "Kirby/resources/boomerangkirby/kirby_right.png";
	public static final String KIRBY_RIGHTBOOMERANG_SUCC = "Kirby/resources/boomerangkirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTBOOMERANG_SUCC = "Kirby/resources/boomerangkirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTBOOMERANG_FLY = "Kirby/resources/boomerangkirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTBOOMERANG_FLY = "Kirby/resources/boomerangkirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTBOOMERANG_ATTACK = "Kirby/resources/boomerangkirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTBOOMERANG_ATTACK = "Kirby/resources/boomerangkirby/kirby_left_attack.png";
	//boomerang attack
	public static final String BOOMERANG_RSC = "Kirby/resources/boomerang.png";
	
	//fighter kirby
	public static final String KIRBY_LEFTFIGHT = "Kirby/resources/fighterkirby/kirby_left.png";
	public static final String KIRBY_RIGHTFIGHT = "Kirby/resources/fighterkirby/kirby_right.png";
	public static final String KIRBY_RIGHTFIGHT_SUCC = "Kirby/resources/fighterkirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTFIGHT_SUCC = "Kirby/resources/fighterkirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTFIGHT_FLY = "Kirby/resources/fighterkirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTFIGHT_FLY = "Kirby/resources/fighterkirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTFIGHT_ATTACK = "Kirby/resources/fighterkirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTFIGHT_ATTACK = "Kirby/resources/fighterkirby/kirby_left_attack.png";
	
	//fire kirby
	public static final String KIRBY_LEFTFIRE = "Kirby/resources/firekirby/kirby_left.png";
	public static final String KIRBY_RIGHTFIRE = "Kirby/resources/firekirby/kirby_right.png";
	public static final String KIRBY_RIGHTFIRE_SUCC = "Kirby/resources/firekirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTFIRE_SUCC = "Kirby/resources/firekirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTFIRE_FLY = "Kirby/resources/firekirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTFIRE_FLY = "Kirby/resources/firekirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTFIRE_ATTACK = "Kirby/resources/firekirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTFIRE_ATTACK = "Kirby/resources/firekirby/kirby_left_attack.png";
	//fire attack
	public static final String LEFTFIRE_ATTACK = "Kirby/resources/firekirby/fire_left.png";
	public static final String RIGHTFIRE_ATTACK = "Kirby/resources/firekirby/fire_right.png";
	
	//hammer kirby
	public static final String KIRBY_LEFTHAMMER = "Kirby/resources/hammerkirby/kirby_left.png";
	public static final String KIRBY_RIGHTHAMMER = "Kirby/resources/hammerkirby/kirby_right.png";
	public static final String KIRBY_RIGHTHAMMER_SUCC = "Kirby/resources/hammerkirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTHAMMER_SUCC = "Kirby/resources/hammerkirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTHAMMER_FLY = "Kirby/resources/hammerkirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTHAMMER_FLY = "Kirby/resources/hammerkirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTHAMMER_ATTACK = "Kirby/resources/hammerkirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTHAMMER_ATTACK = "Kirby/resources/hammerkirby/kirby_left_attack.png";

	//noddy kirby
	public static final String KIRBY_SLEEP = "Kirby/resources/sleepKirby.png";
	
	//poppy kirby
	public static final String KIRBY_LEFTPOPPY = "Kirby/resources/poppykirby/kirby_left.png";
	public static final String KIRBY_RIGHTPOPPY = "Kirby/resources/poppykirby/kirby_right.png";
	public static final String KIRBY_RIGHTPOPPY_SUCC = "Kirby/resources/poppykirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTPOPPY_SUCC = "Kirby/resources/poppykirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTPOPPY_FLY = "Kirby/resources/poppykirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTPOPPY_FLY = "Kirby/resources/poppykirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTPOPPY_ATTACK = "Kirby/resources/poppykirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTPOPPY_ATTACK = "Kirby/resources/poppykirby/kirby_left_attack.png";
	//bomb
	public static final String BOMB_RSC = "Kirby/resources/bomb.png";
	public static final String EXPLOSION_RSC = "Kirby/resources/explosion.png";
	
	//sparky kirby
	public static final String KIRBY_LEFTSPARKY = "Kirby/resources/sparkykirby/kirby_left.png";
	public static final String KIRBY_RIGHTSPARKY = "Kirby/resources/sparkykirby/kirby_right.png";
	public static final String KIRBY_RIGHTSPARKY_SUCC = "Kirby/resources/sparkykirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTSPARKY_SUCC = "Kirby/resources/sparkykirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTSPARKY_FLY = "Kirby/resources/sparkykirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTSPARKY_FLY = "Kirby/resources/sparkykirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTSPARKY_ATTACK = "Kirby/resources/sparkykirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTSPARKY_ATTACK = "Kirby/resources/sparkykirby/kirby_left_attack.png";
	
	//sword kirby
	public static final String KIRBY_LEFTSWORD = "Kirby/resources/swordkirby/kirby_left.png";
	public static final String KIRBY_RIGHTSWORD = "Kirby/resources/swordkirby/kirby_right.png";
	public static final String KIRBY_RIGHTSWORD_SUCC = "Kirby/resources/swordkirby/kirby_right_succ.png";
	public static final String KIRBY_LEFTSWORD_SUCC = "Kirby/resources/swordkirby/kirby_left_succ.png";
	public static final String KIRBY_RIGHTSWORD_FLY = "Kirby/resources/swordkirby/kirby_right_fly.png";
	public static final String KIRBY_LEFTSWORD_FLY = "Kirby/resources/swordkirby/kirby_left_fly.png";
	public static final String KIRBY_RIGHTSWORD_ATTACK = "Kirby/resources/swordkirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTSWORD_ATTACK = "Kirby/resources/swordkirby/kirby_left_attack.png";
	
	//twister kirby
	public static final String KIRBYTWIST_LEFT = "Kirby/resources/twisterkirby/kirby_left.png";
	public static final String KIRBYTWIST_RIGHT = "Kirby/resources/twisterkirby/kirby_right.png";
	public static final String KIRBYTWIST_RIGHT_SUCC = "Kirby/resources/twisterkirby/kirby_right_succ.png";
	public static final String KIRBYTWIST_LEFT_SUCC = "Kirby/resources/twisterkirby/kirby_left_succ.png";
	public static final String KIRBYTWIST_RIGHT_FLY = "Kirby/resources/twisterkirby/kirby_right_fly.png";
	public static final String KIRBYTWIST_LEFT_FLY = "Kirby/resources/twisterkirby/kirby_left_fly.png";
	public static final String KIRBYTWIST_ATTACK = "Kirby/resources/twisterkirby/kirby_attack.png";
	
	//ufo kirby
	public static final String KIRBYUFO_LEFT = "Kirby/resources/ufokirby/kirby_left.png";
	public static final String KIRBYUFO_RIGHT = "Kirby/resources/ufokirby/kirby_right.png";
	public static final String KIRBY_RIGHTUFO_ATTACK = "Kirby/resources/ufokirby/kirby_right_attack.png";
	public static final String KIRBY_LEFTUFO_ATTACK = "Kirby/resources/ufokirby/kirby_left_attack.png";
	//ufo beam
	public static final String KIRBY_BEAM_RSC = "Kirby/resources/kirbyBeam.png";
	
	//waddledoo kirby
	public static final String KIRBYWADDLE_LEFT = "Kirby/resources/waddlekirby/kirby_left.png";
	public static final String KIRBYWADDLE_RIGHT = "Kirby/resources/waddlekirby/kirby_right.png";
	public static final String KIRBYWADDLE_RIGHT_SUCC = "Kirby/resources/waddlekirby/kirby_right_succ.png";
	public static final String KIRBYWADDLE_LEFT_SUCC = "Kirby/resources/waddlekirby/kirby_left_succ.png";
	public static final String KIRBYWADDLE_RIGHT_FLY = "Kirby/resources/waddlekirby/kirby_right_fly.png";
	public static final String KIRBYWADDLE_LEFT_FLY = "Kirby/resources/waddlekirby/kirby_left_fly.png";
	public static final String KIRBYWADDLE_LEFT_ATTACK = "Kirby/resources/waddlekirby/kirby_left_attack.png";
	public static final String KIRBYWADDLE_RIGHT_ATTACK = "Kirby/resources/waddlekirby/kirby_right_attack.png";
	
	/***Enemy images***/
	//Brontoburt images
	public static final String BRONTOBURT_LEFT = "Kirby/resources/brontoburt_left.png";
	public static final String BRONTOBURT_RIGHT = "Kirby/resources/brontoburt_right.png";
	
	//Bonkers images
	public static final String BONKERS_LEFT = "Kirby/resources/bonkers_left.png";
	public static final String BONKERS_RIGHT = "Kirby/resources/bonkers_right.png";
	public static final String BONKERS_ATTACK_R = "Kirby/resources/bonkers_attackR.png";
	public static final String BONKERS_ATTACK_L = "Kirby/resources/bonkers_attackL.png";
	
	//Cappy images
	public static final String CAPPY_LEFT = "Kirby/resources/cappy_left.png";
	public static final String CAPPY_RIGHT = "Kirby/resources/cappy_right.png";
	
	//Hot Head images
	public static final String HOTHEAD_LEFT = "Kirby/resources/hothead_left.png";
	public static final String HOTHEAD_RIGHT = "Kirby/resources/hothead_right.png";
	public static final String HOTHEAD_ATTACK_R = "Kirby/resources/hothead_attackR.png";
	public static final String HOTHEAD_ATTACK_L = "Kirby/resources/hothead_attackL.png";

	//Kibble images
	public static final String KIBBLE_LEFT = "Kirby/resources/kibble_left.png";
	public static final String KIBBLE_RIGHT = "Kirby/resources/kibble_right.png";
	public static final String KIBBLE_ATTACK_R = "Kirby/resources/kibble_attackR.png";
	public static final String KIBBLE_ATTACK_L = "Kirby/resources/kibble_attackL.png";
	
	//Knuckle Joe images
	public static final String KNUCKLE_LEFT = "Kirby/resources/knuckle_left.png";
	public static final String KNUCKLE_RIGHT = "Kirby/resources/knuckle_right.png";
	public static final String KNUCKLE_ATTACK_R = "Kirby/resources/knuckle_attackR.png";
	public static final String KNUCKLE_ATTACK_L = "Kirby/resources/knuckle_attackL.png";
	
	//Noddy images
	public static final String NODDY_LEFT = "Kirby/resources/noddy_left.png";
	public static final String NODDY_RIGHT = "Kirby/resources/noddy_right.png";
	
	//Poppy images
	public static final String POPPY_LEFT = "Kirby/resources/poppy_left.png";
	public static final String POPPY_RIGHT = "Kirby/resources/poppy_right.png";
	public static final String POPPY_ATTACK_R = "Kirby/resources/poppy_attackR.png";
	public static final String POPPY_ATTACK_L = "Kirby/resources/poppy_attackL.png";
	
	//Scarfy images
	public static final String SCARFY_LEFT = "Kirby/resources/scarfy_left.png";
	public static final String SCARFY_RIGHT = "Kirby/resources/scarfy_right.png";
	
	//Sparky images
	public static final String SPARKY_LEFT = "Kirby/resources/sparky_left.png";
	public static final String SPARKY_RIGHT = "Kirby/resources/sparky_right.png";
	public static final String SPARKY_ATTACK_R = "Kirby/resources/sparky_attackR.png";
	public static final String SPARKY_ATTACK_L = "Kirby/resources/sparky_attackL.png";
	
	//Sword Knight images
	public static final String SWORDKNIGHT_LEFT = "Kirby/resources/bladeknight_left.png";
	public static final String SWORDKNIGHT_RIGHT = "Kirby/resources/bladeknight_right.png";
	public static final String SWORDKNIGHT_ATTACK_R = "Kirby/resources/bladeknight_attack_right.png";
	public static final String SWORDKNIGHT_ATTACK_L = "Kirby/resources/bladeknight_attack_left.png";
	
	//Twister images
	public static final String TWISTER_LEFT = "Kirby/resources/twister_left.png";
	public static final String TWISTER_RIGHT = "Kirby/resources/twister_right.png";
	
	//UFO images
	public static final String UFO_LEFT = "Kirby/resources/ufo_left.png";
	public static final String UFO_RIGHT = "Kirby/resources/ufo_right.png";
	public static final String UFO_ATTACK_R = "Kirby/resources/ufo_attackRight.png";
	public static final String UFO_ATTACK_L = "Kirby/resources/ufo_attackLeft.png";
	//ufo beam
	public static final String UFO_BEAM_RSC = "Kirby/resources/beam.png";
	
	public static final String HURT_RSC = "Kirby/resources/hurt.png";
	
	//WaddleDee images
	public static final String WADDLEDEE_LEFT = "Kirby/resources/waddledee_left.png";
	public static final String WADDLEDEE_RIGHT = "Kirby/resources/waddledee_right.png";
		
	//WaddleDoo images
	public static final String WADDLEDOO_LEFT = "Kirby/resources/waddledoo_left.png";
	public static final String WADDLEDOO_RIGHT = "Kirby/resources/waddledoo_right.png";
	public static final String WADDLEDOO_ATTACK_R = "Kirby/resources/waddledoo_attackR.png";
	public static final String WADDLEDOO_ATTACK_L = "Kirby/resources/waddledoo_attackL.png";	
	
	public static final String POACHER_LEFTIMG_RSC = "Kirby/resources/poacher-left.png";
	
	public static final String VERTEX_IMG_RSC = "Kirby/resources/vertex-r.png";
	
	public static final String TILE_IMG_RSC = "Kirby/resources/tile.png";
	
	public static final String STAR_IMG_RSC = "Kirby/resources/star.png";
	
	//public static final String HITWALL_RSC = "bounce/resource/wall_hit.wav";
	
    public static final int SCREEN_WIDTH  = 1280;
    public static final int SCREEN_HEIGHT = SCREEN_WIDTH / 16 * 9;
    //public static final float SCALE = (float) (1.25*((double)SCREEN_WIDTH/1280));
	
	TiledMap map;
	
	int level;
	Kirby kirby;
	
	Set<MovingEnemy> enemies;
	Set<Attack> kirbyAttacks;
	Set<Attack> enemyAttacks;
	
	ArrayList<Bonkers> bonkers; //hammer state
	ArrayList<Brontoburt> brontoburt;
	ArrayList<Cappy> cappy;
	ArrayList<HotHead> hothead; //fire state
	ArrayList<KnuckleJoe> knucklejoe; //fighter state
	ArrayList<Noddy> noddy; //sleep state
	ArrayList<PoppyJr> poppy; //bomb state
	ArrayList<SirKibble> sirkibble; //boomerang state
	ArrayList<Scarfy> scarfy;
	ArrayList<Sparky> sparky; //spark state
	ArrayList<SwordKnight> swordknight; //sword state
	ArrayList<Twister> twister; //twister state
	ArrayList<UFO> ufo; //ufo state
	ArrayList<WaddleDee> waddledee;
	ArrayList<WaddleDoo> waddledoo; //beam state
	
	Set<Vertex> vertices;
	Map<String, Vertex> vPos;
	
	static KirbyServer server;
	
	/**
	 * Create the KirbyGame frame, saving the width and height for later use.
	 * @param title: the window's title
	 * @param width: the window's width
	 * @param height: the window's height
	 */
	public KirbyGame(String title) {
		super(title);

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		
		enemies = new HashSet<MovingEnemy>();
		kirbyAttacks = new HashSet<Attack>();
		enemyAttacks = new HashSet<Attack>();
		
		bonkers = new ArrayList<Bonkers>();
		brontoburt = new ArrayList<Brontoburt>();
		cappy = new ArrayList<Cappy>();
		hothead = new ArrayList<HotHead>();
		knucklejoe = new ArrayList<KnuckleJoe>();
		noddy = new ArrayList<Noddy>();
		poppy = new ArrayList<PoppyJr>();
		scarfy = new ArrayList<Scarfy>();
		sirkibble = new ArrayList<SirKibble>();
		sparky = new ArrayList<Sparky>();
		swordknight = new ArrayList<SwordKnight>();
		twister = new ArrayList<Twister>();
		ufo = new ArrayList<UFO>();
		waddledoo = new ArrayList<WaddleDoo>();
		waddledee = new ArrayList<WaddleDee>();
		
		vertices = new HashSet<Vertex>();
		vPos = new HashMap<String, Vertex>();
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		addState(new TransitionState());
		addState(new YouWinState());
		loadImages();
		
		level = 1;
		
		level1Setup();
		
		kirby = new Kirby(90, 422);
		
		
	}
	
	private void loadImages() {
		ResourceManager.loadSound(THE_SOUND_OF_DEATH); 
		
		ResourceManager.loadImage(BACKGROUND_IMG_RSC);
		ResourceManager.loadImage(STARTUP_IMG_RSC);
		ResourceManager.loadImage(GAMEOVER_IMG_RSC);
		ResourceManager.loadImage(YOUWIN_IMG_RSC);
		
		ResourceManager.loadImage(KIRBY_LEFTIMG_RSC);
		ResourceManager.loadImage(KIRBY_RIGHTIMG_RSC);
		ResourceManager.loadImage(KIRBY_RIGHT_SUCC);
		ResourceManager.loadImage(KIRBY_LEFT_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHT_FLY);
		ResourceManager.loadImage(KIRBY_LEFT_FLY);
		ResourceManager.loadImage(KIRBY_LEFT_FULL);
		ResourceManager.loadImage(KIRBY_RIGHT_FULL);
		
		ResourceManager.loadImage(KIRBY_LEFTBOOMERANG);
		ResourceManager.loadImage(KIRBY_RIGHTBOOMERANG);
		ResourceManager.loadImage(KIRBY_RIGHTBOOMERANG_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTBOOMERANG_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTBOOMERANG_FLY);
		ResourceManager.loadImage(KIRBY_LEFTBOOMERANG_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTBOOMERANG_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTBOOMERANG_ATTACK);
		
		ResourceManager.loadImage(BOOMERANG_RSC);
		
		ResourceManager.loadImage(KIRBY_LEFTFIGHT);
		ResourceManager.loadImage(KIRBY_RIGHTFIGHT);
		ResourceManager.loadImage(KIRBY_RIGHTFIGHT_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTFIGHT_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTFIGHT_FLY);
		ResourceManager.loadImage(KIRBY_LEFTFIGHT_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTFIGHT_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTFIGHT_ATTACK);

		ResourceManager.loadImage(KIRBY_LEFTFIRE);
		ResourceManager.loadImage(KIRBY_RIGHTFIRE);
		ResourceManager.loadImage(KIRBY_RIGHTFIRE_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTFIRE_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTFIRE_FLY);
		ResourceManager.loadImage(KIRBY_LEFTFIRE_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTFIRE_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTFIRE_ATTACK);
		
		ResourceManager.loadImage(LEFTFIRE_ATTACK);
		ResourceManager.loadImage(RIGHTFIRE_ATTACK);

		ResourceManager.loadImage(KIRBY_LEFTHAMMER);
		ResourceManager.loadImage(KIRBY_RIGHTHAMMER);
		ResourceManager.loadImage(KIRBY_RIGHTHAMMER_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTHAMMER_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTHAMMER_FLY);
		ResourceManager.loadImage(KIRBY_LEFTHAMMER_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTHAMMER_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTHAMMER_ATTACK);
		
		ResourceManager.loadImage(KIRBY_LEFTPOPPY);
		ResourceManager.loadImage(KIRBY_RIGHTPOPPY);
		ResourceManager.loadImage(KIRBY_RIGHTPOPPY_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTPOPPY_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTPOPPY_FLY);
		ResourceManager.loadImage(KIRBY_LEFTPOPPY_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTPOPPY_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTPOPPY_ATTACK);
		
		ResourceManager.loadImage(BOMB_RSC);
		ResourceManager.loadImage(HURT_RSC);
		ResourceManager.loadImage(EXPLOSION_RSC);
		
		ResourceManager.loadImage(KIRBY_LEFTSPARKY);
		ResourceManager.loadImage(KIRBY_RIGHTSPARKY);
		ResourceManager.loadImage(KIRBY_RIGHTSPARKY_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTSPARKY_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTSPARKY_FLY);
		ResourceManager.loadImage(KIRBY_LEFTSPARKY_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTSPARKY_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTSPARKY_ATTACK);
		
		ResourceManager.loadImage(KIRBY_LEFTSWORD);
		ResourceManager.loadImage(KIRBY_RIGHTSWORD);
		ResourceManager.loadImage(KIRBY_RIGHTSWORD_SUCC);
		ResourceManager.loadImage(KIRBY_LEFTSWORD_SUCC);
		ResourceManager.loadImage(KIRBY_RIGHTSWORD_FLY);
		ResourceManager.loadImage(KIRBY_LEFTSWORD_FLY);
		ResourceManager.loadImage(KIRBY_RIGHTSWORD_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTSWORD_ATTACK);
		
		ResourceManager.loadImage(KIRBYWADDLE_RIGHT);
		ResourceManager.loadImage(KIRBYWADDLE_LEFT);
		ResourceManager.loadImage(KIRBYWADDLE_RIGHT_SUCC);
		ResourceManager.loadImage(KIRBYWADDLE_LEFT_SUCC);
		ResourceManager.loadImage(KIRBYWADDLE_RIGHT_FLY);
		ResourceManager.loadImage(KIRBYWADDLE_LEFT_FLY);
		ResourceManager.loadImage(KIRBYWADDLE_RIGHT_ATTACK);
		ResourceManager.loadImage(KIRBYWADDLE_LEFT_ATTACK);
		
		ResourceManager.loadImage(KIRBYTWIST_LEFT);
		ResourceManager.loadImage(KIRBYTWIST_RIGHT);
		ResourceManager.loadImage(KIRBYTWIST_RIGHT_SUCC);
		ResourceManager.loadImage(KIRBYTWIST_LEFT_SUCC);
		ResourceManager.loadImage(KIRBYTWIST_RIGHT_FLY);
		ResourceManager.loadImage(KIRBYTWIST_LEFT_FLY);
		ResourceManager.loadImage(KIRBYTWIST_ATTACK);
		
		ResourceManager.loadImage(KIRBYUFO_LEFT);
		ResourceManager.loadImage(KIRBYUFO_RIGHT);
		ResourceManager.loadImage(KIRBY_RIGHTUFO_ATTACK);
		ResourceManager.loadImage(KIRBY_LEFTUFO_ATTACK);
		ResourceManager.loadImage(KIRBY_BEAM_RSC);
		
		ResourceManager.loadImage(BRONTOBURT_LEFT);
		ResourceManager.loadImage(BRONTOBURT_RIGHT);
		
		ResourceManager.loadImage(BONKERS_LEFT);
		ResourceManager.loadImage(BONKERS_RIGHT);
		ResourceManager.loadImage(BONKERS_ATTACK_R);
		ResourceManager.loadImage(BONKERS_ATTACK_L);
		
		ResourceManager.loadImage(CAPPY_LEFT);
		ResourceManager.loadImage(CAPPY_RIGHT);
		
		ResourceManager.loadImage(HOTHEAD_LEFT);
		ResourceManager.loadImage(HOTHEAD_RIGHT);
		ResourceManager.loadImage(HOTHEAD_ATTACK_R);
		ResourceManager.loadImage(HOTHEAD_ATTACK_L);
		
		ResourceManager.loadImage(KIBBLE_LEFT);
		ResourceManager.loadImage(KIBBLE_RIGHT);
		ResourceManager.loadImage(KIBBLE_ATTACK_R);
		ResourceManager.loadImage(KIBBLE_ATTACK_L);
		
		ResourceManager.loadImage(KNUCKLE_LEFT);
		ResourceManager.loadImage(KNUCKLE_RIGHT);
		ResourceManager.loadImage(KNUCKLE_ATTACK_R);
		ResourceManager.loadImage(KNUCKLE_ATTACK_L);
		
		ResourceManager.loadImage(NODDY_LEFT);
		ResourceManager.loadImage(NODDY_RIGHT);
		
		ResourceManager.loadImage(POPPY_LEFT);
		ResourceManager.loadImage(POPPY_RIGHT);
		ResourceManager.loadImage(POPPY_ATTACK_R);
		ResourceManager.loadImage(POPPY_ATTACK_L);
		
		ResourceManager.loadImage(SCARFY_LEFT);
		ResourceManager.loadImage(SCARFY_RIGHT);
		
		ResourceManager.loadImage(SPARKY_LEFT);
		ResourceManager.loadImage(SPARKY_RIGHT);
		ResourceManager.loadImage(SPARKY_ATTACK_R);
		ResourceManager.loadImage(SPARKY_ATTACK_L);
		
		ResourceManager.loadImage(SWORDKNIGHT_LEFT);
		ResourceManager.loadImage(SWORDKNIGHT_RIGHT);
		ResourceManager.loadImage(SWORDKNIGHT_ATTACK_R);
		ResourceManager.loadImage(SWORDKNIGHT_ATTACK_L);
		
		ResourceManager.loadImage(TWISTER_LEFT);
		ResourceManager.loadImage(TWISTER_RIGHT);
		
		ResourceManager.loadImage(UFO_LEFT);
		ResourceManager.loadImage(UFO_RIGHT);
		ResourceManager.loadImage(UFO_ATTACK_R);
		ResourceManager.loadImage(UFO_ATTACK_L);
		
		ResourceManager.loadImage(UFO_BEAM_RSC);
		
		ResourceManager.loadImage(WADDLEDEE_LEFT);
		ResourceManager.loadImage(WADDLEDEE_RIGHT);
		
		ResourceManager.loadImage(WADDLEDOO_LEFT);
		ResourceManager.loadImage(WADDLEDOO_RIGHT);
		ResourceManager.loadImage(WADDLEDOO_ATTACK_R);
		ResourceManager.loadImage(WADDLEDOO_ATTACK_L);
		
		ResourceManager.loadImage(POACHER_LEFTIMG_RSC);
		
		ResourceManager.loadImage(TILE_IMG_RSC);
		
		ResourceManager.loadImage(STAR_IMG_RSC);
		
		ResourceManager.loadImage(VERTEX_IMG_RSC);
		
	}
	
	public void level1Setup() throws SlickException {
		map = new TiledMap("Kirby/resources/kirbymap.tmx","Kirby/resources/");
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		int port = 7777;
		try {
			//server = new KirbyServer(port);
			
			app = new AppGameContainer(new KirbyGame("Kirby"));
			app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			app.setVSync(true);
			app.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}