package com.xpeedit.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public class Product implements Comparable<Product>, Serializable {

    private int size;

    private Package aPackage;

    public Product(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Package getPackage() {
        return aPackage;
    }

    public void setPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public boolean waitingForDistribution(){
        return  Objects.isNull(getPackage());
    }

    @Override
    public int compareTo(Product o)  {
        return this.getSize() - o.getSize();
    }
}
