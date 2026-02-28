package tests;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import ge.tbc.testautomation.steps.HomePageSteps;
import ge.tbc.testautomation.steps.LoanCalculatorPageSteps;
import ge.tbc.testautomation.steps.LoanPageSteps;
import org.testng.annotations.*;

public class ValidateQrMessageForLoanAndLoanCalculation {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    HomePageSteps homePageSteps;
    LoanPageSteps loanPageSteps;

    @Parameters({"browser", "headless"})
    @BeforeClass
    public void beforeClass(@Optional("chromium") String browserName,
                            @Optional("true") String headless) {

        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = browser.newContext();
        page = context.newPage();
        homePageSteps = new HomePageSteps(page);
        loanPageSteps = new LoanPageSteps(page);

    }

    @AfterClass
    public void afterClass() {
        page.close();
        browser.close();
        playwright.close();
    }

    @Test(description = "KAN-T20")
    public void validateQrMessageForLoanAndLoanCalculation() {
        page.navigate(Constants.URL);
        homePageSteps.navigateToLoansPage();
        loanPageSteps
                .assertLoanButtonIsDisplayed();
        Page loanCalcPage = page.context().waitForPage(() -> {
            loanPageSteps.requestLoan();
        });

        loanCalcPage.waitForLoadState();

        LoanCalculatorPageSteps loanCalculatorSteps =
                new LoanCalculatorPageSteps(loanCalcPage);

        loanCalculatorSteps
                .deleteDefaultValueOfPeriod()
                .definePeriodOfLoan()
                .validateMonthlyAmountOfLoan();
    }
}


