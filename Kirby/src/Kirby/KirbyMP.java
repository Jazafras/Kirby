package Kirby;

import java.io.*;

public class KirbyMP extends Kirby implements Runnable{

	DataOutputStream out;
	DataInputStream in;
	KirbyMP[] player = new KirbyMP[10];
	String name;
	int playerid;
	
	int playeridIN;
	int xIN;
	int yIN;
	
	public KirbyMP(DataOutputStream out, DataInputStream in, KirbyMP[] players, int pid, float x, float y) {
		super(x, y);
		this.out = out;
		this.in = in;
		this.player = players;
		this.playerid = pid;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try{
			out.writeInt(playerid);
		}
		catch(IOException e1){
		}
		while(true){
			try{
				playeridIN = in.readInt();
				xIN = in.readInt();
				yIN = in.readInt();
				for(int i = 0; i < 10; i++){
					if(player[i] != null){
						player[i].out.writeInt(playeridIN);
						player[i].out.writeInt(xIN);
						player[i].out.writeInt(yIN);
					}
				}
			}catch(IOException e){
				player[playerid] = null;
			}
		}
		
	}
	
	
}
