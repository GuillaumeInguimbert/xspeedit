package com.xpeedit.service;

import com.xpeedit.repository.StorageRepository;

import java.util.List;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public interface PackageEngine {

    /**
     * Parse the specified product chain and distribute each product into packages.
     * When the storage space is full and/or all the product have been distributed, make a delivery.
     * If the quantity of product is higher than the storage capacity, a delivery is made and the distribution continue with a new empty storage space.
     *
     * @param productChain The product chain (example: 163841689525773)
     * @return The list of deliveries that have been made. (example: 163/8/41/6/8/9/52/5/7/73)
     * @throws Exception If any error occurs during distribution.
     */
    List<String> distributeAndDeliver(final String productChain) throws Exception;

    StorageRepository getStorageRepository();
}
