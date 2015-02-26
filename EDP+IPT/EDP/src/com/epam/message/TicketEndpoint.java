package com.epam.message;

import com.epam.TicketRepository;
import com.epam.model.Ticket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class TicketEndpoint {

    public static void main(String... args) throws Exception {
        BufferedReader in;
        PrintWriter out;

        ServerSocket servers = null;
        Socket fromСlient = null;

        // create server socket
        try {
            servers = new ServerSocket(4444);
        } catch (IOException e) {
            System.exit(-1);
        }

        try {
            fromСlient = servers.accept();
        } catch (IOException e) {
            System.exit(-1);
        }

        in = new BufferedReader(new InputStreamReader(fromСlient.getInputStream()));
        out = new PrintWriter(fromСlient.getOutputStream(), true);
        String input, output;

        while ((input = in.readLine()) != null) {
            output = "";

            if (input.equalsIgnoreCase("exit")) {
                break;
            } else if (input.equals("sell")) {
                List<Ticket> tickets = TicketRepository.instance.read();

                if (!tickets.isEmpty()) {
                    for (Ticket ticket : tickets) {
                        if (ticket.getStatus() == Ticket.Status.FRESH) {
                            ticket.setStatus(Ticket.Status.SOLD);
                            TicketRepository.instance.update(ticket);

                            output = "1";
                        }
                    }
                }
            } else if (input.equals("isEmpty?")) {
                output = TicketRepository.instance.read().isEmpty() ? "1" : "0";
            }

            out.println(output);
            System.out.println(input);
        }

        System.out.println("exit");

        out.close();
        in.close();
        fromСlient.close();
        servers.close();
    }

}
