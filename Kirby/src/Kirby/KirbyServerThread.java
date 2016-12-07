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
	
	public KirbyServerThread(KirbyServer server, Socket socket){
		super();
		this.server = server;
		this.socketWriter = socket;
		id = socket.getPort();

	}
	public void tellClient(int output){
		try {
			out = new DataOutputStream(socketWriter.getOutputStream());
			out.writeInt(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		getSocket();
		
		try {
			in = new DataInputStream(socketReader.getInputStream());
			out = new DataOutputStream(socketWriter.getOutputStream());
			while(true){
				String msg = in.readUTF();
				
				if(msg.equals("position")){
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
					kirby = new Kirby(418, 418);
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
		return kirby;
	}
	
	CopyOnWriteArrayList<KirbyServerThread> getClients() { 
		return clients; 
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
		}
	}

}
