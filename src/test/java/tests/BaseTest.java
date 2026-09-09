package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public abstract class BaseTest {
    protected static final String BASE_URL = "https://parabank.parasoft.com/parabank";
    protected static final String USERNAME = "tttttien";
    protected static final String PASSWORD = "Demcomdilam123";

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Logs in with the shared test account and leaves the browser on the account overview page. */
    protected void loginAsTestUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo(BASE_URL);
        loginPage.login(USERNAME, PASSWORD);
    }
}
