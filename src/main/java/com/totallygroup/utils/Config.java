

package com.totallygroup.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    public static final Logger logger = Logger.getLogger(Config.class.getName());  // Change to public
    private static Properties properties = null;
    private static final String DEFAULT_CONFIG_PATH = "src/test/resources/config/config.properties";

    private Config() {
        // Private constructor to prevent instantiation
    }

    public static synchronized void loadConfig(String configFilePath) {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream configFile = new FileInputStream(
                    configFilePath != null ? configFilePath : DEFAULT_CONFIG_PATH)) {
                properties.load(configFile);
                logger.info("Configuration loaded successfully.");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error loading config file: {0}", e.getMessage());
                throw new RuntimeException("Error loading config file: " + e.getMessage());
            }
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            loadConfig(null);
        }
        return properties.getProperty(key);
    }

    public static String getPropertyWithException(String key) {
        String value = getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Property not found or empty: " + key);
        }
        return value;
    }
}
