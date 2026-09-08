package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("input[value='Log In']");
    private final By loginErrorMessage = By.cssSelector(".error, #rightPanel p.error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void goTo(String baseUrl) {
        driver.get(baseUrl + "/index.htm");
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public boolean isLoginErrorDisplayed() {
        return isDisplayed(loginErrorMessage);
    }

    public String getLoginErrorText() {
        return getText(loginErrorMessage);
    }


}
