package edu.dal.eauction.itemManagement.configurer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ItemImagesMvcConfigurer implements WebMvcConfigurer {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("images/**")
		.addResourceLocations("classpath:/static/images/");
		LOG.info("Added the Resource Handlers for Item Images");
	}

}
