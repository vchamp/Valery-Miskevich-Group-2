package com.epam.event;

public interface EventListener {

    public void onEvent(String event, String... args);

}
