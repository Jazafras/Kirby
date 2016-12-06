package Kirby;

import java.net.*;
import java.io.*;

public class KirbyServer implements Runnable{
	private KirbyServerThread clients[] = new KirbyServerThread[10];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;
   
	public KirbyServer(int port){
		try {
			System.out.println("Binding port " + port + " to server.");
			server = new ServerSocket(port);
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(thread != null){
			try {
				addThread(server.accept());
			} catch (IOException e) {
				e.printStackTrace();
				stop();
			}
		}
	}
	public void start() {
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	public void stop() {
		if(thread != null){
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID){
		for(int i = 0; i < clientCount; i++){
			if(clients[i].getID() == ID){
				return i;
			}
		}
		return -1;
	}
	
	public synchronized void clientHandler(int ID, String input){
		/* items to be synchronized:
		amount of lives (int)
		xOffset (float)
		kirby's position (vector) (int, int)
		kirby's velocity (vector) (int, int)
		jumps (int)
		floating (boolean)
		maxFallSpeed (float)
		key pressed (int)
		*/
		
		if(input.equals("lives")){
			clients[findClient(ID)].tellClient(PlayingState.amountLives());
		}
		else if(input.equals("xOffset")){
			
		}
		else if(input.equals("position")){
			
		}
		else if(input.equals("velocity")){
			
		}
		else if(input.equals("jumps")){
			
		}
		else if(input.equals("floating")){
			
		}
		else if(input.equals("fallSpeed")){
			
		}
		else if(input.equals("keyPress")){
			
		}
	}
	
	public synchronized void remove(int ID){
		int pos = findClient(ID);
		if(pos >= 0){
			KirbyServerThread toTerminate = clients[pos];
			System.out.println("Removing client thread " + ID + " at " + pos);
	        if (pos < clientCount - 1){
	        	for(int i = pos + 1; i < clientCount; i++){
	        		clients[i-1] = clients[i];
	        	}
	        }
	        clientCount--;
	        try {
				toTerminate.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        toTerminate.stop();
		}
	}
	
	private void addThread(Socket socket) {
		if(clientCount < clients.length){
			clients[clientCount] = new KirbyServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Client refused: maximum " + clients.length + " reached.");
		}
	}
	/*
	public static void main(String args[]){
		KirbyServer server = null;
		if (args.length != 1){
			System.out.println("Usage: java KirbyServer port");
		}
		else{
			server = new KirbyServer(Integer.parseInt(args[0]));
		}
	}
	 */
}
