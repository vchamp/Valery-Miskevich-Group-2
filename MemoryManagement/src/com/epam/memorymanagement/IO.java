package com.epam.memorymanagement;

import java.io.Closeable;
import java.io.IOException;

public class IO {

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
