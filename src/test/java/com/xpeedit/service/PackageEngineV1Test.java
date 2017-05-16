package com.xpeedit.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.Collections.*;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PackageEngineV1Test extends PackageEngineTest{

    @Autowired
    private
    PackageEngine packageEngineV1;

    @After
    public void cleanup() throws Exception {
        packageEngineV1.getStorageRepository().clear();
        packageEngineV1.getStorageRepository().clearHistory();
    }

    @Test
    public void singleDeliveryTest1() {
        checkDeliveries(packageEngineV1, "163841689525773", singletonList("163/46/82/55/73/81/9/7"));
    }

    @Test
    public void singleDeliveryTest2() {
        checkDeliveries(packageEngineV1, "111234123412341234123412341234123411", singletonList("1112311/4231/4231/4231/4231/4231/4231/4231/4"));
    }

    @Test
    public void multipleDeliveriesTest() {
        checkDeliveries(packageEngineV1, "12391219999999999999999999999999919999999999999999999999999",
                Arrays.asList("123211/91/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9"));
    }

}