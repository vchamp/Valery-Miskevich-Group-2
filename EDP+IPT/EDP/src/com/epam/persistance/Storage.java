package com.epam.persistance;

import java.util.List;

/**
 * Created by Aliaksei_Dziashko on 26-Feb-15.
 */
public interface Storage<T> {
    public void create(T t);

    public List<T> read();

    public void update(T t);

    public void delete(T t);
}
