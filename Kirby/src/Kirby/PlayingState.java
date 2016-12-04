package Kirby;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

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
 * This state is active when the Game is being played. In this state, sound is
 * turned on, the bounce counter begins at 0 and increases until 10 at which
 * point a transition to the Game Over state is initiated. The user can also
 * control the ball using the WAS & D keys.
 * Transitions From StartUpState
 * Transitions To GameOverState
 */
class PlayingState extends BasicGameState {
	
	public static final int GROUND = 0;
	public static final int AIR = 1;
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	public static final float gravity = 0.0015f;
	
	int lives;
	Image background;
	Tile[][] tileMap;
	Set<Tile> groundTiles;
	Map<String, Tile> tileFetch;
	int topX;
	int topY;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		KirbyGame bg = (KirbyGame)game;
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
		float yOffset = getYOffset(bg);
		topX = (int)(-1 * (xOffset % 32));
		topY = (int)(-1 * (yOffset % 32));
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		KirbyGame bg = (KirbyGame)game;
		
		float xOffset = getXOffset(bg);
		float yOffset = getYOffset(bg);
		
		topX = (int)(-1 * (xOffset % 32));
		topY = (int)(-1 * (yOffset % 32));
		
        background.draw(xOffset * (background.getWidth() - KirbyGame.SCREEN_WIDTH) / 
        		(bg.map.getWidth() * 32 - KirbyGame.SCREEN_WIDTH) * -1.f, 
        		yOffset * (background.getHeight() - KirbyGame.SCREEN_HEIGHT) / 
        		(bg.map.getHeight() * 32 - KirbyGame.SCREEN_HEIGHT) * -1.f);
		
		bg.map.render((int)(-1 * (xOffset % 32)), (int)(-1 * (yOffset % 32)), 
				(int)(xOffset / 32), (int)(yOffset / 32), bg.SCREEN_WIDTH / 32, bg.SCREEN_HEIGHT / 32);
		
		bg.kirby.render(g, xOffset, yOffset);
		
		for (Tile t : bg.kirby.surroundingTiles(tileMap))
			t.render(g, xOffset, yOffset);
		for (Tile t : bg.kirby.getGroundTiles(tileMap))
			t.render(g, xOffset, yOffset);
		
		/*for (int i = 0; i < tileMap.length; i++) {
			for (int j = 0; j < tileMap[i].length; j++) {
				if (tileMap[i][j].getType() == GROUND) {
					tileMap[i][j].render(g, 0, yOffset);
				}
			}
		}*/
		
		
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
		
		float kXOffset = getXOffset(bg);
		float kYOffset = getYOffset(bg);
		
		/*for (int i = 0; i < tileMap.length; i++) {
			for (int j = 0; j < tileMap[i].length; j++) {
				tileMap[i][j].setPosition(tileMap[i][j].getX() + (kXOffset / 32), tileMap[i][j].getY());
			}
		}*/
		
		// kirby collision with cubs
		Set<Tile> kirbyOccupying = bg.kirby.surroundingTiles(tileMap);
		Set<Tile> kirbyGround = bg.kirby.getGroundTiles(tileMap);
		
		System.out.println(bg.kirby.getVelocity().getY());
		
		int move = -1;
		for (Tile t : kirbyOccupying) {
			if (t.getType() == GROUND && !kirbyGround.contains(t)) {
				if (bg.kirby.getVelocity().getX() < 0) {
					System.out.println("left collision");
					move = LEFT;
				} else if (bg.kirby.getVelocity().getX() > 0){
					move = RIGHT;
					System.out.println("right collision");
				}
				break;
			}
		}
		
		keyPresses(input, bg, delta, move);
		checkLives(game, bg);
		
		/*System.out.println(bg.kirby.toString());
		System.out.print("surrounding tiles ("+bg.kirby.surroundingTiles(tileMap).size()+") : ");
		for (Tile t : bg.kirby.surroundingTiles(tileMap))
			System.out.print(t.toString() + ", ");
		System.out.println();
		System.out.print("ground tiles ("+bg.kirby.getGroundTiles(tileMap).size()+"): ");
		for (Tile t : bg.kirby.getGroundTiles(tileMap))
			System.out.print(t.toString() + ", ");
		System.out.println();
		System.out.println();*/
		
		if (!bg.kirby.isOnGround(tileMap) || bg.kirby.getVelocity().getY() < 0) {
			//System.out.println("gravity " + bg.kirby.getVelocity().getY() + " " + !bg.kirby.isOnGround(tileMap));
			//System.out.println("kirby pos " + bg.kirby.getY());
			//for (Tile t : bg.kirby.getGroundTiles(tileMap))
			//	System.out.println(t.toString());
	     	bg.kirby.applyGravity(gravity * delta, tileMap);
		} else {
			bg.kirby.setVelocity(new Vector(bg.kirby.getVelocity().getX(), 0.f));
		}
		
		
		
