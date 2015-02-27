package com.epam.message;

import com.epam.adapter.Adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TicketChannel {

    Socket fromserver = null;
    BufferedReader in = null;
    PrintWriter out = null;
    BufferedReader inu = null;
    private Adapter<String> adapter;

    public TicketChannel(Adapter<String> adapter) {
        this.adapter = adapter;
    }

    public void connect() {
        try {
            fromserver = new Socket("127.0.0.1", 4444);
            in = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
            out = new PrintWriter(fromserver.getOutputStream(), true);
            inu = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            assert out != null;
            out.close();
            in.close();
            assert inu != null;
            inu.close();
            fromserver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String send(String command) {
        try {
            String fserver;

            out.println(command);
            fserver = in.readLine();

            // for sample just send without looping
            return adapter.transform(fserver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
