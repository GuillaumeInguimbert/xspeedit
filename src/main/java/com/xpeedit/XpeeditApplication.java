package com.xpeedit;

import com.xpeedit.repository.StorageRepository;
import com.xpeedit.service.PackageEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.List;

import static java.lang.String.format;

/**
 * Created by GUILLAUME.INGUIMBERT on 15/05/2017.
 */
@SpringBootApplication
public class XpeeditApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(XpeeditApplication.class);

	private static final String PRODUCTS_CHAIN = "products";

	@Autowired
	private PackageEngine packageEngineV1;

	@Autowired
	private PackageEngine packageEngineV2;

	@Autowired
	private StorageRepository storageRepository;

	@Override
	public void run(String... args) {
		SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource(args);
		if (args.length > 0 && propertySource.containsProperty(PRODUCTS_CHAIN)) {
			String productChain = propertySource.getProperty(PRODUCTS_CHAIN);
			LOGGER.info(format("Chaîne d'articles en entrée : %s", productChain));
			try {
				LOGGER.info(" === Résultat de l'algo. 1:");
				distributeAndDeliver(packageEngineV1, productChain);
				LOGGER.info(" === Résultat de l'algo. 2:");
				distributeAndDeliver(packageEngineV2, productChain);
			} catch (Exception e) {
				LOGGER.error("Erreur durant la distribution.", e);
			}
		}
		else {
			LOGGER.warn("Il n'y a pas d'articles en entrée.");
		}
	}

	private void distributeAndDeliver(PackageEngine packageEngine, String productChain) throws Exception {
		List<String> deliveries = packageEngine.distributeAndDeliver(productChain);
		// Print the content of each delivery
		for (int i = 0; i < deliveries.size(); i++) {
			LOGGER.info(format("Livraison %d - Chaîne d'articles emballés : %s", i + 1, deliveries.get(i)));
		}
		storageRepository.clearHistory();
	}

	public static void main(String[] args) {
		SpringApplication.run(XpeeditApplication.class, args);
	}
}
