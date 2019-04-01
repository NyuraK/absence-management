package com.netcracker.vacations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*I'm trying to make SPA work with Spring properly*/

//@Configuration
//@EnableWebMvc
public class FrontConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/{spring:\\w+}")
                .setViewName("forward:/resources/public/index.html");
        registry.addViewController("/**/{spring:\\w+}")
                .setViewName("forward:/resources/public/index.html");
        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
                .setViewName("forward:/resources/public/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/{spring:\\w+}")
                .addResourceLocations("forward:/resources/public/");
        registry.addResourceHandler("/**/{spring:\\w+}")
                .addResourceLocations("forward:/resources/public/");
        registry.addResourceHandler("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
                .addResourceLocations("forward:/resources/public/");
    }
}
