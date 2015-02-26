package com.epam.persistance;

import com.epam.model.Ticket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public class TicketStorage implements Storage<Ticket> {

    private String path;

    public TicketStorage(String path) {
        this.path = path;
    }

    @Override
    public void create(Ticket ticket) {
        List<Ticket> tickets = getAll();

        for (Ticket t : tickets) {
            if (t.toString().equals(ticket.toString())) {
                return;
            }
        }

        tickets.add(ticket);
        writeAll(tickets);
    }

    @Override
    public List<Ticket> read() {
        return getAll();
    }

    @Override
    public void update(Ticket ticket) {
        List<Ticket> tickets = getAll();

        for (Ticket t : tickets) {
            if (ticket.getTitle().equals(t.getTitle())) {
                t.setPrice(ticket.getPrice());

                writeAll(tickets);

                break;
            }
        }
    }

    @Override
    public void delete(Ticket ticket) {
        List<Ticket> tickets = getAll();
        int pos = -1;

        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);

            if (ticket.toString().equals(t.toString())) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            tickets.remove(pos);

            writeAll(tickets);
        }
    }

    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<Ticket>();

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tickets = (List<Ticket>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return tickets;
    }

    private void writeAll(List<Ticket> tickets) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tickets);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
