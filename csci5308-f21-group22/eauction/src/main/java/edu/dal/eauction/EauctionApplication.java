package edu.dal.eauction;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import edu.dal.eauction.coreServices.SystemServices;

@SpringBootApplication
public class EauctionApplication {

	private static final Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EauctionApplication.class, args);

//		Initializing core system services while startup
		if (Objects.isNull(SystemServices.getInstance())) {
			LOG.error("Error while initialising system services. Shutting down the application");
			context.close();
		}
		LOG.debug("Core system services are initialized");
	}

}
