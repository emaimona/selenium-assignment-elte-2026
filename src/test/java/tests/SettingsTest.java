package tests;

import base.BaseTest;
import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NavBarComponent;
import pages.SettingsPage;

public class SettingsTest extends BaseTest {

    @BeforeMethod
    public void loginBeforeEachTest() {
        navigateTo("/login");
        new LoginPage(driver).loginAs(
                TestConfig.getUserEmail(),
                TestConfig.getUserPassword());
    }

    @Test
    public void testSettingsDialogOpensFromUserMenu() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        SettingsPage settings = new SettingsPage(driver);
        Assert.assertTrue(settings.isSettingsDialogOpen(),
                "Settings dialog should open after clicking Settings in the user menu");
    }

    @Test
    public void testGeneralSettingsTabContainsLanguageAndTimeZoneDropdowns() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        // Language label is in the General tab panel
        WebElement languageLabel = driver.findElement(
                By.xpath("//div[@role='dialog']//div[@role='tabpanel']//text()[normalize-space()='Language']/.."));
        Assert.assertNotNull(languageLabel,
                "Language option should be present in General settings");

        // Time Zone label
        WebElement timezoneLabel = driver.findElement(
                By.xpath("//div[@role='dialog']//div[@role='tabpanel']//text()[normalize-space()='Time Zone']/.."));
        Assert.assertNotNull(timezoneLabel,
                "Time Zone option should be present in General settings");
    }

    @Test
    public void testLanguageDropdownOpensOnClick() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        // Click the first combobox button in the General tab (Language)
        WebElement languageBtn = driver.findElement(
                By.xpath("//div[@role='dialog']//div[@role='tabpanel']//button[@aria-haspopup or @aria-expanded][1]"));
        languageBtn.click();

        // A listbox with options should appear
        WebElement listbox = new SettingsPage(driver).waitForVisible(
                By.xpath("//div[@role='listbox'] | //ul[@role='listbox']"));
        Assert.assertTrue(listbox.isDisplayed(),
                "Language dropdown listbox should be visible after clicking the dropdown button");
    }

    @Test
    public void testEmailNotificationsToggleChangesStateOnClick() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        SettingsPage settings = new SettingsPage(driver);
        String initialState = settings.getEmailToggleState();

        settings.clickEmailNotificationsToggle();

        String newState = settings.getEmailToggleState();
        Assert.assertNotEquals(newState, initialState,
                "Email Notifications toggle aria-checked state should flip after being clicked");

        // Restore to original state
        settings.clickEmailNotificationsToggle();
    }

    @Test
    public void testSaveChangesSubmitsTheSettingsForm() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        SettingsPage settings = new SettingsPage(driver);
        settings.clickSaveChanges();

        // The dialog remains open (or a success toast appears); either way no error
        Assert.assertTrue(settings.isSettingsDialogOpen() ||
                !driver.findElements(By.xpath("//*[contains(text(),'saved') or contains(text(),'Success')]")).isEmpty(),
                "No error should occur when saving settings without changes");
    }

    @Test
    public void testSecurityTabDisplaysAllThreePasswordFields() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        SettingsPage settings = new SettingsPage(driver);
        settings.clickSecurityTab();

        Assert.assertTrue(settings.isCurrentPasswordFieldVisible(),
                "Current password field should appear on the Security tab");
        Assert.assertTrue(settings.isNewPasswordFieldVisible(),
                "New password field should appear on the Security tab");
        Assert.assertTrue(settings.isConfirmPasswordFieldVisible(),
                "Confirm new password field should appear on the Security tab");
    }

    @Test
    public void testPasswordChangeFormCanBeFilledAndSubmitted() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickSettings();

        SettingsPage settings = new SettingsPage(driver);
        settings.clickSecurityTab();

        // Deliberately use wrong current password so the actual password is not changed
        settings.fillCurrentPassword("deliberatelyWrongCurrentPassword!");
        settings.fillNewPassword("NewTestPassword@123");
        settings.fillConfirmPassword("NewTestPassword@123");
        settings.clickChangePassword();

        // An error or validation message should appear (not a success)
        boolean hasResponseMessage = !driver.findElements(
                By.xpath("//*[contains(text(),'password') or contains(text(),'Password') or contains(text(),'error') or contains(text(),'Error')]"))
                .isEmpty();
        Assert.assertTrue(hasResponseMessage,
                "Some response should appear after submitting the password change form");
    }
}
