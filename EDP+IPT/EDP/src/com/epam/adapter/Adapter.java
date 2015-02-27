package com.epam.adapter;

/**
 * Created by Aliaksei_Dziashko on 27-Feb-15.
 */
public interface Adapter<T> {
    public T transform(String message);
}
