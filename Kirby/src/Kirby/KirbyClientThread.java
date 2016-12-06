package Kirby;

import java.net.*;
import java.io.*;

public class KirbyClientThread extends Thread{
	
	private Socket socket = null;
	private KirbyClient client = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	public KirbyClientThread(KirbyClient client, Socket socket){
		this.client = client;
		this.socket = socket;
		open();
		start();
	}

	public void open() {
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			client.stop();
		}
	}
	
	public void close(){
		try {
			if(in != null){
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				client.tellServer(in.readInt());
			} catch (IOException e) {
				e.printStackTrace();
				client.stop();
			}
		}
	}

}
