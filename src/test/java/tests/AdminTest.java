package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.AdminPage;
import pages.LoginPage;

public class AdminTest {

    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/AdminTestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");

        loginPage = new LoginPage(driver);
        loginPage.loginToHRM("Admin", "admin123");
        adminPage = new AdminPage(driver);
        adminPage.goToAdminPage();
    }

    @Test(priority = 1)
    public void testCountLeftMenuOptions() {
        test = extent.createTest("Count Left Menu Options");
        int count = adminPage.getLeftMenuOptionCount();
        adminPage.printLeftMenuItems();
        test.info("Found " + count + " menu items.");
        if (count == 12) {
            test.pass("Menu count is correct.");
        } else {
            test.fail("Expected 12 menu items but found " + count);
        }
    }

    @Test(priority = 2)
    public void testSearchByUsername() {
        test = extent.createTest("Search by Username: Admin");
        adminPage.searchByUsername("Admin");
        int results = adminPage.getResultCount();
        test.info("Found " + results + " records.");
        adminPage.resetSearch();
        if (results > 0) {
            test.pass("Search by username successful.");
        } else {
            test.fail("No records found for username.");
        }
    }

    @Test(priority = 3)
    public void testSearchByUserRole() throws InterruptedException {
        test = extent.createTest("Search by User Role: Admin");
        adminPage.searchByUserRole("Admin");
        int results = adminPage.getResultCount();
        test.info("Found " + results + " records.");
        adminPage.resetSearch();
        if (results > 0) {
            test.pass("Search by role successful.");
        } else {
            test.fail("No records found for role.");
        }
    }

    @Test(priority = 4)
    public void testSearchByStatus() throws InterruptedException {
        test = extent.createTest("Search by Status: Enabled");
        adminPage.searchByStatus("Enabled");
        int results = adminPage.getResultCount();
        test.info("Found " + results + " records.");
        if (results > 0) {
            test.pass("Search by status successful.");
        } else {
            test.fail("No records found for status.");
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}
