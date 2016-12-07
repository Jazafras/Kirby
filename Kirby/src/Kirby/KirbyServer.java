package Kirby;

import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.*;

public class KirbyServer implements Runnable{

	private CopyOnWriteArrayList<KirbyServerThread> clients;
	public int port;
	private ServerSocket server = null;
	private Socket socket = null;
	private Thread thread = null;
	static int clientCount = 0;

	public static final int PORT = 7777;
	
	public int screenWidth = 1280;
	public int screenHeight = screenWidth/16*9;
	
	public KirbyServer(int port){
		this.port = port;
		this.clients = new CopyOnWriteArrayList<>();

	}
	
	public void run(){
		try {
			System.out.println("Binding port " + port + " to server.");
			server = new ServerSocket(port);
			while(thread != null){
				socket = addThread(server);
				
				clientCount = clients.size();
				if(clientCount == 5){
					System.out.println("Can't have more than 4 clients.");
					socket.close();
					continue;
				}
				KirbyServerThread player = new KirbyServerThread(this, socket);
				clients.add(clientCount, player);
				start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void start() {
		if(thread == null){
			thread = new Thread(clients.get(clientCount));
			thread.start();
		}
	}
	public void stop() {
		if(thread != null){
			thread.stop();
			thread = null;
		}
	}
/*
	public synchronized void clientHandler(String input){
		 items to be synchronized:
		amount of lives (int)
		xOffset (float)
		kirby's position (vector) (int, int)
		kirby's velocity (vector) (int, int)
		jumps (int)
		floating (boolean)
		maxFallSpeed (float)
		key pressed (int)
		
		
		if(input.equals("lives")){
			//clients[].tellClient(PlayingState.amountLives());
		}
		else if(input.equals("xOffset")){
			
		}
		else if(input.equals("position")){
			try {
				out.writeInt(clientCount);
				out.writeFloat(x);
				out.writeFloat(y);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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
	*/
	public synchronized void remove(int ID){
		clients.remove(ID);
	}
	
	public static Socket addThread(ServerSocket server) {
		Socket socketClient = null;
		try {
			socketClient = server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socketClient;
	}

	
}
