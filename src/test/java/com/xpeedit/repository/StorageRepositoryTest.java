package com.xpeedit.repository;

import com.xpeedit.common.CapacityException;
import com.xpeedit.domain.Package;
import com.xpeedit.domain.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageRepositoryTest {

    @Autowired
    private StorageRepository storageRepository;

    @Before
    public void setup() throws Exception {

        storageRepository.createPackageAndGet().add(new Product(10));

        Package aPackage = storageRepository.createPackageAndGet();
        aPackage.add(new Product(5));
        aPackage.add(new Product(3));
        aPackage.add(new Product(2));

        aPackage = storageRepository.createPackageAndGet();
        aPackage.add(new Product(3));
        aPackage.add(new Product(3));
    }

    @After
    public void cleanup() throws Exception {
        storageRepository.clear();
        storageRepository.clearHistory();
    }

    @Test
    public void getAllPackagesTest() throws Exception {
        Assert.assertEquals(3, storageRepository.getAllPackages().size());
        storageRepository.createPackageAndGet();
        Assert.assertEquals(4, storageRepository.getAllPackages().size());
    }

    @Test
    public void getAllProductsTest() throws Exception {
        Assert.assertEquals(6, storageRepository.getAllProducts().size());
        storageRepository.getAllPackages().get(2).add(new Product(1));
        Assert.assertEquals(7, storageRepository.getAllProducts().size());

    }

    @Test
    public void capacityTest() throws Exception {
        for (int i = 0; i < 7; i++) {
            storageRepository.createPackageAndGet();
        }
        try {
            storageRepository.createPackageAndGet();
            Assert.fail();
        }
        catch (CapacityException e){
            Assert.assertTrue(e.getCapacityInfo().isFull());
        }

    }

    @Test
    public void isFullTest() throws Exception {
        Assert.assertFalse(storageRepository.isFull());
        for (int i = 0; i < 7; i++) {
            storageRepository.createPackageAndGet();
        }
        Assert.assertTrue(storageRepository.isFull());
    }

    @Test
    public void historyTest() throws Exception {
        Assert.assertEquals("History size", 0, storageRepository.getHistory().size());

        storageRepository.saveToHistory();
        Assert.assertEquals("History size", 1, storageRepository.getHistory().size());
        Assert.assertEquals("History - number of packages", 3, storageRepository.getHistory().get(0).getPackages().size());

        storageRepository.clearHistory();
        Assert.assertEquals("History size", 0, storageRepository.getHistory().size());
    }

}