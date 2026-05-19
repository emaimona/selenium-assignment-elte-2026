package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SettingsPage extends BasePage {

    private static final By SETTINGS_DIALOG = By.xpath(
            "//div[@role='dialog' and .//h2[normalize-space()='Settings']]");

    private static final By GENERAL_TAB = By.xpath(
            "//div[@role='dialog']//button[@role='tab' and normalize-space()='General']");

    private static final By SECURITY_TAB = By.xpath(
            "//div[@role='dialog']//button[@role='tab' and normalize-space()='Security']");

    private static final By EMAIL_NOTIFICATIONS_TOGGLE = By.xpath(
            "//div[@role='dialog']//button[@role='switch']");

    private static final By SAVE_CHANGES_BUTTON = By.xpath(
            "//div[@role='dialog']//button[normalize-space()='Save Changes']");

    private static final By CLOSE_BUTTON = By.xpath(
            "//div[@role='dialog']//button[.//*[normalize-space()='Close']]");

    private static final By CURRENT_PASSWORD = By.xpath(
            "//div[@role='dialog']//div[@role='tabpanel']//input[@type='password'][1]");

    private static final By NEW_PASSWORD = By.xpath(
            "//div[@role='dialog']//div[@role='tabpanel']//input[@type='password'][2]");

    private static final By CONFIRM_PASSWORD = By.xpath(
            "//div[@role='dialog']//div[@role='tabpanel']//input[@type='password'][3]");

    private static final By CHANGE_PASSWORD_BUTTON = By.xpath(
            "//div[@role='dialog']//button[normalize-space()='Change Password']");

    private static final By LANGUAGE_DROPDOWN_BUTTON = By.xpath(
            "//div[@role='dialog']//div[@role='tabpanel']//button[@aria-haspopup or @aria-expanded][1]");

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSettingsDialogOpen() {
        return isElementPresent(SETTINGS_DIALOG);
    }

    public void clickSecurityTab() {
        waitForClickable(SECURITY_TAB).click();
    }

    public void clickGeneralTab() {
        waitForClickable(GENERAL_TAB).click();
    }

    public void clickEmailNotificationsToggle() {
        waitForClickable(EMAIL_NOTIFICATIONS_TOGGLE).click();
    }

    public String getEmailToggleState() {
        return waitForVisible(EMAIL_NOTIFICATIONS_TOGGLE).getAttribute("aria-checked");
    }

    public void clickSaveChanges() {
        waitForClickable(SAVE_CHANGES_BUTTON).click();
    }

    public void close() {
        waitForClickable(CLOSE_BUTTON).click();
    }

    public boolean isCurrentPasswordFieldVisible() {
        return isElementPresent(CURRENT_PASSWORD);
    }

    public boolean isNewPasswordFieldVisible() {
        return isElementPresent(NEW_PASSWORD);
    }

    public boolean isConfirmPasswordFieldVisible() {
        return isElementPresent(CONFIRM_PASSWORD);
    }

    public void fillCurrentPassword(String password) {
        WebElement input = waitForVisible(CURRENT_PASSWORD);
        input.clear();
        input.sendKeys(password);
    }

    public void fillNewPassword(String password) {
        WebElement input = waitForVisible(NEW_PASSWORD);
        input.clear();
        input.sendKeys(password);
    }

    public void fillConfirmPassword(String password) {
        WebElement input = waitForVisible(CONFIRM_PASSWORD);
        input.clear();
        input.sendKeys(password);
    }

    public void clickChangePassword() {
        waitForClickable(CHANGE_PASSWORD_BUTTON).click();
    }

    public void openLanguageDropdown() {
        waitForClickable(LANGUAGE_DROPDOWN_BUTTON).click();
    }
}
