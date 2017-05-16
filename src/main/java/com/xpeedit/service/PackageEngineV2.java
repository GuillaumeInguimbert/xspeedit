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
        // Sort by product size desc
        products.sort(Comparator.reverseOrder());
        Package aPackage = storageRepository.createPackageAndGet();
        // Try to distribute as many products as possible in the same package
        while (products.stream().anyMatch(Product::waitingForDistribution)){
            // Find the product with the biggest possible size that can be distributed in the current package
            Optional<Product> relevantProduct = getRelevantProduct(aPackage, products);
            // If a product is found, add it to the package
            if (relevantProduct.isPresent()){
                aPackage.add(relevantProduct.get());
            }
            // Else try to add a new empty package (if possible) to the storage and continue the distribution with it
            else if (!storageRepository.isFull()){
                aPackage = storageRepository.createPackageAndGet();
            }
            else {
                break;
            }
        }
    }

    /**
     * Find the first product that can be added to the specified package.
     * If the product list has no encounter order, then any element may be returned.
     * Only the products that are waiting for distribution are treated, the others are ignored.
     *
     * @param aPackage The package.
     * @param products The list of products to look for.
     * @return The relevant product if exists or empty.
     */
    private Optional<Product> getRelevantProduct(Package aPackage, List<Product> products) {
        return products.stream().filter(product -> product.getSize() <= aPackage.getRemainingCapacity() && product.waitingForDistribution()).findFirst();
    }
}
