package com.epam.jmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		int port = 19999;
		
		try ( 
			    ServerSocket serverSocket = new ServerSocket(port);
			    Socket clientSocket = serverSocket.accept();
			    PrintWriter out =
			        new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			        new InputStreamReader(clientSocket.getInputStream()));
			) {
		
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
	}
	}

}
