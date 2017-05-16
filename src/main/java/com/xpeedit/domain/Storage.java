package com.xpeedit.domain;

import com.xpeedit.common.CapacityException;
import com.xpeedit.common.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public class Storage extends Capacity implements Serializable {

    private List<Package> packages = new ArrayList<>();

    public List<Package> getPackages() {
        return packages;
    }

    protected void add(Package aPackage) throws CapacityException {
        if(getRemainingCapacity() == 0){
            throw new CapacityException("Add package to the storage error.", this);
        }
        packages.add(aPackage);
    }

    @Override
    public int getMaxSize() {
        return Constants.STORAGE_CAPACITY;
    }

    @Override
    public int getSize() {
        return packages.size();
    }
}
