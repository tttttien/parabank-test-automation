package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class BillPayPage extends BasePage{
    private final By payeeName = By.name("payee.name");
    private final By payeeAddress = By.name("payee.address.street");
    private final By payeeCity = By.name("payee.address.city");
    private final By payeeState = By.name("payee.address.state");
    private final By payeeZip = By.name("payee.address.zipCode");
    private final By payeePhone = By.name("payee.phoneNumber");
    private final By payeeAccount = By.name("payee.accountNumber");
    private final By verifyAccount = By.name("verifyAccount");
    private final By amountField = By.name("amount");
    private final By fromAccountSelect = By.name("fromAccountId");;
    private final By sendPaymentButton = By.cssSelector("input[value='Send Payment']");
    private final By billPayResultPanel = By.id("billpayResult");
    private final By errorMessages = By.cssSelector(".error");

    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    public void goTo(String baseUrl) {
        driver.get(baseUrl + "/billpay.htm");
    }

    public void fillPayeeForm(String name, String address, String city, String state,
                              String zip, String phone, String account, String verifyAcc,
                              String amount) {
        if (name != null) type(payeeName, name);
        if (address != null) type(payeeAddress, address);
        if (city != null) type(payeeCity, city);
        if (state != null) type(payeeState, state);
        if (zip != null) type(payeeZip, zip);
        if (phone != null) type(payeePhone, phone);
        if (account != null) type(payeeAccount, account);
        if (verifyAcc != null) type(verifyAccount, verifyAcc);
        if (amount != null) type(amountField, amount);
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

    public void submitPayment() {
        click(sendPaymentButton);
    }

    public boolean isPaymentSuccessful() {
        return isDisplayed(billPayResultPanel);
    }

    public boolean hasValidationErrors() {
        return isDisplayed(errorMessages);
    }
}
