package tests;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import ge.tbc.testautomation.steps.HomePageSteps;
import ge.tbc.testautomation.steps.LocationPageSteps;
import org.testng.annotations.*;

public class AddressOfBranches {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    LocationPageSteps locationPageSteps;
    HomePageSteps homePageSteps;

    @Parameters({"browser", "headless"})
    @BeforeClass
    public void beforeClass(@Optional("chromium") String browserName,
                            @Optional("true") String headless) {

        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = browser.newContext(new Browser.NewContextOptions()
                .setGeolocation(41.7151, 44.8271)
                .setPermissions(java.util.Arrays.asList("geolocation"))
        );

        page = context.newPage();
        locationPageSteps = new LocationPageSteps(page);
        homePageSteps = new HomePageSteps(page);
    }


    @AfterClass
    public void afterClass() {
        page.close();
        browser.close();
        playwright.close();
    }

    @Test(description = "KAN-T21")
    public void addressOfBranches() {
        page.navigate(Constants.URL);
        homePageSteps.navigateToLocationsPage();
        locationPageSteps.showBranches();
        locationPageSteps.validateVisibilityOfDropDown();
        locationPageSteps.chooseCity();
        locationPageSteps.chooseLocation();
        locationPageSteps.validateNumberOfATMs();
    }
}
