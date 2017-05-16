package com.xpeedit.service;

import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
public abstract class PackageEngineTest {

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    /**
     * Call the specified package engine and pass it the specified product chain.
     * Check the number of deliveries & the content of each delivery.
     *
     * @param packageEngine The engine to use
     * @param productChain The products to process
     * @param expectedDeliveries The expected deliveries
     */
    public void checkDeliveries(PackageEngine packageEngine, String productChain, List<String> expectedDeliveries)  {
        try {
            List<String> actualDeliveries = packageEngine.distributeAndDeliver(productChain);
            // Check the number of deliveries
            errorCollector.checkThat("Number of deliveries", actualDeliveries.size(), equalTo(expectedDeliveries.size()));
            // Check the content of each delivery
            for (int i = 0; i < actualDeliveries.size(); i++) {
                errorCollector.checkThat("Delivery number " + (i+1), actualDeliveries.get(i), equalTo(expectedDeliveries.get(i)));
            }
        } catch (Exception e) {
            errorCollector.addError(e);
        }
    }
}
