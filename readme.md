# Parabank Automation Testing Project

Selenium + Java + TestNG automation framework for
[https://parabank.parasoft.com](https://parabank.parasoft.com), built using the
Page Object Model (POM). 

## 1. Project structure

```
parabank/
├── pom.xml
├── testng.xml
├── src/
│   ├── main/java/pages/     ← Page Objects (one class per page)
│   │   ├── BasePage.java
│   │   ├── LoginPage.java
│   │   ├── OpenNewAccountPage.java
│   │   ├── TransferFundsPage.java
│   │   ├── BillPayPage.java
│   └── test/java/com/parabank/tests/     ← Test classes (TestNG)
│       ├── BaseTest.java                 ← driver setup/teardown + login helper
│       ├── AccountOpeningTest.java        (TC-AO-*)
│       ├── FundTransferTest.java          (TC-FT-*)
│       ├── BillPaymentTest.java           (TC-BP-*)
```
## 2. Tech Stack

- Language: Java
- Automation Framework: Selenium WebDriver
- Test Framework: TestNG
- Build Tool: Maven
- Design Pattern: Page Object Model (POM)
- Browser: Google Chrome
- API Testing: Postman
- Version Control: Git / GitHub

## 3. Test Scope

The project focuses on automating core banking functionalities of ParaBank, including:

- User login
- New account opening
- Fund transfer
- Bill payment
- Form validation
- Error/negative scenarios

## Test Cases

| TC ID | Module | Test Case | Precondition | Test Steps | Test Data | Expected Result |
| ----- | ------ | --------- | ------------ | ---------- | --------- | --------------- |
| TC-AO-01 | Account Opening | Open a new Checking account | User is logged in and on the Open New Account page | 1. Select "Checking" as account type<br>2. Select an existing account as the initial deposit account<br>3. Click "Open New Account" | Account Type: Checking | A new Checking account is successfully created and the new account number is displayed |
| TC-AO-02 | Account Opening | Open a new Savings account | User is logged in and on the Open New Account page | 1. Select "Savings" as account type<br>2. Select an existing account as the initial deposit account<br>3. Click "Open New Account" | Account Type: Savings | A new Savings account is successfully created and the new account number is displayed |
| TC-AO-03 | Account Opening | Verify generated account ID | User has successfully opened a new account | 1. Open a new account<br>2. Capture the generated account ID<br>3. Verify the account ID format | Generated Account ID | The generated account ID is displayed and contains numeric values |
| TC-FT-01 | Fund Transfer | Transfer a valid amount between accounts | User is logged in and has at least two accounts | 1. Navigate to Transfer Funds<br>2. Enter a valid transfer amount<br>3. Select the source account<br>4. Select the destination account<br>5. Click "Transfer" | Amount: 100<br>From Account: Existing account<br>To Account: Existing account | The transfer is successfully completed and a confirmation message is displayed |
| TC-FT-02 | Fund Transfer | Transfer zero amount | User is logged in and has at least two accounts | 1. Navigate to Transfer Funds<br>2. Enter amount 0<br>3. Select the source account<br>4. Select the destination account<br>5. Click "Transfer" | Amount: 0 | The system rejects the invalid amount or displays an appropriate validation/error message |
| TC-FT-03 | Fund Transfer | Transfer negative amount | User is logged in and has at least two accounts | 1. Navigate to Transfer Funds<br>2. Enter a negative amount<br>3. Select the source account<br>4. Select the destination account<br>5. Click "Transfer" | Amount: -100 | The system rejects the invalid amount or displays an appropriate validation/error message |
| TC-FT-04 | Fund Transfer | Transfer non-numeric amount | User is logged in and has at least two accounts | 1. Navigate to Transfer Funds<br>2. Enter a non-numeric value<br>3. Select the source account<br>4. Select the destination account<br>5. Click "Transfer" | Amount: abc | The system rejects the invalid input or displays an appropriate validation/error message |
| TC-BP-01 | Bill Payment | Pay a bill with valid information | User is logged in and has an available account | 1. Navigate to Bill Pay<br>2. Enter valid payee information<br>3. Enter a valid account number and verify account number<br>4. Enter the payment amount<br>5. Select the source account<br>6. Click "Send Payment" | Valid payee information<br>Valid account number<br>Amount: 100 | The bill payment is successfully processed and a confirmation message is displayed |
| TC-BP-02 | Bill Payment | Submit bill payment with missing Payee Name | User is logged in and on the Bill Pay page | 1. Leave Payee Name empty<br>2. Fill in the remaining required fields with valid data<br>3. Click "Send Payment" | Payee Name: Empty | The system rejects the submission and displays an appropriate validation/error message |
| TC-BP-03 | Bill Payment | Submit bill payment with mismatched account numbers | User is logged in and on the Bill Pay page | 1. Enter a valid account number<br>2. Enter a different value in Verify Account<br>3. Fill in the remaining required fields<br>4. Click "Send Payment" | Account: 123456<br>Verify Account: 654321 | The system rejects the payment and displays a validation/error message indicating that the account numbers do not match |
| TC-BP-04 | Bill Payment | Reject bill payment when payment amount exceeds account balance | User is logged in and has an account with insufficient balance for the payment | 1. Navigate to Bill Pay<br>2. Enter valid payee information<br>3. Enter valid account number and verification number<br>4. Enter a payment amount exceeding the available account balance<br>5. Select the source account<br>6. Submit the payment | Payee: Electric Co<br>Account: 123456<br>Verify Account: 123456<br>Amount: 999999 |The bill payment should be rejected because the payment amount exceeds the available account balance|

## 5. Test Execution

### Prerequisites

- Java JDK
- Maven
- Google Chrome
- Git

### Run all tests

```bash
mvn clean test
```
### Test Execution Summary

| Module | Test Cases | Passed | Failed | Errors | Skipped |
| ------ | ---------- | ------ | ------ | ------ | ------- |
| Account Opening | 3 | 3 | 0 | 0 | 0 |
| Fund Transfer | 4 | 2 | 2 | 0 | 0 |
| Bill Payment | 4 | 3 | 1 | 0 | 0 |
| **Total** | **11** | **8** | **3** | **0** | **0** |

### Defects

| ID | Module | Scenario | Expected Result | Actual Result | Status |
| -- | ------ | -------- | --------------- | ------------- | ------ |
| BUG-01 | Fund Transfer | Transfer zero amount | The system should reject a transfer with amount = 0 | The system accepted the zero-amount transfer, causing the test assertion to fail | Failed |
| BUG-02 | Fund Transfer | Transfer negative amount | The system should reject a transfer with a negative amount | The system accepted the negative-amount transfer, causing the test assertion to fail | Failed |
| BUG-03 | Bill Payment | Payment amount exceeds account balance | The system should reject the payment because the amount exceeds the available account balance | The system accepted the payment even though the payment amount exceeded the available account balance, causing the test assertion to fail | Failed |