package com.xpeedit.service;

import com.xpeedit.repository.StorageRepository;

import java.util.List;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public interface PackageEngine {

    List<String> distributeAndDeliver(final String productChain) throws Exception;

    StorageRepository getStorageRepository();
}
