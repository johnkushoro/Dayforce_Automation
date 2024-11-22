
package com.totallygroup.utils;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.totallygroup.pageObject", "com.totallygroup.utils", "com.totallygroup.model"})
public class TestConfig {

    @Bean
    public WebDriver webDriver() {
        String browser = System.getProperty("browser", "chrome");
        return BrowserFactory.createDriver(browser);  // Use the factory to create the driver
    }
}