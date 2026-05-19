package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavBarComponent extends BasePage {

    private static final By LOGO_LINK = By.xpath(
            "//a[@href='/']//img[@alt='Incharge Logo']");

    private static final By USER_MENU_BUTTON = By.xpath(
            "//button[.//*[normalize-space()='Test User 1']]");

    private static final By SETTINGS_MENU_ITEM = By.xpath(
            "//*[@role='menuitem' and .//*[normalize-space()='Settings']]");

    private static final By LOGOUT_MENU_ITEM = By.xpath(
            "//*[@role='menuitem' and .//*[normalize-space()='Logout']]");

    private static final By NOTIFICATIONS_DIALOG = By.xpath(
            "//div[@role='dialog' and .//div[contains(text(),'Notifications')]]");

    public NavBarComponent(WebDriver driver) {
        super(driver);
    }

    public void clickLogo() {
        waitForClickable(LOGO_LINK).click();
    }

    public void openUserMenu() {
        waitForClickable(USER_MENU_BUTTON).click();
    }

    public void clickSettings() {
        openUserMenu();
        waitForClickable(SETTINGS_MENU_ITEM).click();
    }

    public void clickLogout() {
        openUserMenu();
        waitForClickable(LOGOUT_MENU_ITEM).click();
    }

    public boolean isUserMenuVisible() {
        return isElementPresent(USER_MENU_BUTTON);
    }

    public void hoverOverUserMenuButton() {
        WebElement button = waitForVisible(USER_MENU_BUTTON);
        new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(button)
                .perform();
    }
}
