package com.xpeedit.service;

import com.xpeedit.domain.Package;
import com.xpeedit.domain.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@Service("packageEngineV2")
@Scope("singleton")
public class PackageEngineV2 extends PackageEngineBase{

    @Override
    protected void distribute(final List<Product> products) {
        products.sort(Comparator.reverseOrder());
        Package aPackage = storageRepository.createPackageAndGet();
        while (!aPackage.isFull() && products.stream().anyMatch(Product::waitingForDistribution)){
            Optional<Product> relevantProduct = getRelevantProduct(aPackage, products);
            if (relevantProduct.isPresent()){
                aPackage.add(relevantProduct.get());
            }
            else if (!storageRepository.isFull()){
                aPackage = storageRepository.createPackageAndGet();
            }
            else {
                break;
            }
        }
    }

    private Optional<Product> getRelevantProduct(Package aPackage, List<Product> products) {
        return products.stream().filter(product -> product.getSize() <= aPackage.getRemainingCapacity() && product.waitingForDistribution()).findFirst();
    }
}
