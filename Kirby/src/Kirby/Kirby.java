package Kirby

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Kirby extends MovingEntity {
	
	private boolean startState;
	private boolean state1;
	private boolean state2;
	private boolean state3;
	
	public Kirby(final float x, final float y)
	{
		super(x,y);
		setVelocity(new Vector(0,0));
		startState = true;
		state1 = false;
		state2 = false;
		state3 = false;
		
	}
	
	public void succ(){
		
	}
	
	public void swallow(){
		
	}
	
	public void spit(){
		
	}

}
