package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static final Properties properties = new Properties();

    static {
        String gridUrlFromEnv = System.getenv("SELENIUM_GRID_URL");
        String headlessFromEnv = System.getenv("HEADLESS");

        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("test.properties")) {
            if (input == null) {
                throw new RuntimeException("test.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test.properties", e);
        }

        if (gridUrlFromEnv != null) {
            properties.setProperty("selenium.grid.url", gridUrlFromEnv);
        }
        if (headlessFromEnv != null) {
            properties.setProperty("headless", headlessFromEnv);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getUserEmail() {
        return properties.getProperty("user.email");
    }

    public static String getUserPassword() {
        return properties.getProperty("user.password");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static String getSeleniumGridUrl() {
        return properties.getProperty("selenium.grid.url");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "true"));
    }
}
