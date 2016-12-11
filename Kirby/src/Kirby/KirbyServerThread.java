package Kirby;

import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.*;

public class KirbyServerThread implements Runnable{
	private Socket socketReader;
	private Socket socketWriter = null;
	private int ID;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	private Kirby kirby;
	private CopyOnWriteArrayList<KirbyServerThread> clients;
	
	public KirbyServerThread(Socket socket, CopyOnWriteArrayList<KirbyServerThread> clientList, int id){
		super();
		socketWriter = socket;
		clients = clientList;
		ID = id;
		socketReader = null;

	}
	public void tellClient(int output){
		try {
			out = new DataOutputStream(socketWriter.getOutputStream());
			out.writeInt(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int getID(){
		return ID;
	}
	
	public void run(){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocketReader = new ServerSocket(0);
			this.tellClient(serverSocketReader.getLocalPort());
			this.socketReader = serverSocketReader.accept();
		} catch (IOException e) {
			e.printStackTrace();
			this.socketReader = null;
		}
		
		try {
			in = new DataInputStream(socketReader.getInputStream());
			out = new DataOutputStream(socketWriter.getOutputStream());
			while(true){
				String msg = in.readUTF();
				
				if(msg.equals("position")){
					out.writeInt(clients.size());
					out.writeFloat(this.player().getX());
					out.writeFloat(this.player().getY());
					
					for(int i = 0; i < clients.size(); i++){
						KirbyServerThread client = clients.get(i);
						
						if(client.ID == this.ID){
							continue;
						}
						clients.get(i).kirby = new Kirby(128, 418);
						out.writeFloat(client.player().getX());
						out.writeFloat(client.player().getY());
					}
				}
				else if(msg.equals("connect")){
					kirby = new Kirby(128, 418);
					out.writeFloat(kirby.getX());
					out.writeFloat(kirby.getY());
				}
				else if(msg.equals("update")){
					out.writeInt(clients.size());
					System.out.println("client size is " + clients.size());
					for(int i = 0; i < clients.size(); i++){
						System.out.println("client " + i + " is " + clients.get(i));
						System.out.println("client " + i + "'s kirby is " + clients.get(i).kirby);
						out.writeFloat(clients.get(i).kirby.getX());
						out.writeFloat(clients.get(i).kirby.getY());
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}
	
	public Kirby player(){
		return kirby;
	}
	
	CopyOnWriteArrayList<KirbyServerThread> getClients() { 
		return this.clients; 
	}
	
	public void close() throws IOException{
		if(socketReader != null){
			socketReader.close();
		}
		if(socketWriter != null){
			socketWriter.close();
		}
	}
	

}
