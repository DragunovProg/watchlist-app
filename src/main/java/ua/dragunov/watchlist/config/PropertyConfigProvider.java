package ua.dragunov.watchlist.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConfigProvider {
    private static final String PROPERTY_PATH = "config.properties";

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try(InputStream stream = PropertyConfigProvider.class.getClassLoader().getResourceAsStream(PropertyConfigProvider.PROPERTY_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    public static Properties getPropertyConfig() {
        return loadProperties();
    }
}
