package com.xpeedit.domain;

import com.xpeedit.common.CapacityException;
import com.xpeedit.common.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public class Package extends Capacity implements Comparable<Package>, Serializable {

    private List<Product> products;

    public Package() {
        super();
        products = new ArrayList<>();
    }

    public void add(Product product) throws CapacityException {
        if(product.getSize() > getRemainingCapacity()){
            throw new CapacityException("Add product size " + product.getSize() + " error.", this);
        }
        products.add(product);
        product.setPackage(this);
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public int getMaxSize() {
        return Constants.PACKAGE_CAPACITY;
    }

    @Override
    public int getSize() {
        return products.stream().mapToInt(Product::getSize).sum();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String toString() {
        return products.stream().map(Product::toString).collect(Collectors.joining());
    }

    @Override
    public int compareTo(Package o) {
            return this.getSize() - o.getSize();
    }
}
