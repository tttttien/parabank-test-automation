package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenNewAccountPage extends BasePage {

    private final By accountTypeSelect = By.id("type");
    private final By fromAccountSelect = By.id("fromAccountId");
    private final By openAccountButton = By.cssSelector("input[value='Open New Account']");
    private final By newAccountIdResult = By.id("newAccountId");
    private final By openAccountResultPanel = By.id("openAccountResult");

    public OpenNewAccountPage(WebDriver driver) {
        super(driver);
    }

    public void goTo(String baseUrl) {
        driver.get(baseUrl + "/openaccount.htm");
    }

    public void selectAccountType(String accountType) {
        Select select = new Select(waitVisible(accountTypeSelect));
        select.selectByVisibleText(accountType);
    }

    public void selectFromAccountByIndex(int index) {
        WebElement selectElement = waitVisible(fromAccountSelect);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> new Select(selectElement).getOptions().size() > index);

        new Select(selectElement).selectByIndex(index);
    }

    public void submitOpenAccount() {
        click(openAccountButton);
    }

    public boolean isAccountCreated() {
        return isDisplayed(openAccountResultPanel);
    }

    public String getNewAccountId() {
        return getText(newAccountIdResult);
    }
}

