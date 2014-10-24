package com.epam.jmp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class Main {

	static final Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {


		/** Define a host server */
	    String host = "localhost";
	    /** Define a port */
	    int port = 19999;
	    
	    StringBuffer instr = new StringBuffer();
	    String TimeStamp;
	    System.out.println("SocketClient initialized");
	    
	    /** Obtain an address object of the server */
	      InetAddress address;
		try {
			address = InetAddress.getByName(host);
			/** Establish a socket connection */
			
			
			
			Socket connection = new Socket(address, port);
		      /** Instantiate a BufferedOutputStream object */
			
			BufferedOutputStream bos = new BufferedOutputStream(connection.
			          getOutputStream());

			/** Instantiate an OutputStreamWriter object with the optional character
			 * encoding.
			 */
			OutputStreamWriter osw = new OutputStreamWriter(bos, "UTF-8");
			
			TimeStamp = new java.util.Date().toString();
		    String process = "Calling the Socket Server on "+ host + " port " + port +
		          " at " + TimeStamp +  (char) 13;

		    /** Write across the socket connection and flush the buffer */
		    osw.write(process);
		    osw.flush();
			
		      /** Instantiate a BufferedInputStream object for reading
		       * incoming socket streams.
		       */

			BufferedInputStream bis = new BufferedInputStream(connection.
			      getInputStream());
			/**Instantiate an InputStreamReader with the optional
			 * character encoding.
			 */
			
			InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
			
			/**Read the socket's InputStream and append to a StringBuffer */
			int c;
			while ( (c = isr.read()) != 13){
			    instr.append( (char) c);
			}
			
			/** Close the socket connection. */
			connection.close();
			System.out.println(instr);
		     
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}

}
