package com.xpeedit.repository;

import com.xpeedit.common.CapacityException;
import com.xpeedit.domain.Package;
import com.xpeedit.domain.Product;
import com.xpeedit.domain.Storage;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public interface StorageRepository {
    List<Package> getAllPackages();

    List<Product> getAllProducts();

    Package createPackageAndGet() throws CapacityException;

    boolean isFull();

    void clear();

    void saveToHistory();

    List<Storage> getHistory();

    void clearHistory();
}
