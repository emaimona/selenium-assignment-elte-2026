package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage extends BasePage {

    private static final By STUDENT_DASHBOARD_HEADING = By.xpath(
            "//h1[normalize-space()='Student Dashboard']");

    private static final By OVERVIEW_TAB = By.xpath(
            "//div[@role='tablist']//button[@role='tab' and normalize-space()='Overview']");

    private static final By COURSES_TAB = By.xpath(
            "//div[@role='tablist']//button[@role='tab' and normalize-space()='Courses']");

    private static final By ATTENDANCE_TAB = By.xpath(
            "//div[@role='tablist']//button[@role='tab' and normalize-space()='Attendance']");

    private static final By COURSE_SEARCH_INPUT = By.xpath(
            "//input[@placeholder='Search courses...']");

    private static final By NO_ATTENDANCE_MESSAGE = By.xpath(
            "//div[@role='tabpanel']//p[contains(text(),'No Attendance') or contains(text(),'attendance records')]");

    private static final By FOOTER_COPYRIGHT = By.xpath(
            "//*[@role='contentinfo']//a[contains(@href,'incharge.education')]");

    private static final By PRIVACY_POLICY_LINK = By.xpath(
            "//*[@role='contentinfo']//a[normalize-space()='Privacy Policy']");

    private static final By TERMS_OF_SERVICE_LINK = By.xpath(
            "//*[@role='contentinfo']//a[normalize-space()='Terms of Service']");

    private static final By UPCOMING_FEEDBACK_SECTION = By.xpath(
            "//h2[.//text()[contains(.,'Upcoming Feedback')]]");

    private static final By ACTIVE_COURSES_STAT = By.xpath(
            "//h3[normalize-space()='Active Courses']/ancestor::div[2]//div[normalize-space()='0']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardDisplayed() {
        return isElementPresent(STUDENT_DASHBOARD_HEADING);
    }

    public String getDashboardHeading() {
        return waitForVisible(STUDENT_DASHBOARD_HEADING).getText();
    }

    public void clickCoursesTab() {
        waitForClickable(COURSES_TAB).click();
    }

    public void clickAttendanceTab() {
        waitForClickable(ATTENDANCE_TAB).click();
    }

    public void clickOverviewTab() {
        waitForClickable(OVERVIEW_TAB).click();
    }

    public void searchForCourse(String query) {
        clickCoursesTab();
        WebElement searchInput = waitForVisible(COURSE_SEARCH_INPUT);
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public boolean isCoursesTabPresent() {
        return isElementPresent(COURSES_TAB);
    }

    public boolean isAttendanceTabPresent() {
        return isElementPresent(ATTENDANCE_TAB);
    }

    public boolean isNoAttendanceMessageDisplayed() {
        return isElementPresent(NO_ATTENDANCE_MESSAGE);
    }

    public boolean isFooterPresent() {
        return isElementPresent(FOOTER_COPYRIGHT);
    }

    public String getFooterCopyrightText() {
        return waitForVisible(FOOTER_COPYRIGHT).getText();
    }

    public boolean isPrivacyPolicyLinkPresent() {
        return isElementPresent(PRIVACY_POLICY_LINK);
    }

    public boolean isTermsOfServiceLinkPresent() {
        return isElementPresent(TERMS_OF_SERVICE_LINK);
    }

    public boolean isUpcomingFeedbackSectionPresent() {
        return isElementPresent(UPCOMING_FEEDBACK_SECTION);
    }

    public NavBarComponent getNavBar() {
        return new NavBarComponent(driver);
    }

    public WebElement waitForVisible(By locator) {
        return super.waitForVisible(locator);
    }
}
