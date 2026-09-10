package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TransferFundsPage;

public class FundTransferTest extends BaseTest {
    @Test
    public void testValidTransferSucceeds() {
        loginAsTestUser();
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.goTo(BASE_URL);
        //driver.get(BASE_URL + "/transfer.htm");

        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TITLE: " + driver.getTitle());

        transferPage.enterAmount("100");
        transferPage.selectFromAccountByIndex(2);
        transferPage.selectToAccountByIndex(0);
        transferPage.submitTransfer();

        Assert.assertTrue(transferPage.isTransferSuccessful(),
                "Expected the transfer confirmation panel to be displayed");
    }

    @Test
    public void testZeroAmountTransfer() {
        loginAsTestUser();
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.goTo(BASE_URL);

        transferPage.enterAmount("0");
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.submitTransfer();

        // NOTE: Parabank's demo validation is known to be loose — document the
        // ACTUAL observed behavior as a defect if the transfer is unexpectedly accepted.
        boolean succeeded = transferPage.isTransferSuccessful();
        if (succeeded) {
            System.out.println("DEFECT CANDIDATE: zero-amount transfer was accepted by the app.");
        }
        Assert.assertFalse(succeeded,
                "Expected a zero-amount transfer to be rejected, but it was accepted (see console log)");
    }

    @Test
    public void testNegativeAmountTransfer() {
        loginAsTestUser();
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.goTo(BASE_URL);

        transferPage.enterAmount("-100");
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.submitTransfer();

        boolean succeeded = transferPage.isTransferSuccessful();
        if (succeeded) {
            System.out.println("DEFECT CANDIDATE: negative-amount transfer was accepted by the app.");
        }
        Assert.assertFalse(succeeded,
                "Expected a negative-amount transfer to be rejected, but it was accepted (see console log)");
    }

    @Test
    public void testNonNumericAmountTransfer() {
        loginAsTestUser();
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.goTo(BASE_URL);

        transferPage.enterAmount("abc");
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.submitTransfer();

        Assert.assertFalse(transferPage.isTransferSuccessful(),
                "Expected a non-numeric amount to be rejected");
    }
}
