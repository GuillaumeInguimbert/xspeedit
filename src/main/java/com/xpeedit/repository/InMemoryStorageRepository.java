package com.xpeedit.repository;

import com.xpeedit.common.CapacityException;
import com.xpeedit.domain.Package;
import com.xpeedit.domain.Product;
import com.xpeedit.domain.Storage;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * In-memory implementation of the storage space.
 *
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@Repository("storageRepository")
@Scope("singleton")
public class InMemoryStorageRepository extends Storage implements StorageRepository {

    private List<Storage> history = new LinkedList<>();

    /**
     *
     * @return All packages available in the storage space
     */
    @Override
    public List<Package> getAllPackages(){
        return getPackages();
    }

    /**
     *
     * @return All products stored in all packages
     */
    @Override
    public List<Product> getAllProducts(){
        return getAllPackages()
                .stream()
                .filter(aPackage -> !isEmpty(aPackage.getProducts()))
                .map(Package::getProducts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     *  Select all packages having a capacity higher or equals than the specified capacity.
     *
     * @param capacity The needed capacity.
     * @return All packages having a capacity higher or equals to the specified capacity.
     */
    @Override
    public Stream<Package> getPackagesWhereCapacityHigherOrEqualsThan(int capacity){
        return getPackages().stream()
                .filter(aPackage -> aPackage.getRemainingCapacity() >= capacity);
    }

    /**
     * Add an empty package in the storage space and return it.
     * @return The package.
     * @throws CapacityException
     */
    @Override
    public Package createPackageAndGet() throws CapacityException {
        Package aPackage = new Package();
        add(aPackage);
        return aPackage;
    }

    /**
     * Remove all packages available in the storage space.
     */
    @Override
    public void clear(){
        getPackages().clear();
    }

    /**
     * Save the current storage context (packages & products) to the history.
     */
    @Override
    public void saveToHistory() {
        history.add(SerializationUtils.clone(this));
    }

    /**
     *
     * @return The storage history.
     */
    @Override
    public List<Storage> getHistory() {
        return history;
    }

    /**
     * Remove history.
     */
    @Override
    public void clearHistory() {
        history.clear();
    }
}
