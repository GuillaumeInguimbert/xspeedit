package com.xpeedit.domain;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public abstract class Capacity {

    public abstract int getMaxSize();

    public abstract int getSize();

    public int getRemainingCapacity(){
        return getMaxSize() - getSize();
    }

    public boolean isFull(){
        return 0 == getRemainingCapacity();
    }

}
