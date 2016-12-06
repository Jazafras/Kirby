package Kirby;

import java.net.*;
import java.io.*;

public class KirbyServerThread extends Thread{
	private KirbyServer server = null;
	private Socket socket = null;
	private int id = -1;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	public KirbyServerThread(KirbyServer server, Socket socket){
		super();
		this.server = server;
		this.socket = socket;
		id = socket.getPort();
	}
	public void tellClient(int output){
		try {
			out.writeInt(output);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			server.remove(id);
			stop();
		}
	}
	public int getID(){
		return id;
	}
	
	public void run(){
		System.out.println("Server Thread for Client " + id + " is running.");
		while(true){
			try {
				server.clientHandler(id, in.readUTF()); //edit this so it can handle ints as well
			} catch (IOException e) {
				e.printStackTrace();
				server.remove(id);
				stop();
			}
		}
	}
	public void open() throws IOException{
		in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	
	public void close() throws IOException{
		if(socket != null){
			socket.close();
		}
		if(in != null){
			in.close();
		}
		if(out != null){
			out.close();
		}
	}
}
