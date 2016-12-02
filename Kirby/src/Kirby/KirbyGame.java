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
	
	// states of the game
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	
	// background
	public static final String BACKGROUND_IMG_RSC = "Kirby/resources/background.png";
	public static final String STARTUP_IMG_RSC = "Kirby/resources/startup.png";
	
	/***Kirby images***/
	//default
	public static final String KIRBY_LEFTIMG_RSC = "Kirby/resources/kirby_regular_left.png";
	public static final String KIRBY_RIGHTIMG_RSC = "Kirby/resources/kirby_regular_right.png";
	public static final String KIRBY_RIGHT_SUCC = "Kirby/resources/kirby_right_succ.png";
	public static final String KIRBY_LEFT_SUCC = "Kirby/resources/kirby_left_succ.png";
	public static final String KIRBY_RIGHT_FLY = "Kirby/resources/kirby_right_fly.png";
	public static final String KIRBY_LEFT_FLY = "Kirby/resources/kirby_left_fly.png";
	
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
	
	//WaddleDee images
	public static final String WADDLEDEE_LEFT = "Kirby/resources/waddledee_left.png";
	public static final String WADDLEDEE_RIGHT = "Kirby/resources/waddledee_right.png";
		
	//WaddleDoo images
	public static final String WADDLEDOO_LEFT = "Kirby/resources/waddledoo_left.png";
	public static final String WADDLEDOO_RIGHT = "Kirby/resources/waddledoo_right.png";
	public static final String WADDLEDOO_ATTACK_R = "Kirby/resources/waddledoo_attackR.png";
	public static final String WADDLEDOO_ATTACK_L = "Kirby/resources/waddledoo_attackL.png";	
	
	public static final String CUB_IMG_RSC = "Kirby/resources/cub.png";
	
	public static final String POACHER_LEFTIMG_RSC = "Kirby/resources/poacher-left.png";
	
	public static final String UNDERBRUSH_IMG_RSC = "Kirby/resources/underbrush.png";
	public static final String NEST_IMG_RSC = "Kirby/resources/nest.png";
	public static final String VERTEX_IMG_RSC = "Kirby/resources/vertex-r.png";
	
	//public static final String HITWALL_RSC = "bounce/resource/wall_hit.wav";
	
    public static final int SCREEN_WIDTH  = 1280;
    public static final int SCREEN_HEIGHT = SCREEN_WIDTH / 16 * 9;
    //public static final float SCALE = (float) (1.25*((double)SCREEN_WIDTH/1280));
	
	TiledMap map;
	
	int level;
	Kirby kirby;
	Poacher poacher;
	ArrayList<Cub> cubs;
	Set<Underbrush> underbrushes;
	Set<Vertex> vertices;
	Map<String, Vertex> vPos;
	Nest nest;
	
	/**
	 * Create the TigressGame frame, saving the width and height for later use.
	 * @param title: the window's title
	 * @param width: the window's width
	 * @param height: the window's height
	 */
	public KirbyGame(String title) {
		super(title);

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		cubs = new ArrayList<Cub>();
		underbrushes = new HashSet<Underbrush>();
		vertices = new HashSet<Vertex>();
		vPos = new HashMap<String, Vertex>();
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		loadImages();
		
		level = 1;
		
		level1Setup();
		
		kirby = new Kirby(128, 418);
		poacher = new Poacher(128, 418, vPos.get(new Vertex(50, 50).toString()));
		//bonkers = new Bonkers();
		//brontoburt = new Brontoburt();
		//cappy = new Cappy();
		//hothead = new HotHead();
		//knucklejoe = new KnuckleJoe();
		//Noddy = new noddy();
		//poppyjr = new PoppyJr();
		//sirkibble = new SirKibble();
		//sparky = new Sparky();
		//swordknight = new SwordKnight();
		//twister = new Twister();
		//ufo = new UFO();
		//waddledee = new WaddleDee();
	}
	
	private void loadImages() {
		ResourceManager.loadImage(BACKGROUND_IMG_RSC);
		ResourceManager.loadImage(STARTUP_IMG_RSC);
		
		ResourceManager.loadImage(KIRBY_LEFTIMG_RSC);
		ResourceManager.loadImage(KIRBY_RIGHTIMG_RSC);
		

		
		ResourceManager.loadImage(CUB_IMG_RSC);
		
		ResourceManager.loadImage(POACHER_LEFTIMG_RSC);
		
		ResourceManager.loadImage(UNDERBRUSH_IMG_RSC);
		ResourceManager.loadImage(NEST_IMG_RSC);
		ResourceManager.loadImage(VERTEX_IMG_RSC);
	}
	
	public void level1Setup() throws SlickException {
		map = new TiledMap("Kirby/resources/kirbymap.tmx","Kirby/resources/");
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new KirbyGame("Kirby"));
			app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	
}