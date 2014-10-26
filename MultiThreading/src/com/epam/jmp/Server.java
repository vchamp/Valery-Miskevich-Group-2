package com.epam.jmp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class Server {

	static final Logger logger = Logger.getLogger(ClientConnection.class);
	
	public static void main(String[] args) {
		
		int port = 19999;
		int clientNumber = 0;
		
		try ( 
			    ServerSocket serverSocket = new ServerSocket(port);
			    
			) {
		
			while(true) {
				
				Socket clientSocket = serverSocket.accept();
				String newClient = "Client - "+(++clientNumber);
				new ClientConnection(clientSocket, clientNumber).start();
				
				logger.info("New client: " + newClient);
			}
		} catch (IOException e) {
			
			logger.error("Server", e);
		} 
	}

}
