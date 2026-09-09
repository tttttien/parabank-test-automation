package tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OpenNewAccountPage;

public class AccountOpeningTest extends BaseTest {
    @Test
    public void testOpenCheckingAccount() {
        loginAsTestUser();
        OpenNewAccountPage openAccountPage = new OpenNewAccountPage(driver);
        openAccountPage.goTo(BASE_URL);

        openAccountPage.selectAccountType("CHECKING");
        openAccountPage.selectFromAccountByIndex(0);
        openAccountPage.submitOpenAccount();

        Assert.assertTrue(openAccountPage.isAccountCreated(),
                "Expected the new account confirmation panel to be displayed");
        String newAccountId = openAccountPage.getNewAccountId();
        Assert.assertFalse(newAccountId.isEmpty(), "Expected a new Account ID to be returned");
    }

    @Test
    public void testOpenSavingsAccount() {
        loginAsTestUser();
        OpenNewAccountPage openAccountPage = new OpenNewAccountPage(driver);
        openAccountPage.goTo(BASE_URL);

        openAccountPage.selectAccountType("SAVINGS");
        openAccountPage.selectFromAccountByIndex(0);
        openAccountPage.submitOpenAccount();

        Assert.assertTrue(openAccountPage.isAccountCreated(),
                "Expected the new account confirmation panel to be displayed");
    }

    @Test
    public void testNewAccountIdIsReturned() {
        loginAsTestUser();
        OpenNewAccountPage openAccountPage = new OpenNewAccountPage(driver);
        openAccountPage.goTo(BASE_URL);

        openAccountPage.selectAccountType("CHECKING");
        openAccountPage.selectFromAccountByIndex(0);
        openAccountPage.submitOpenAccount();

        String newAccountId = openAccountPage.getNewAccountId();
        Assert.assertTrue(newAccountId.matches("\\d+"),
                "Expected the new Account ID to be numeric, but was: " + newAccountId);
    }
}
