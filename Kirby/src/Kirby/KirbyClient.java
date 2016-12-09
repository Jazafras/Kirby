package Kirby;

import java.net.*;
import java.util.Scanner;

import org.newdawn.slick.Input;

import java.io.*;

public class KirbyClient extends Thread{

	private ServerSocket socket;
	private InetAddress ipAddress;
	private KirbyGame game;
	DataOutputStream out;
	DataInputStream in;
	/*
	public KirbyClient(KirbyGame game, String ipAddress){
		this.game = game;
		try {
			this.socket = new ServerSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try{
			out.writeInt(playerid);
		}
		catch(IOException e1){
		}
		while(true){
			try{
				playeridIN = in.readInt();
				xIN = in.readInt();
				yIN = in.readInt();
				for(int i = 0; i < 10; i++){
					if(player[i] != null){
						player[i].out.writeInt(playeridIN);
						player[i].out.writeInt(xIN);
						player[i].out.writeInt(yIN);
					}
				}
			}catch(IOException e){
				player[playerid] = null;
			
	*/
   public static void main(String [] args) throws Exception{
      String serverName = args[0];
      int port = Integer.parseInt(args[1]);
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         /*
         Input input = new Input(in);
         Thread thread = new Thread(input);
         thread.start();
         */
         Scanner sc = new Scanner(System.in);
         
         System.out.println("Server says " + in.readUTF());
         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}