package tests;

import com.microsoft.playwright.*;
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


    @Parameters({"browser", "headless"})
    @BeforeClass
    public void beforeClass(@Optional("chromium") String browserName, @Optional("true") String headless)
    { playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));
        context = browser.newContext();
        page = context.newPage();
        homePageSteps = new HomePageSteps(page); }

    @AfterClass public void afterClass() {
        page.close(); browser.close();
        playwright.close(); }

    @Test(description = "KAN-T19")
    public void validateQrMessageForPersonalBanking() {
        page.navigate(Constants.URL);
        homePageSteps.digitalBanking();
        homePageSteps
                .assertPersonalBanking()
                .personalBanking();
        Page personalBankingPage = page.context().waitForPage(() -> {
            homePageSteps.personalBanking(); });
        HomePageSteps personalSteps = new HomePageSteps(personalBankingPage);
        personalSteps
                .assertIFrame()
                .assertIFrameLoaded()
                .assertQRVisibility()
                .assertQRText(); } }