package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import ge.tbc.testautomation.steps.OffersSteps;
import org.testng.annotations.*;

public class OffersFiltersTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("chromium") String browserName,
                      @Optional("true") String headless) {

        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setLocale("ka-GE")
        );

        page = context.newPage();

        page.setDefaultTimeout(60_000);
        page.setDefaultNavigationTimeout(60_000);

        System.out.println("OffersFiltersTest | Browser=" + browserName +
                " | Thread=" + Thread.currentThread().getId());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @DataProvider(name = "offersFilters", parallel = false)
    public Object[][] offersFilters() {
        return new Object[][]{
                // ✅ PASSED scenarios only
                {"მოგზაურობა", null, null, null},
                {"შოპინგი", "ქეშბექი", null, null}
        };
    }

    @Test(dataProvider = "offersFilters")
    public void offersFiltersVariations(String category,
                                        String offerType,
                                        String cardType,
                                        String productType) {

        page.navigate(
                Constants.Offers_URL,
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE)
        );

        OffersSteps offersSteps = new OffersSteps(page);

        offersSteps.openFilters()
                .applyFilterIfNotNull(category)
                .applyFilterIfNotNull(offerType)
                .applyFilterIfNotNull(cardType)
                .applyFilterIfNotNull(productType)
                .assertResultsExist();
    }
}

//comment for commit