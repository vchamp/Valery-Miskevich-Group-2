package com.epam.jmp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.epam.jmp.io.IO;

public class ClientConnection extends Thread {
	
	static final Logger logger = Logger.getLogger(ClientConnection.class);
	
	private Socket socket;
	private int clientNumber;

	public ClientConnection(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber;
		setDaemon(true);
	}

	@Override
	public void run() {
		
		
		try (
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
			    )
			{
			
			String inputLine, outputLine;
	        
		    // Initiate conversation with client
		    Protocol kkp = new Protocol();
		    outputLine = kkp.processInput(null);
		    out.println(outputLine);
	
		    while ((inputLine = in.readLine()) != null) {
		        outputLine = kkp.processInput(inputLine);
		        out.println(outputLine);
		        if (outputLine.equals("Bye."))
		            break;
		    }
		    
		} catch (IOException e) {
			logger.error("Client connection", e);
		} 
	}

	
}
