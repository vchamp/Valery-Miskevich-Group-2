package com.epam;

import com.epam.event.EventListener;
import com.epam.event.EventService;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public class Main {

    public static void main(String[] args) {
        EventListener listener = new EventListener() {
            @Override
            public void onEvent(String event, String... args) {
                System.out.println(event+" "+args[0]);
            }
        };
        EventService.EventServiceImpl.instance.registerSubscriber("tickets", listener);
        TicketRepository repository = TicketRepository.instance;
        repository.fillRepository();

        TicketWindow window = TicketWindow.instance;
        window.sell();
        window.sell();
        
        new Client().tryBuyTicket();
    }
}
