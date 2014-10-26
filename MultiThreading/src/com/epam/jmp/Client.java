package com.epam.jmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		String host = "localhost";
	    /** Define a port */
	    int port = 19999;

		try (
			    Socket kkSocket = new Socket(host, port);
			    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
			) {
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
	            
            
            while ((fromServer = in.readLine()) != null) {
			    System.out.println("" + fromServer.replaceAll("\\$\\$", "\n"));
			    if (fromServer.equals("Bye."))
			        break;
			    
			    fromUser = stdIn.readLine();
			    if (fromUser != null) {
			        //System.out.println("Client: " + fromUser);
			        out.println(fromUser);
			    }
			}
		}

	}

}
