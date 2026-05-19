package tests;

import base.BaseTest;
import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.NavBarComponent;

import java.util.Set;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginPageTitleContainsInCharge() {
        navigateTo("/login");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("InCharge"),
                "Login page title should contain 'InCharge', was: " + title);
    }

    @Test
    public void testLoginPageDisplaysSignInAndHelperButtons() {
        navigateTo("/login");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isSignInButtonPresent(),
                "Sign In button should be present on the login page");
        Assert.assertTrue(loginPage.isForgotPasswordButtonPresent(),
                "Forgot password button should be present on the login page");
        Assert.assertTrue(loginPage.isCreateAccountButtonPresent(),
                "Create account button should be present on the login page");
    }

    @Test
    public void testLoginWithValidCredentialsNavigatesToDashboard() {
        navigateTo("/login");
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard = loginPage.loginAs(
                TestConfig.getUserEmail(),
                TestConfig.getUserPassword());
        Assert.assertTrue(dashboard.isDashboardDisplayed(),
                "Student Dashboard heading should appear after successful login");
    }

    @Test
    public void testLoginWithInvalidCredentialsShowsError() {
        navigateTo("/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("invalid@nowhere.com");
        loginPage.enterPassword("wrongpassword999");
        loginPage.clickSignIn();
        Assert.assertTrue(loginPage.isLoginErrorDisplayed(),
                "An error notification should appear for invalid credentials");
    }

    @Test(dependsOnMethods = "testLoginWithValidCredentialsNavigatesToDashboard")
    public void testLogoutRedirectsBackToLoginPage() {
        navigateTo("/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(TestConfig.getUserEmail(), TestConfig.getUserPassword());

        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickLogout();

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "After logout the URL should redirect to /login");
        Assert.assertTrue(new LoginPage(driver).isSignInButtonPresent(),
                "Sign In button should be visible again after logout");
    }

    @Test
    public void testSessionCookiesAreSetAfterLogin() {
        navigateTo("/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(TestConfig.getUserEmail(), TestConfig.getUserPassword());

        Set<Cookie> cookies = driver.manage().getCookies();
        Assert.assertFalse(cookies.isEmpty(),
                "Session cookies should be present after a successful login");

        // Add, retrieve and delete a custom cookie to verify cookie manipulation
        Cookie testCookie = new Cookie("selenium_test_cookie", "test_value");
        driver.manage().addCookie(testCookie);

        Cookie retrieved = driver.manage().getCookieNamed("selenium_test_cookie");
        Assert.assertNotNull(retrieved, "Added cookie should be retrievable");
        Assert.assertEquals(retrieved.getValue(), "test_value",
                "Cookie value should match what was set");

        driver.manage().deleteCookie(retrieved);
        Assert.assertNull(driver.manage().getCookieNamed("selenium_test_cookie"),
                "Cookie should no longer exist after deletion");
    }

    @Test
    public void testLoginFormAcceptsEmailAndPasswordInputTypes() {
        navigateTo("/login");
        // Verifies that both the email input and the password input are fillable
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("test@example.com");
        loginPage.enterPassword("somepassword");

        // Verify the email field retains its value (type="email" or type="text")
        String emailValue = driver.findElement(
                By.xpath("//input[@placeholder='your.name@organization.com']"))
                .getAttribute("value");
        Assert.assertEquals(emailValue, "test@example.com",
                "Email input should hold the typed value");
    }
}
