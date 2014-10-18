package com.epam.memorymanagement;

import android.content.Context;
import android.content.MutableContextWrapper;

public class AppWrapper extends MutableContextWrapper {

    public AppWrapper(Context base) {
        super(base.getApplicationContext());
    }

    public BitmapProvider images(){
        return BitmapProvider.getInstance(this);
    }
}
