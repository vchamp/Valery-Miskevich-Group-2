package com.epam.model;

import java.io.Serializable;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public class Ticket implements Serializable{

    private String title;
    private Price price;
    private Status status;

    public Ticket(String title, Price price) {
        this.title = title;
        this.price = price;
        status = Status.FRESH;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

    public static enum Status {
        FRESH,
        SOLD
    }
}
