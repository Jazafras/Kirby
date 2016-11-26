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
	
	public static final String KIRBY_LEFTIMG_RSC = "Kirby/resources/kirby-sprite.png";
	
	// all entity images used in game
	public static final String TIGRESS_LEFTIMG_RSC = "Kirby/resources/tigress-left.png";
	public static final String TIGRESS_RIGHTIMG_RSC = "Kirby/resources/tigress-right.png";
	public static final String TIGRESS_FRONTIMG_RSC = "Kirby/resources/tigress-front.png";
	public static final String TIGRESS_BACKIMG_RSC = "Kirby/resources/tigress-back.png";
	
	public static final String TIGRESS_HOLDING_LEFTIMG_RSC = "Kirby/resources/tigress-holding-left.png";
	public static final String TIGRESS_HOLDING_RIGHTIMG_RSC = "Kirby/resources/tigress-holding-right.png";
	public static final String TIGRESS_HOLDING_FRONTIMG_RSC = "Kirby/resources/tigress-holding-front.png";
	public static final String TIGRESS_HOLDING_BACKIMG_RSC = "Kirby/resources/tigress-holding-back.png";
	
	public static final String CUB_IMG_RSC = "Kirby/resources/cub.png";
	
	public static final String POACHER_LEFTIMG_RSC = "Kirby/resources/poacher-left.png";
	
	public static final String FLOWER_IMG_RSC = "Kirby/resources/flower.png";
	public static final String MEAT_IMG_RSC = "Kirby/resources/meat.png";
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
	ArrayList<Flower> flowers;
	ArrayList<Meat> meats;	
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
		flowers = new ArrayList<Flower>();
		meats = new ArrayList<Meat>();
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
		poacher = new Poacher(50, 50, vPos.get(new Vertex(50, 50).toString()));
		nest = new Nest(SCREEN_WIDTH - 85, 60);
		
	}
	
	private void loadImages() {
		//ResourceManager.loadSound(HITWALL_RSC);	
		
		ResourceManager.loadImage(BACKGROUND_IMG_RSC);
		ResourceManager.loadImage(STARTUP_IMG_RSC);
		
		ResourceManager.loadImage(KIRBY_LEFTIMG_RSC);
		
		ResourceManager.loadImage(TIGRESS_LEFTIMG_RSC);
		ResourceManager.loadImage(TIGRESS_RIGHTIMG_RSC);
		ResourceManager.loadImage(TIGRESS_FRONTIMG_RSC);
		ResourceManager.loadImage(TIGRESS_BACKIMG_RSC);
		
		ResourceManager.loadImage(TIGRESS_HOLDING_LEFTIMG_RSC);
		ResourceManager.loadImage(TIGRESS_HOLDING_RIGHTIMG_RSC);
		ResourceManager.loadImage(TIGRESS_HOLDING_FRONTIMG_RSC);
		ResourceManager.loadImage(TIGRESS_HOLDING_BACKIMG_RSC);
		
		ResourceManager.loadImage(CUB_IMG_RSC);
		
		ResourceManager.loadImage(POACHER_LEFTIMG_RSC);
		
		ResourceManager.loadImage(FLOWER_IMG_RSC);
		ResourceManager.loadImage(MEAT_IMG_RSC);
		ResourceManager.loadImage(UNDERBRUSH_IMG_RSC);
		ResourceManager.loadImage(NEST_IMG_RSC);
		ResourceManager.loadImage(VERTEX_IMG_RSC);
	}
	
	public void level1Setup() throws SlickException {
		map = new TiledMap("Kirby/resources/level_0.tmx","Kirby/resources/");
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