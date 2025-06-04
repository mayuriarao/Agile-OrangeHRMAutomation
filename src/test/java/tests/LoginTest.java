package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.ExcelUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/LoginTestReport.html");
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

    }

    @Test
    public void loginDataDrivenTest() throws IOException {
        List<String[]> credentials = ExcelUtil.readLoginData("Excel/loginData.xlsx", "LoginData");

        for (String[] data : credentials) {
            String username = data[0];
            String password = data[1];

            test = extent.createTest("Login Test: " + username + " / " + password);
            loginPage.loginToHRM(username, password);

            // Wait briefly for UI response (not ideal; will optimize later)
            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

            takeScreenshot(username + "_" + password);

            if (username.equals("Admin") && password.equals("admin123")) {
                if (loginPage.isDashboardVisible()) {
                    test.pass("Valid login successful.");
                    loginPage.logout();
                    test.info("Logged out successfully.");
                } else {
                    test.fail("Valid login failed unexpectedly.");
                }
            } else {
                if (loginPage.isLoginErrorVisible()) {
                    test.pass("Invalid login failed as expected.");
                } else {
                    test.fail("Invalid login passed unexpectedly.");
                }
            }

            driver.get("https://opensource-demo.orangehrmlive.com/"); // Reset to login page
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

    public void takeScreenshot(String filename) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + filename + ".png");
        dest.getParentFile().mkdirs();
        try (FileOutputStream out = new FileOutputStream(dest)) {
            out.write(java.nio.file.Files.readAllBytes(src.toPath()));
        }
    }
}
