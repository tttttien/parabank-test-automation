package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class TransferFundsPage extends BasePage {
    private final By amountField = By.id("amount");
    private final By fromAccountSelect = By.id("fromAccountId");
    private final By toAccountSelect = By.id("toAccountId");
    private final By transferButton = By.cssSelector("input[value='Transfer']");
    private final By transferResultPanel = By.id("showResult");
    private final By transferErrorMessage = By.cssSelector(".error");

    public TransferFundsPage (WebDriver driver) {
        super(driver);
    }

    public void goTo(String baseUrl) {
        driver.get(baseUrl + "/transfer.htm");
    }

    public void enterAmount(String amount) {
        type(amountField, amount);
    }

    public void selectFromAccountByIndex(int index) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(fromAccountSelect)
        );

        wait.until(driver ->
                new Select(element).getOptions().size() > index
        );

        new Select(element).selectByIndex(index);
    }

    public void selectToAccountByIndex(int index) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(toAccountSelect)
        );

        wait.until(driver ->
                new Select(element).getOptions().size() > index
        );

        new Select(element).selectByIndex(index);
    }

    public void submitTransfer() {
        click(transferButton);
    }

    public boolean isTransferSuccessful() {
        return isDisplayed(transferResultPanel);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(transferErrorMessage);
    }
}
