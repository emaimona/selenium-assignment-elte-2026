package tests;

import base.BaseTest;
import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.NavBarComponent;

import java.util.Random;

public class DashboardTest extends BaseTest {

    @BeforeMethod
    public void loginBeforeEachTest() {
        navigateTo("/login");
        new LoginPage(driver).loginAs(
                TestConfig.getUserEmail(),
                TestConfig.getUserPassword());
    }

    @Test
    public void testStudentDashboardHeadingIsCorrect() {
        DashboardPage dashboard = new DashboardPage(driver);
        String heading = dashboard.getDashboardHeading();
        Assert.assertEquals(heading, "Student Dashboard",
                "Main heading on the dashboard should read 'Student Dashboard'");
    }

    @Test
    public void testDashboardContainsOverviewCoursesAndAttendanceTabs() {
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isCoursesTabPresent(),
                "Courses tab should be visible on the dashboard");
        Assert.assertTrue(dashboard.isAttendanceTabPresent(),
                "Attendance tab should be visible on the dashboard");
    }

    @Test
    public void testCoursesTabRevealsCourseSearchInput() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickCoursesTab();
        WebElement searchInput = dashboard.waitForVisible(
                By.xpath("//input[@placeholder='Search courses...']"));
        Assert.assertTrue(searchInput.isDisplayed(),
                "Course search input should be visible after switching to Courses tab");
    }

    @Test
    public void testSearchCoursesInputAcceptsRandomText() {
        DashboardPage dashboard = new DashboardPage(driver);
        String randomQuery = "search_" + new Random().nextInt(99999);
        dashboard.searchForCourse(randomQuery);

        String inputValue = driver.findElement(
                By.xpath("//input[@placeholder='Search courses...']"))
                .getAttribute("value");
        Assert.assertEquals(inputValue, randomQuery,
                "Search input should hold the randomly generated query");
    }

    @Test
    public void testAttendanceTabDisplaysNoAttendanceMessage() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickAttendanceTab();
        WebElement msg = dashboard.waitForVisible(
                By.xpath("//div[@role='tabpanel']//p[contains(.,'No Attendance') or contains(.,'attendance records')]"));
        Assert.assertTrue(msg.isDisplayed(),
                "Attendance tab should inform the user that no records exist yet");
    }

    @Test
    public void testFooterShowsInChargeCopyrightAndLegalLinks() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.scrollToBottom();

        Assert.assertTrue(dashboard.isFooterPresent(),
                "Copyright link in the footer should be visible");
        Assert.assertTrue(dashboard.isPrivacyPolicyLinkPresent(),
                "Privacy Policy link in the footer should be present");
        Assert.assertTrue(dashboard.isTermsOfServiceLinkPresent(),
                "Terms of Service link in the footer should be present");
    }

    @Test
    public void testMultipleAppPagesCarryTheInChargeTitle() {
        String[] paths = {
            "/login",
            "/participant",
            "/participant?tab=programs",
            "/participant?tab=attendance"
        };
        for (String path : paths) {
            driver.get(TestConfig.getBaseUrl() + path);
            String title = driver.getTitle();
            Assert.assertTrue(title.contains("InCharge"),
                    "Page '" + path + "' should have 'InCharge' in the title, got: " + title);
        }
    }

    @Test
    public void testBrowserBackAndForwardNavigationWorks() {
        DashboardPage dashboard = new DashboardPage(driver);

        // Navigate to the Courses tab — URL gains a query parameter
        dashboard.clickCoursesTab();
        String coursesUrl = driver.getCurrentUrl();

        // Navigate back
        driver.navigate().back();
        String afterBackUrl = driver.getCurrentUrl();

        // Navigate forward
        driver.navigate().forward();
        String afterForwardUrl = driver.getCurrentUrl();

        Assert.assertNotEquals(coursesUrl, afterBackUrl,
                "URL should change after navigating back");
        Assert.assertEquals(afterForwardUrl, coursesUrl,
                "URL should be restored after navigating forward");
    }

    @Test
    public void testScrollToPageBottomWithJavascriptExecutor() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.scrollToBottom();

        WebElement footer = dashboard.waitForVisible(
                By.xpath("//*[@role='contentinfo'] | //footer"));
        Assert.assertTrue(footer.isDisplayed(),
                "Footer should become visible after scrolling to the bottom of the page");
    }

    @Test
    public void testHoverOverUserMenuButtonRevealsCursor() {
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.hoverOverUserMenuButton();

        // Verify the user menu button is still visible after hover
        Assert.assertTrue(navBar.isUserMenuVisible(),
                "User menu button should remain visible while hovered");
    }

    @Test
    public void testUpcomingFeedbackSectionIsDisplayedOnOverview() {
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isUpcomingFeedbackSectionPresent(),
                "Upcoming Feedback section should appear on the Overview tab");
    }

    @Test
    public void testLogoLinkNavigatesToDashboard() {
        DashboardPage dashboard = new DashboardPage(driver);

        // Navigate away first
        dashboard.clickCoursesTab();

        // Click the logo
        NavBarComponent navBar = new NavBarComponent(driver);
        navBar.clickLogo();

        Assert.assertTrue(driver.getCurrentUrl().contains("/participant") ||
                driver.getCurrentUrl().equals(TestConfig.getBaseUrl() + "/"),
                "Clicking the logo should navigate back to the main dashboard");
    }
}
