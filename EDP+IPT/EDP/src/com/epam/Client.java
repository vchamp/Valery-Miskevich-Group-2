package com.epam;

import com.epam.adapter.MessageAdapter;
import com.epam.event.EventListener;
import com.epam.message.TicketChannel;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public class Client implements EventListener {
    
    @Override
    public void onEvent(String event, String... args) {
        System.out.println(event+" "+args[0]);
    }
    
    public void tryBuyTicket() {
        TicketChannel ticketChannel = new TicketChannel(new MessageAdapter());
        ticketChannel.connect();
        String response = ticketChannel.send("sell");
        System.out.println(response);
        ticketChannel.disconnect();
    }
    
}
