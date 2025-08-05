package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load config.properties", ex);
        }
    }

    public static String getBaseUrl() {
        String envUrl = System.getenv("BASE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            System.out.println("Using BASE_URL from environment: " + envUrl);
            return envUrl;
        }
        String propUrl = properties.getProperty("base.url");
        System.out.println("Using BASE_URL from properties: " + propUrl);
        return propUrl;
    }
}