		bg.kirby.update(delta);
		//handleGameObject(bg.kirby, delta);
	}
	
	private void keyPresses(Input input, KirbyGame bg, int delta, int move) {	
		// System.out.println(move);
		// Control user input
		if (input.isKeyDown(Input.KEY_LEFT) && move != LEFT) {
			bg.kirby.setVelocity(new Vector(-.2f, 0));
		} else if (input.isKeyDown(Input.KEY_RIGHT) && move != RIGHT) { 
			bg.kirby.setVelocity(new Vector(.2f, 0f));
		/*else if (input.isKeyDown(Input.KEY_UP) && (move == null || move.getY() <= 0)) 
			bg.kirby.setVelocity(new Vector(0f, -.2f));
		else if (input.isKeyDown(Input.KEY_DOWN) && (move == null || move.getY() >= 0)) 
			bg.kirby.setVelocity(new Vector(0f, .2f));*/
		} else if (input.isKeyDown(Input.KEY_SPACE)) {
			bg.kirby.jump(tileMap);
		} else 
			bg.kirby.setVelocity(new Vector(0.f, bg.kirby.getVelocity().getY()));
		
		
		/*if (move == LEFT)
			bg.kirby.moveRight(delta, .3f);
		else if (move == RIGHT)
			bg.kirby.moveLeft(delta, .3f);*/
		
		// if space pressed, kirby drops cub
		
		
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
					t = new Tile(topX + i*32 + 16, topY + j*32 + 16, GROUND);
					groundTiles.add(t);
				} else {
					t = new Tile(topX + 16 + i*32, topY + j*32 + 16, AIR);
				}
				tileFetch.put(t.toString(), t);
				tileMap[i][j] = t;
			}
		}
		
		System.out.println("kirby " + bg.kirby.getY());
		for (String t : tileFetch.keySet())
			System.out.println(t);
	}
	
    /*private void handleGameObject(MovingEntity e, int delta){
        
        //first update the onGround of the object
        e.setOnGround(e.isOnGround(tileMap));
        
        //now apply gravitational force if we are not on the ground or when we are about to jump
        if(!e.isOnGround(tileMap) || e.getVelocity().getY() < 0)
            e.applyGravity(gravity * delta);
        else
        	e.setVelocity(new Vector(e.getVelocity().getX(), 0.f));
        
        //calculate how much we actually have to move
        float x_movement = e.getVelocity().getX()*delta;
        float y_movement   = e.getVelocity().getY()*delta;
        
        //we have to calculate the step we have to take
        float step_y = 0;
        float step_x = 0;
        
        if(x_movement != 0){
            step_y = Math.abs(y_movement)/Math.abs(x_movement);
            if(y_movement < 0)
                step_y = -step_y;
            
            if(x_movement > 0)
                step_x = 1;
            else
                step_x = -1;
            
            if((step_y > 1 || step_y < -1) && step_y != 0){
                step_x = Math.abs(step_x)/Math.abs(step_y);
                if(x_movement < 0)
                    step_x = -step_x;
                if(y_movement < 0)
                    step_y = -1;
                else
                    step_y = 1;
            }
        }else if(y_movement != 0){
            //if we only have vertical movement, we can just use a step of 1
            if(y_movement > 0)
                step_y = 1;
            else
                step_y = -1;
        }
        
        //and then do little steps until we are done moving
        while(x_movement != 0 || y_movement != 0){
            
            //we first move in the x direction
            if(x_movement != 0){
                //when we do a step, we have to update the amount we have to move after this
                if((x_movement > 0 && x_movement < step_x) || (x_movement > step_x  && x_movement < 0)){
                    step_x = x_movement;
                    x_movement = 0;
                }else
                    x_movement -= step_x;
                
                //then we move the object one step
                e.setPosition(e.getX()+step_x,e.getY());
                //obj.setX(obj.getX()+step_x);
                
                //if we collide with any of the bounding shapes of the tiles we have to revert to our original position
                if(e.checkCollision(tileMap)){
                    
                   //undo our step, and set the velocity and amount we still have to move to 0, because we can't move in that direction
                	e.setPosition(e.getX()-step_x,e.getY());
                	e.setVelocity(new Vector(0.f, e.getVelocity().getY()));
                    x_movement = 0;
                }
                
            }
            //same thing for the vertical, or y movement
            if(y_movement != 0){
                if((y_movement > 0 && y_movement < step_y) || (y_movement > step_y  && y_movement < 0)){
                    step_y = y_movement;
                    y_movement = 0;
                }else
                    y_movement -= step_y;
                
                e.setPosition(e.getX(), e.getY()+step_y);
                
                if(e.checkCollision(tileMap)){
                	e.setPosition(e.getX(), e.getY()-step_y);
                	e.setVelocity(new Vector(e.getVelocity().getX(), 0.f));
                    y_movement = 0;
                    break;
                }
            }
        }
    }*/

	@Override
	public int getID() {
		return KirbyGame.PLAYINGSTATE;
	}
	
}