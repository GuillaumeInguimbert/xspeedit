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
        Optional<List<Product>> products = scanProducts(productChain);
        if (products.isPresent()){
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
     * @param productChain
     * @return
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

    private void deliver() throws Exception {
        storageRepository.saveToHistory();
        storageRepository.clear();
    }

    public StorageRepository getStorageRepository() {
        return storageRepository;
    }
}
