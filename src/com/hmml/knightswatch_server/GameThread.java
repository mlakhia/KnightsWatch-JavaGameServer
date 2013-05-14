package com.hmml.knightswatch_server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameThread extends Thread {
	
	public enum Player { Black, White }
	
	protected static ArrayList<Socket> Watchers;
	
	private Socket socket1;
	private Socket socket2;

	private String currentBroadcast;
	private Player currentPlayer; 
	// 0 or 1; 
	// 0 = player 1 = socket 1 = black
	// 2 = player 2 = socket 2 = white
	
	
	public GameThread(Socket socket1, Socket socket2) {
		this.socket1 = socket1;
		this.socket2 = socket2;
	}
	
	public void addWatcher(Socket watcher){
		Watchers.add(watcher);
	}

	public void run() {
        System.out.println("Inside Game Thread");        

        // socket 1 = player 1 = black ; starts
        currentPlayer = Player.Black;
        currentBroadcast ="";
	
		try{
 			Scanner scanner1 = null;
			PrintStream out1 = null;
 			Scanner scanner2 = null;
			PrintStream out2 = null;
			try {	
				// make a scanner & printstream out of the socket's I/O streams
				scanner1 = new Scanner(socket1.getInputStream());
				scanner2 = new Scanner(socket2.getInputStream());
				out1 = new PrintStream(socket1.getOutputStream());
				out2 = new PrintStream(socket2.getOutputStream());
	
				while(true){
					
					// Retrieve Next Action into currentBroadcast
					if(currentPlayer == Player.Black){ // player 1 - socket 1
						
						//while(scanner1.hasNext()) {
						currentBroadcast = scanner1.nextLine();
						//break;
						//}
						
						// Switch Player Turn
						currentPlayer = Player.White;
					}
					if(currentPlayer == Player.White){ // player 2 - socket 2
						
						//while(scanner1.hasNext()) {
						currentBroadcast = scanner2.nextLine();
						//break;
						//}
						
						// Switch Player Turn
						currentPlayer = Player.Black;
					}
					
					// Switch Player Turn
					
					// Handle Disconnects
					if(scanner1 == null){
						out2.println("D\n Player 2 Disconnected.");
						break;
					}
					if(scanner2 == null){
						out1.println("D\n Player 1 Disconnected.");
						break;
					}
				}
				
				// 
				while(scanner1.hasNext()) {
					//out1.println(scanner1.nextLine());
				}

				while(scanner2.hasNext()) {
					//out1.println(scanner1.nextLine());
				}	
				
			} finally {
		 		if(scanner1 != null)
		 			scanner1.close();
		 		if(out1 != null)
		 			out1.close();
		 		if(socket1 != null)
					socket1.close();

		 		if(scanner2 != null)
		 			scanner2.close();
		 		if(out2 != null)
		 			out2.close();
		 		if(socket2 != null)
					socket2.close();
			}				
	 	} catch (IOException e) {
			e.printStackTrace();
		}
		 	
 		
	}
}