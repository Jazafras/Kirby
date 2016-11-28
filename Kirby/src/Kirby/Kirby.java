package Kirby

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Kirby extends MovingEntity {
	
	private boolean startState;
	private boolean swordState;
	private boolean state2;
	private boolean state3;
	
	public Kirby(final float x, final float y)
	{
		super(x,y);
		setVelocity(new Vector(0,0));
		startState = true;
		swordState = false;
		state2 = false;
		state3 = false;
		
	}
	
	public Kirby succ(){
		return succ;
		
	}
	
	public void swallow(){
		return swallow;
		//if entity = sword
		//switch to sword state
		
	}
	
	public void spit(){
		return spit;
		
	}

}
