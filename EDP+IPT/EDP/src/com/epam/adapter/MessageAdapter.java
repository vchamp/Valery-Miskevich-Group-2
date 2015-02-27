package com.epam.adapter;

/**
 * Created by Aliaksei_Dziashko on 27-Feb-15.
 */
public class MessageAdapter implements Adapter<String>{
    @Override
    public String transform(String message) {
        if("1".equals(message))
            return "buy success";
        return "no tickets";
    }
}
