package tests;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import ge.tbc.testautomation.steps.HomePageSteps;
import ge.tbc.testautomation.steps.LocationPageSteps;
import org.testng.annotations.*;

public class ValidateATM {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    HomePageSteps homePageSteps;
    LocationPageSteps locationPageSteps;

    @Parameters({"browser", "headless"})
    @BeforeClass
    public void beforeClass(@Optional("chromium") String browserName,
                            @Optional("true") String headless) {

        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = browser.newContext();
        page = context.newPage();
        homePageSteps = new HomePageSteps(page);
        locationPageSteps = new LocationPageSteps(page);
    }

    @AfterClass
    public void afterClass() {
        page.close();
        browser.close();
        playwright.close();
    }

    @Test(description = "KAN-T22")
    public void validateATM() {
        page.navigate(Constants.URL);
        homePageSteps.navigateToLocationsPage();
        locationPageSteps.searchForATMs();
        locationPageSteps.validateATMsList();
    }
}

//comment for commit
