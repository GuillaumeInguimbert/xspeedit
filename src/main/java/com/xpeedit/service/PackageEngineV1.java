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

    private Optional<Package> getOrCreateRelevantPackage(int capacity) {
        Optional<Package> aPackage = getPackagesWhereCapacityHigherOrEqualsThan(capacity)
                .max(Package::compareTo);
        if (aPackage.isPresent()){
            return aPackage;
        }
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
