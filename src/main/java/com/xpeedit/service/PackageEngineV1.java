package com.xpeedit.service;

import com.xpeedit.domain.Package;
import com.xpeedit.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@Service("packageEngineV1")
@Scope("singleton")
public class PackageEngineV1 extends PackageEngineBase{

    @Override
    protected void distribute(List<Product> products) {
        products.stream()
                .filter(Product::waitingForDistribution)
                .forEach(product -> getOrCreateRelevantPackage(product.getSize()).ifPresent(aPackage -> aPackage.add(product)));
    }

    /**
     * Find the Package with a capacity as biggest as possible of the specified one.
     * If no package can be found, create a new empty one if the storage space is not full.
     * @param capacity The ideal capacity.
     * @return The package, or empty if the storage space is full.
     */
    private Optional<Package> getOrCreateRelevantPackage(int capacity) {
        // Find the Package with the biggest possible capacity
        Optional<Package> aPackage = getPackagesWhereCapacityHigherOrEqualsThan(capacity)
                .max(Package::compareTo);
        // If a package is found, return it
        if (aPackage.isPresent()){
            return aPackage;
        }
        // Else try to add a new empty package (if possible) and return it
        else if (!storageRepository.isFull()){
            return Optional.of(storageRepository.createPackageAndGet());
        }
        else
            return Optional.empty();
    }

    /**
     *  Select all packages having a capacity higher or equals than the specified capacity.
     *
     * @param capacity The needed capacity.
     * @return All packages having a capacity higher or equals to the specified capacity.
     */
    private Stream<Package> getPackagesWhereCapacityHigherOrEqualsThan(int capacity){
        return storageRepository.getAllPackages().stream()
                .filter(aPackage -> aPackage.getRemainingCapacity() >= capacity);
    }
}
