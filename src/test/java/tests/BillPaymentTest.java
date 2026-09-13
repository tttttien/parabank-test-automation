package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BillPayPage;

public class BillPaymentTest extends BaseTest {
    @Test
    public void testPayBillSuccessfully() {
        loginAsTestUser();
        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.goTo(BASE_URL);
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TITLE: " + driver.getTitle());

        billPayPage.fillPayeeForm(
                "Electric Co",
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "5551234567",
                "123456",
                "123456",
                "50"
        );
        System.out.println(
                driver.findElements(By.id("fromAccountId")).size()
        );
        billPayPage.selectFromAccountByIndex(0);
        billPayPage.submitPayment();

        Assert.assertTrue(billPayPage.isPaymentSuccessful(),
                "Expected the bill payment confirmation panel to be displayed");
    }

    @Test
    public void testMissingPayeeNameShowsValidationError() {
        loginAsTestUser();
        BillPayPage billPayPage = new BillPayPage(driver);

        billPayPage.goTo(BASE_URL);
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TITLE: " + driver.getTitle());

        billPayPage.fillPayeeForm(
                null,
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "5551234567",
                "123456",
                "123456",
                "50"
        );
        billPayPage.selectFromAccountByIndex(0);
        billPayPage.submitPayment();

        Assert.assertFalse(billPayPage.isPaymentSuccessful(),
                "Expected the payment to be rejected due to missing payee name");
    }

    @Test
    public void testAccountVerifyMismatchShowsError() {
        loginAsTestUser();
        BillPayPage billPayPage = new BillPayPage(driver);

        billPayPage.goTo(BASE_URL);
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TITLE: " + driver.getTitle());

        billPayPage.fillPayeeForm(
                "Electric Co",
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "5551234567",
                "12345",
                "54321",
                "50"
        );
        billPayPage.selectFromAccountByIndex(0);
        billPayPage.submitPayment();

        Assert.assertFalse(billPayPage.isPaymentSuccessful  (),
                "Expected the payment to be rejected due to account/verify-account mismatch");
    }

    @Test
    public void testPayBillWithExceedingAmountBalance() {
        loginAsTestUser();
        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.goTo(BASE_URL);
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TITLE: " + driver.getTitle());

        billPayPage.fillPayeeForm(
                "Electric Co",
                "123 Main St",
                "Springfield",
                "IL",
                "62704",
                "5551234567",
                "123456",
                "123456",
                "999999"
        );
        System.out.println(
                driver.findElements(By.id("fromAccountId")).size()
        );
        billPayPage.selectFromAccountByIndex(0);
        billPayPage.submitPayment();

        Assert.assertFalse(billPayPage.isPaymentSuccessful(),
                "Expected the payment to be rejected due to the exceeding money");
    }


}
