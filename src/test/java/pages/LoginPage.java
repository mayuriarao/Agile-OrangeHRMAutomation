package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By usernameField = By.name("username");
    By passwordField = By.name("password");
    By loginButton   = By.xpath("//button[@type='submit']");
    By dashboard     = By.xpath("//h6[text()='Dashboard']");
   // By errorMessage  = By.xpath("//p[contains(text(), 'Invalid credentials')]");
    By errorMessage  = By.xpath("//p[contains(., 'Invalid') and contains(., 'credentials')]");

    By userDropdown = By.xpath("//p[@class='oxd-userdropdown-name']");
    By logoutLink   = By.xpath("//a[text()='Logout']");

    // Actions
    public void enterUsername(String username) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        usernameElement.clear();
        usernameElement.sendKeys(username);

        /*driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);*/
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isDashboardVisible() {
        return !driver.findElements(dashboard).isEmpty();
    }

    public boolean isLoginErrorVisible() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public void loginToHRM(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void logout() {
        driver.findElement(userDropdown).click();
        driver.findElement(logoutLink).click();
    }
}
