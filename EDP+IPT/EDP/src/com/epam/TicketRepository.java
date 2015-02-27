package com.epam;

import com.epam.model.Price;
import com.epam.model.Ticket;
import com.epam.persistance.Storage;
import com.epam.persistance.TicketStorage;

import java.util.List;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public enum TicketRepository {

    instance;

    private Storage<Ticket> ticketStorage = new TicketStorage("tickets.db");

    public List<Ticket> read() {
        return ticketStorage.read();
    }

    public void update(Ticket ticket) {
        ticketStorage.update(ticket);
    }

    public boolean isEmpty() {
        return ticketStorage.read().isEmpty();
    }

    public void fillRepository() {
        for (int i = 0; i <= 10; i++) {
            ticketStorage.create(new Ticket("Random title", new Price("tugric", 100500)));
        }
    }
}
