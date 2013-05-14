package com.hmml.knightswatch_server;

import java.util.ArrayList;

public class GameServer {

	final protected static int DEFAULT_PORT = 9999;
	protected static int port = DEFAULT_PORT;
	protected static PairingThread pairingThread;
	protected static ArrayList<GameThread> GameThreads; 
	
	public static void main(String[] argv) {
		// set port
		port = argv.length > 0 ? Integer.parseInt(argv[0]) : DEFAULT_PORT;
		
		// init GameThreads array
		GameThreads = new ArrayList<GameThread>();
		
		// start pairing thread, there should only be one
		pairingThread = new PairingThread();
		pairingThread.start();
		
		// pairing thread starts game threads		
	}
	
}
