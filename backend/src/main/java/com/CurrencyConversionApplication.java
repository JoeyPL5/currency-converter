package com;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CurrencyConversionApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CurrencyConversionApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}