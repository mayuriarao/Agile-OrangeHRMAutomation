# OrangeHRM Automation Testing Project

This is a capstone automation project for the **Human Resource Management (HRM)** domain using the OrangeHRM open-source demo site. The project automates key functionalities such as login, logout, and admin user management using Selenium, TestNG, Apache POI, and ExtentReports.

---

## ✅ Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Apache POI (Excel handling)
- ExtentReports (HTML reporting)
- Maven
- WebDriverManager
- Page Object Model (POM)

---

##  Test Scenarios

✅ Scenario 1 – Login Functionality (Excel Data-Driven)

- Login using 5 data sets (1 valid, 4 invalid) from Excel
- Capture screenshots
- Generate Extent HTML report

✅ Scenario 2 – Admin Page (Search and Navigation)

- Count and list all left menu items
- Search user by:
  - Username
  -  User Role
  -  Status
  -   Report results

---

##  Setup Instructions

1. **Clone or Download** the repository
2. Ensure you have **JDK 11 or 17** installed
3. Run `mvn clean install` to download dependencies
4. Create the required folders:
   - `Excel/` (place `loginData.xlsx` inside)
5. Open the project in IntelliJ or Eclipse

---

## Running Tests

### Run from terminal:
```bash
mvn test
```
