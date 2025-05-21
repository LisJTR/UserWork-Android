package com.TFG_backend.dockerized.postgresql.uploads;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// Permite servir archivos estatios desde la ruta de la carpeta
        registry.addResourceHandler("/uploads/**")
        		.addResourceLocations("file:/uploads/");
    }
}