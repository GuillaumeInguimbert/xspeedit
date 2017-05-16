package com.xpeedit.service;

import com.xpeedit.common.CapacityException;
import com.xpeedit.domain.Product;
import com.xpeedit.domain.Package;
import com.xpeedit.domain.Storage;
import com.xpeedit.repository.StorageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public abstract class PackageEngineBase implements PackageEngine {

    @Autowired
    StorageRepository storageRepository;

    protected abstract void distribute(final List<Product> products) throws CapacityException;

    @Override
    public synchronized List<String> distributeAndDeliver(final String productChain) throws Exception {
        // Parse the chain and map it to a list of products.
        Optional<List<Product>> products = scanProducts(productChain);
        if (products.isPresent()){
            // Distribute all products and make deliveries as many as necessary
            while (products.get().stream().anyMatch(Product::waitingForDistribution)){
                distribute(products.get());
                deliver();
            }
            return printDeliveries(storageRepository.getHistory());
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * Parse the specified product chain and map it to a list of products.
     * @param productChain The product chain as string
     * @return The resulting list of products
     */
    private Optional<List<Product>> scanProducts(final String productChain){
        if (StringUtils.isNotBlank(productChain)){
            return Optional.ofNullable(productChain.chars()
                    .mapToObj(value -> (char)value)
                    .map(String::valueOf)
                    .filter(s -> s.matches("[1-9]{1}"))
                    .map(Integer::new)
                    .map(Product::new)
                    .collect(Collectors.toList()));
        }
        else {
            return Optional.empty();
        }
    }

    /**
     *
     * @param storageHistory - The list of deliveries to be printed.
     * @return The list of deliveries formatted for printing.
     */
    private List<String> printDeliveries(final List<Storage> storageHistory){
        if (!CollectionUtils.isEmpty(storageHistory)){
            return storageHistory.stream()
                    .map(Storage::getPackages)
                    .map(packages -> packages.stream().sorted(Comparator.reverseOrder()).map(Package::toString).collect(Collectors.joining("/")))
                    .collect(Collectors.toList());
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * Deliver the packages available in the storage space.
     * Clean the storage space.
     * @throws Exception
     */
    private void deliver() throws Exception {
        storageRepository.saveToHistory();
        storageRepository.clear();
    }

    public StorageRepository getStorageRepository() {
        return storageRepository;
    }
}
