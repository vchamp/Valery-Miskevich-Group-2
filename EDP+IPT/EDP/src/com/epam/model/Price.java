package com.epam.model;

import java.io.Serializable;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
// Value object kind
public class Price implements Serializable {
    
    private String currency;
    private double cost;

    public Price(String currency, double cost) {
        this.currency = currency;
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Price{" +
                "currency='" + currency + '\'' +
                ", cost=" + cost +
                '}';
    }
}
