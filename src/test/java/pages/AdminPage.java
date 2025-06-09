package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {

    WebDriver driver;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    By sideMenuItems = By.cssSelector(".oxd-sidepanel-body .oxd-main-menu li span");
    By adminMenu     = By.xpath("//span[text()='Admin']");
    By usernameField = By.xpath("//label[text()='Username']/../following-sibling::div/input");
    By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[contains(@class,'oxd-select-text')]");
    By statusDropdown   = By.xpath("//label[text()='Status']/../following-sibling::div//div[contains(@class,'oxd-select-text')]");
    By dropdownOptions  = By.xpath("//div[@role='listbox']//span");
    By searchButton     = By.xpath("//button[@type='submit']");
    By resetButton      = By.xpath("//button[contains(.,'Reset')]");
    By resultRows       = By.cssSelector(".oxd-table-body .oxd-table-row");

    public void goToAdminPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminMenu));
        driver.findElement(adminMenu).click();
    }

    public int getLeftMenuOptionCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(sideMenuItems));

        List<WebElement> items = driver.findElements(sideMenuItems);
        return items.size();
    }

    public void printLeftMenuItems() {
        List<WebElement> items = driver.findElements(sideMenuItems);
        for (WebElement item : items) {
            System.out.println("Menu: " + item.getText());
        }
    }

    public void searchByUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(usernameField));

        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(searchButton).click();
    }

    public void searchByUserRole(String roleText) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(userRoleDropdown));

        driver.findElements(userRoleDropdown).get(0).click();
        Thread.sleep(500);
        selectFromDropdown(roleText);
        driver.findElement(searchButton).click();
    }

    public void searchByStatus(String statusText) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(statusDropdown));

        driver.findElements(statusDropdown).get(0).click();
        Thread.sleep(500);
        selectFromDropdown(statusText);
        driver.findElement(searchButton).click();
    }

    private void selectFromDropdown(String optionText) {
        List<WebElement> options = driver.findElements(dropdownOptions);
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(optionText)) {
                option.click();
                break;
            }
        }
    }

    public int getResultCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultRows));

        return driver.findElements(resultRows).size();
    }

    public void resetSearch() {
        driver.findElement(resetButton).click();
    }
}
