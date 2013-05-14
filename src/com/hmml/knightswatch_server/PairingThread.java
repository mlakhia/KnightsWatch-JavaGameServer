package com.hmml.knightswatch_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class PairingThread extends Thread {
	
	public PairingThread() {
        // constructor
    }
	
    public void run() {
        System.out.println("Inside Pairing Thread");
        
        // to listen to incoming connections		
 		ServerSocket server = null;
 		try {
 			server = new ServerSocket(GameServer.port);
 		} catch (IOException e) {
 			System.err.println("Could not listen on port: " + GameServer.port);
 			System.exit(-1);
 		}
 		System.out.println("Listening on port: " + GameServer.port);

 		while(true) {
 			
			// wait for a client connection, socket is obtained when a client connects
			Socket socket1 = null;
			Socket socket2 = null;
			try {
				socket1 = server.accept();
				System.out.println("Accepted connection 1: " + socket1.getInetAddress().toString());

				socket2 = server.accept();
				System.out.println("Accepted connection 2: " + socket2.getInetAddress().toString());
				
				// first param is gameId, 
				// we use the current position in the GameThreads array as the Id
				// starts at 0
				GameThread newGame = new GameThread(GameServer.GameThreads.size(), socket1, socket2);
				GameServer.GameThreads.add(newGame);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
 		}
 		
    }
    
}