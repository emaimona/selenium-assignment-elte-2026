package base;

import config.TestConfig;
import listeners.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.net.MalformedURLException;
import java.net.URL;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    public void setUpDriver() throws MalformedURLException {
        ChromeOptions options = buildChromeOptions();
        driver = new RemoteWebDriver(new URL(TestConfig.getSeleniumGridUrl()), options);
        driver.manage().window().maximize();
    }

    private ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (TestConfig.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36");
        return options;
    }

    @AfterMethod
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void navigateTo(String path) {
        driver.get(TestConfig.getBaseUrl() + path);
    }
}
