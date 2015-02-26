package com.epam.event;

import java.util.HashMap;
import java.util.Map;

public interface EventService {

    public void send(String event, String... params);

    public void registerSubscriber(String event, EventListener listener);

    public void unregisterSubscriber(EventListener listener);

    public static class EventServiceImpl implements EventService {
        private Map<Integer, EventListener> listeners;
        private Map<Integer, String> events;
        
        public static EventServiceImpl instance = new EventServiceImpl();
        
        private EventServiceImpl() {
            listeners = new HashMap<Integer, EventListener>();
        }

        @Override
        public void send(String event, String... args) {
            for(Integer id : events.keySet()){
                if(events.get(id).equals(event)){
                    listeners.get(id).onEvent(event, args);
                }
            }
        }

        @Override
        public void registerSubscriber(String event, EventListener listener) {
            listeners.put(listener.hashCode(), listener);
            events.put(listener.hashCode(), event);
        }

        @Override
        public void unregisterSubscriber(EventListener listener) {
            listeners.remove(listener.hashCode());
            events.remove(listener.hashCode());
        }
    }
}
