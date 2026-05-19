package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.xpath(
            "//input[@placeholder='your.name@organization.com']");

    private static final By PASSWORD_INPUT = By.xpath(
            "//input[@type='password'][1]");

    private static final By SIGN_IN_BUTTON = By.xpath(
            "//button[normalize-space()='Sign In']");

    private static final By FORGOT_PASSWORD_BUTTON = By.xpath(
            "//button[normalize-space()='Forgot password?']");

    private static final By CREATE_ACCOUNT_BUTTON = By.xpath(
            "//button[normalize-space()='Create an account']");

    private static final By LOGIN_ERROR = By.xpath(
            "//*[contains(text(),'Login failed') or contains(text(),'Invalid') or contains(text(),'Error')]");

    private static final By LANGUAGE_SELECTOR = By.xpath(
            "//div[contains(@class,'language') or @role='combobox'][1]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        WebElement input = waitForVisible(EMAIL_INPUT);
        input.clear();
        input.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement input = waitForVisible(PASSWORD_INPUT);
        input.clear();
        input.sendKeys(password);
    }

    public void clickSignIn() {
        waitForClickable(SIGN_IN_BUTTON).click();
    }

    public DashboardPage loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        return new DashboardPage(driver);
    }

    public boolean isSignInButtonPresent() {
        return isElementPresent(SIGN_IN_BUTTON);
    }

    public boolean isForgotPasswordButtonPresent() {
        return isElementPresent(FORGOT_PASSWORD_BUTTON);
    }

    public boolean isCreateAccountButtonPresent() {
        return isElementPresent(CREATE_ACCOUNT_BUTTON);
    }

    public boolean isLoginErrorDisplayed() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return isElementPresent(LOGIN_ERROR);
    }
}
