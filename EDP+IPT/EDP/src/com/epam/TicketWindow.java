package com.epam;

import com.epam.event.EventService;
import com.epam.model.Ticket;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public class TicketWindow {

    private TicketRepository repository;

    public TicketWindow() {
        repository = TicketRepository.instance;
    }

    public void sell() {
        if (!repository.isEmpty()) {
            for(Ticket ticket : repository.read()) {
                if(ticket.getStatus()== Ticket.Status.FRESH){
                    ticket.setStatus(Ticket.Status.SOLD);
                    repository.update(ticket);
                    EventService.EventServiceImpl.instance.send("ticket buy",ticket.toString());
                    return;
                }
            }
        }
        EventService.EventServiceImpl.instance.send("ticket buy","no tickets");
    }

}
