package com.epam.jmp.io;

import java.io.Closeable;
import java.io.IOException;

import com.google.gson.Gson;

public class IO {

	public static void write(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		//todo write to file
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
