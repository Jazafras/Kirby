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
	
	public KirbyClient(String servername, int port){
		try {
			socketReader = new Socket(servername, port);
			reader = new DataInputStream(socketReader.getInputStream());
			
			int writePort = reader.readInt();
			socketWriter = new Socket(servername, writePort);
			writer = new DataOutputStream(socketWriter.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
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