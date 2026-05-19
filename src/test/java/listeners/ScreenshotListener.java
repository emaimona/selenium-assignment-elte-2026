package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {

    private static final String SCREENSHOT_DIR = "build/screenshots";

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = extractDriver(result);
        if (driver instanceof TakesScreenshot) {
            saveScreenshot((TakesScreenshot) driver, result.getName());
        }
    }

    private WebDriver extractDriver(ITestResult result) {
        Object instance = result.getInstance();
        try {
            return (WebDriver) instance.getClass()
                    .getField("driver")
                    .get(instance);
        } catch (Exception e) {
            try {
                return (WebDriver) instance.getClass()
                        .getSuperclass()
                        .getDeclaredField("driver")
                        .get(instance);
            } catch (Exception ex) {
                System.err.println("Could not obtain WebDriver for screenshot: " + ex.getMessage());
                return null;
            }
        }
    }

    private void saveScreenshot(TakesScreenshot driver, String testName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = SCREENSHOT_DIR + "/" + testName + "_" + timestamp + ".png";
            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(filePath), screenshot);
            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onFinish(ITestContext context) {}
    @Override public void onStart(ITestContext context) {}
}
