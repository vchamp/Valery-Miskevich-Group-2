package com.epam.jmp.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.jmp.model.Account;

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
	
	public synchronized String write(Account object) {
		String fileName = "No name";
		String json = object.toString();
		//todo write to file
		File f = new File(DIR, object.getName());
		
		try (BufferedWriter writer = Files.newBufferedWriter(f.toPath(), Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
		    writer.write(json, 0, json.length());
		    fileName = f.toString();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		return fileName;
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

	public boolean delete(String string) {
		File file = new File(DIR, string);
		boolean result = false;
		try {
		    Files.delete(file.toPath());
		    result = true;
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", file);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", file);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		} 
		return result;
	}
}
