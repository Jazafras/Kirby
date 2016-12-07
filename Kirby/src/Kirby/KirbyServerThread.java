package Kirby;

import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.*;

public class KirbyServerThread implements Runnable{
	private KirbyServer server = null;
	private Socket socketReader = null;
	private Socket socketWriter = null;
	private int id;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	private Kirby kirby;
	private CopyOnWriteArrayList<KirbyServerThread> clients;
	
	public KirbyServerThread(Socket socket, CopyOnWriteArrayList<KirbyServerThread> clients, int id){
		super();
		this.socketWriter = socket;
		this.clients = clients;
		this.id = id;

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
		return this.id;
	}
	
	public void run(){
		getSocket();
		
		try {
			in = new DataInputStream(socketReader.getInputStream());
			out = new DataOutputStream(socketWriter.getOutputStream());
			while(true){
				String msg = in.readUTF();
				
				if(msg.equals("position")){
					this.kirby = new Kirby(128, 418);
					out.writeInt(clients.size());
					out.writeFloat(this.kirby.getX());
					out.writeFloat(this.kirby.getY());
					
					for(int i = 0; i < clients.size(); i++){
						KirbyServerThread client = clients.get(i);
						if(client.id == this.id){
							continue;
						}
						out.writeFloat(client.player().getX());
						out.writeFloat(client.player().getY());
					}
				}
				else if(msg.equals("connect")){
					this.kirby = new Kirby(128, 418);
					out.writeFloat(kirby.getX());
					out.writeFloat(kirby.getY());
				}
				else if(msg.equals("update")){
					float dX = in.readFloat();
					float dY = in.readFloat();
					for(int i = 0; i < clients.size(); i++){
						Kirby k = clients.get(i).player();
						if(k != this.player()){
							k.translate(dX, dY);
						}
					}
				}
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}
	
	public Kirby player(){
		return this.kirby;
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
	
	private void getSocket(){
		try {
			ServerSocket serverSocketReader = new ServerSocket(0);
			this.tellClient(serverSocketReader.getLocalPort());
			this.socketReader = KirbyServer.addThread(serverSocketReader);
		} catch (IOException e) {
			e.printStackTrace();
			this.socketReader = null;
		}
	}

}
