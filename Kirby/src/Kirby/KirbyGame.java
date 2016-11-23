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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class KirbyGame extends StateBasedGame {
	
	// states of the game
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	
	// background
	public static final String BACKGROUND_IMG_RSC = "Kirby/resources/background.png";
	public static final String STARTUP_IMG_RSC = "Kirby/resources/startup.png";
	
	// all entity images used in game
	public static final String TIGRESS_LEFTIMG_RSC = "Kirby/resources/tigress-left.png";
	public static final String TIGRESS_RIGHTIMG_RSC = "Kirby/resources/tigress-right.png";
	public static final String TIGRESS_FRONTIMG_RSC = "Kirby/resources/tigress-front.png";
	public static final String TIGRESS_BACKIMG_RSC = "Kirby/resources/tigress-back.png";
	
	public static final String TIGRESS_HOLDING_LEFTIMG_RSC = "Kirby/resources/tigress-holding-left.png";
	public static final String TIGRESS_HOLDING_RIGHTIMG_RSC = "Kirby/resources/tigress-holding-right.png";
	public static final String TIGRESS_HOLDING_FRONTIMG_RSC = "Kirby/resources/tigress-holding-front.png";
	public static final String TIGRESS_HOLDING_BACKIMG_RSC = "Kirby/resources/tigress-holding-back.png";
	
	//Sword Knight images
	public static final String SWORDKNIGHT_LEFT = "Kirby/resources/bladeknight_left.png";
	public static final String SWORDKNIGHT_RIGHT = "Kirby/resources/bladeknight_right.png";
	public static final String SWORDKNIGHT_ATTACK_R = "Kirby/resources/bladeknight_attack_right.png";
	public static final String SWORDKNIGHT_ATTACK_L = "Kirby/resources/bladeknight_attack_left.png";
	
	//Noddy images
	public static final String NODDY_LEFT = "Kirby/resources/noddy_left.png";
	public static final String NODDY_RIGHT = "Kirby/resources/noddy_right.png";
	
	public static final String CUB_IMG_RSC = "Kirby/resources/cub.png";
	
	public static final String POACHER_LEFTIMG_RSC = "Kirby/resources/poacher-left.png";
	
	public static final String FLOWER_IMG_RSC = "Kirby/resources/flower.png";
	public static final String MEAT_IMG_RSC = "Kirby/resources/meat.png";
	public static final String UNDERBRUSH_IMG_RSC = "Kirby/resources/underbrush.png";
	public static final String NEST_IMG_RSC = "Kirby/resources/nest.png";
	public static final String VERTEX_IMG_RSC = "Kirby/resources/vertex-r.png";
	
	//public static final String HITWALL_RSC = "bounce/resource/wall_hit.wav";

	public final int ScreenWidth;
	public final int ScreenHeight;
	
	int level;
	Tigress tigress;
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
	public KirbyGame(String title, int width, int height) {
		super(title);
		
		ScreenHeight = height;
		ScreenWidth = width;

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
		
		tigress = new Tigress(ScreenWidth - 50, ScreenHeight - 50);
		poacher = new Poacher(50, 50, vPos.get(new Vertex(50, 50).toString()));
		nest = new Nest(ScreenWidth - 85, 60);
		
	}
	
	private void loadImages() {
		//ResourceManager.loadSound(HITWALL_RSC);	
		
		ResourceManager.loadImage(BACKGROUND_IMG_RSC);
		ResourceManager.loadImage(STARTUP_IMG_RSC);
		
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
	
	private void createVertices() {
		vPos.clear();
		vertices.clear();
		
		// create vertices on graph for path finding
		for (int i = 1; i < 16; i++) {
			for (int j = 1; j < 12; j++) {
				Vertex v = new Vertex(i * 50, j * 50);
				
				boolean collides = false;
				for (Underbrush u : underbrushes) {
					if (v.collides(u) != null)
						collides = true;
				}
				if (!collides) {
					vertices.add(v);
					vPos.put(v.toString(), v);
				}
			}
		}
		
		// adding the neighbor nodes to each vertex
		for (Vertex v : vertices) {
			Vertex left = new Vertex(v.getX() - 50, v.getY());
			Vertex right = new Vertex(v.getX() + 50, v.getY());
			Vertex above = new Vertex(v.getX(), v.getY() - 50);
			Vertex below = new Vertex(v.getX(), v.getY() + 50);
			if (vPos.containsKey(left.toString()))
				v.addNeighbors(vPos.get(left.toString()), "left");
			if (vPos.containsKey(right.toString()))
				v.addNeighbors(vPos.get(right.toString()), "right");
			if (vPos.containsKey(above.toString()))
				v.addNeighbors(vPos.get(above.toString()), "above");
			if (vPos.containsKey(below.toString()))
				v.addNeighbors(vPos.get(below.toString()), "below");			
		}
	}
	
	public void level1Setup() {
		for (int i = 0; i <= 4; i++)
			underbrushes.add(new Underbrush(300, ScreenHeight - (i * 50)));
		for (int i = 1; i <= 3; i++)
			underbrushes.add(new Underbrush(300 - (i*50), ScreenHeight - (4*50)));
		for (int i = 0; i <= 6; i++)
			underbrushes.add(new Underbrush(600, i*50));
		for (int i = 1; i <= 4; i++)
			underbrushes.add(new Underbrush(600 - i * 50, 3*50));
		for (int i = 0; i <= 4; i++)
			underbrushes.add(new Underbrush(150, i * 50));
		for (int i = 0; i <= 3; i++)
			underbrushes.add(new Underbrush(450, ScreenHeight - i * 50));
		for (int i = 1; i <= 4; i++)
			underbrushes.add(new Underbrush(450 + i * 50, ScreenHeight - 3*50));
		
		createVertices();
		
		cubs.add(new Cub(50, 550));
		cubs.add(new Cub(400, 50));
		cubs.add(new Cub(250, 300));

	}
	
	public void level2Setup() {
		underbrushes.clear();
		for (int i = 0; i <= 4; i++)
			underbrushes.add(new Underbrush(i * 50, 200));
		for (int i = 1; i <= 1; i++)
			underbrushes.add(new Underbrush(4*50, 200 - (i*50)));
		for (int i = 0; i <= 4; i++)
			underbrushes.add(new Underbrush(150, ScreenHeight - i*50));
		for (int i = 1; i <= 3; i++)
			underbrushes.add(new Underbrush(150 + i * 50, ScreenHeight - 4*50));
		for (int i = 0; i <= 4; i++)
			underbrushes.add(new Underbrush(550, ScreenHeight - i*50));
		for (int i = 2; i <= 7; i++)
			underbrushes.add(new Underbrush(ScreenWidth - i * 50, 200));
		for (int i = 0; i <= 1; i++)
			underbrushes.add(new Underbrush(500, i*50));
		
		createVertices();
		
		cubs.add(new Cub(50, 400));
		cubs.add(new Cub(450, 50));
		cubs.add(new Cub(400, 350));
		cubs.add(new Cub(500, 550));
		
	}
	
	public void level3Setup() {
		underbrushes.clear();
		// create all underbrush
		for (int i = 0; i <= 2; i++)
			underbrushes.add(new Underbrush(i * 50, 200));
		for (int i = 0; i <= 8; i++)
			underbrushes.add(new Underbrush(i * 50, ScreenHeight - 3*50));
		for (int i = 1; i <= 4; i++)
			underbrushes.add(new Underbrush(5 * 50, ScreenHeight - 3*50 - i * 50));
		for (int i = 0; i <= 1; i++)
			underbrushes.add(new Underbrush(250, i * 50));
		for (int i = 0; i <= 2; i++)
			underbrushes.add(new Underbrush(i * 50, 200));
		for (int i = 0; i <= 5; i++)
			underbrushes.add(new Underbrush(ScreenWidth - 3 * 50, ScreenHeight - i * 50));
		for (int i = 0; i <= 5; i++)
			underbrushes.add(new Underbrush(450, i * 50));
		for (int i = 0; i <= 2; i++)
			underbrushes.add(new Underbrush(450 + i * 50, 200));
		
		createVertices();
		
		cubs.add(new Cub(400, 550));
		cubs.add(new Cub(400, 50));
		cubs.add(new Cub(50, 300));
		cubs.add(new Cub(600, 350));
		cubs.add(new Cub(300, 400));
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new KirbyGame("Tigress", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	
}