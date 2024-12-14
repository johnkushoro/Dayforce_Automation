package com.totallygroup.utils;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.totallygroup.pageObject", "com.totallygroup.utils", "com.totallygroup.model"})
@PropertySource("classpath:application.yml")
public class TestConfig {


    @Bean
    public WebDriver webDriver() {
        String browser = System.getProperty("browser", "chrome");
        return BrowserFactory.createDriver(browser);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
