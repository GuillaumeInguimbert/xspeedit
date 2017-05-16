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

import static java.util.Collections.*;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PackageEngineV2Test extends PackageEngineTest{

    @Autowired
    private
    PackageEngine packageEngineV2;

    @After
    public void cleanup() throws Exception {
        packageEngineV2.getStorageRepository().clear();
        packageEngineV2.getStorageRepository().clearHistory();
    }

    @Test
    public void singleDeliveryTest1() {
        checkDeliveries(packageEngineV2, "163841689525773", singletonList("91/82/73/73/64/55/81/6"));
    }

    @Test
    public void singleDeliveryTest2() {
        checkDeliveries(packageEngineV2, "3333333333222222222255555555511", singletonList("55/55/55/55/532/3331/3331/22222/333/2222"));
    }

    @Test
    public void multipleDeliveriesTest1() {
        checkDeliveries(packageEngineV2,
                "12391219999999999999999999999999919999999999999999999999999",
                Arrays.asList("91/91/91/91/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/9/9/9/9/9/9/9/9", "9/9/322"));

    }

    @Test
    public void multipleDeliveriesTest2() {
        checkDeliveries(packageEngineV2,
                "1638416895257733333333333222222222255555555511",
                Arrays.asList("91/82/82/73/73/64/631/55/55/55", "55/55/532/3331/3331/3322/22222/2"));

    }
}