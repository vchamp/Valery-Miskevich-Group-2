package com.epam.jmp.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.jmp.ClientConnection;
import com.epam.jmp.model.Account;
import com.google.gson.Gson;

public class IO {

	static final Logger logger = Logger.getLogger(IO.class);
	
	private static final String DIR = "./storage";

	private static IO instance;
	
	private IO() {
		Path dir = new File(DIR).toPath();
		try {
			if(!Files.exists(dir))
				Files.createDirectory(dir);
		} catch (IOException e) {
			logger.error("IO", e);
		}
	}
	
	public static IO get() {
		if(instance == null){
			instance = new IO();
		}
		return instance;
	}
	
	public synchronized void write(Account object) {
//		Gson gson = new Gson();
		String json = object.toString();
		//todo write to file
		File f = new File(DIR, object.getName());
		
		try (BufferedWriter writer = Files.newBufferedWriter(f.toPath(), Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
		    writer.write(json, 0, json.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
	
	public synchronized Account read(String name) {
		
		File f = new File(DIR, name);
		
		if(!f.exists())
			return null;
		
		try (BufferedReader reader = Files.newBufferedReader(f.toPath(), Charset.forName("UTF-8"))) {
			String s = reader.readLine();
			return new Account(s);
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		return null; 
	}
	
	public synchronized List<String> accountList() {
		
		ArrayList<String> files = new ArrayList<String>();
		File folder = new File(DIR);
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	files.add(fileEntry.getName());
	        }
	    }
	    return files;
	    
	}
	
    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //ignored
            }
        }
    }
}
