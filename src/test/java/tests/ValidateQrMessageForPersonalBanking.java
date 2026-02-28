package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import ge.tbc.testautomation.steps.HomePageSteps;
import org.testng.annotations.*;

public class ValidateQrMessageForPersonalBanking {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    HomePageSteps homePageSteps;

    @Parameters({"browser", "headless", "mobile"})
    @BeforeClass
    public void beforeClass(
            @Optional("chromium") String browserName,
            @Optional("true") String headless,
            @Optional("false") String mobile
    ) {
        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = PlaywrightFactory.newContext(browser, "ka-GE", Boolean.parseBoolean(mobile));

        page = context.newPage();
        homePageSteps = new HomePageSteps(page);
    }

    @AfterClass
    public void afterClass() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test(description = "KAN-T19")
    public void validateQrMessageForPersonalBanking() {
        page.navigate(Constants.URL);

        homePageSteps.digitalBanking();

        homePageSteps.assertPersonalBanking();

        Page personalBankingPage = page.context().waitForPage(() -> {
            homePageSteps.personalBanking();
        });

        personalBankingPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
        personalBankingPage.waitForSelector("iframe#authIframe",
                new Page.WaitForSelectorOptions().setTimeout(30000));

        HomePageSteps personalSteps = new HomePageSteps(personalBankingPage);

        personalSteps
                .assertIFrame()
                .assertIFrameLoaded()
                .assertQRVisibility()
                .assertQRText();
    }
}