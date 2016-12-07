package Kirby;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Input;

import java.io.*;

public class KirbyClient{

	private Socket socketReader = null;
	private Socket socketWriter = null;
	private DataInputStream reader = null;
	private DataOutputStream writer = null;
	int port;
	String host;
	
	public KirbyClient(String servername, int port){
		this.port = port;
		this.host = servername;
	}
	
	public ArrayList<Kirby> getKirbyPositions(){
		ArrayList<Kirby> players = new ArrayList<>();
		
		try {
			writer.writeUTF("position"); //send server request for number of players
			
			int kirbyCount = reader.readInt(); //server sends back number of players
			for(int i = 0; i < kirbyCount; i++){
				float x = reader.readFloat();
				float y = reader.readFloat();
				players.add(new Kirby(x, y));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return players;
	}
	
	public void connect(){
		try {
			System.out.println("host is " + this.host);
			System.out.println("port is " + this.port);
			socketReader = new Socket(this.host, this.port);
			reader = new DataInputStream(socketReader.getInputStream());
			
			int writePort = reader.readInt();
			System.out.println("writePort is " + writePort);
			socketWriter = new Socket(host, writePort);
			writer = new DataOutputStream(socketWriter.getOutputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public void update(){
		try {
			writer.writeUTF("update");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	/*
	public static void main(String args[]){
		KirbyClient client = null;
		if (args.length != 2){
			System.out.println("Usage: java KirbyClient host port");
		}
		else{
			client = new KirbyClient(args[0], Integer.parseInt(args[1]));
		}
	}
	*/
}