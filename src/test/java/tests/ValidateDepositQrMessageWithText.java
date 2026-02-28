package tests;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import ge.tbc.testautomation.steps.DepositPageSteps;
import ge.tbc.testautomation.steps.HomePageSteps;
import org.testng.annotations.*;

public class ValidateDepositQrMessageWithText {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    DepositPageSteps depositPageSteps;
    HomePageSteps homePageSteps;

    @Parameters({"browser", "headless"})
    @BeforeClass
    public void beforeClass(@Optional("chromium") String browserName,
                            @Optional("true") String headless) {

        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = browser.newContext();
        page = context.newPage();
        depositPageSteps = new DepositPageSteps(page);
        homePageSteps = new HomePageSteps(page);
    }

    @AfterClass
    public void afterClass() {
        page.close();
        browser.close();
        playwright.close();
    }

    @Test(description = "KAN-T21")
    public void validateQrMessageForLoanAndLoanCalculation() {
        page.navigate(Constants.URL);
        homePageSteps.navigateToDepositPage();
        depositPageSteps.navigateToDepositMyGoal();
        Page depositTab = page.context().waitForPage(() -> {
            depositPageSteps.openTheDepositMyGoal();
        });
        depositTab.waitForLoadState();
        DepositPageSteps depositStepsOnNewTab = new DepositPageSteps(depositTab);
        depositStepsOnNewTab
                .validateIFrame()
                .assertQRVisibility()
                .assertQRText();

    }
}


