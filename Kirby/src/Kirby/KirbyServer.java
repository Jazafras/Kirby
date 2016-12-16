package Kirby;

import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.*;

public class KirbyServer implements Runnable{

	private CopyOnWriteArrayList<KirbyServerThread> clients;
	private int port;
	private ServerSocket server = null;
	private Socket socket = null;
	private Thread thread = null;
	static int clientCount = 0;

	
	public int screenWidth = 1280;
	public int screenHeight = screenWidth/16*9;
	
	
	public KirbyServer(int port){
		this.port = port;
		this.clients = new CopyOnWriteArrayList<>();
		try {
			server = new ServerSocket(this.port);
			if(thread == null){
				thread = new Thread(this);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		try {
			System.out.println("Binding port " + port + " to server.");
			System.out.println("server port is " + this.port);
			
			while(true){
				socket = server.accept();
				//socket = addThread(server);
				
				clientCount = clients.size();
				if(clientCount == 5){
					System.out.println("Can't have more than 4 clients.");
					socket.close();
					continue;
				}
				KirbyServerThread player = new KirbyServerThread(socket, clients, clientCount);
				clients.add(clientCount, player);
				new Thread(clients.get(clientCount)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void remove(int ID){
		clients.remove(ID);
	}
	
	public static Socket addThread(ServerSocket server) {
		Socket socketClient = null;
		try {
			socketClient = server.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
            System.exit(1);
		}
		return socketClient;
	}

}
