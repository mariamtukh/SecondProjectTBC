package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.LocationPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LocationPageSteps {
    private final Page page;
    private final LocationPage locationPage;

    public LocationPageSteps(Page page) {
        this.page = page;
        this.locationPage = new LocationPage(page);
    }

    public LocationPageSteps showBranches() {
        assertThat(locationPage.branches).isVisible();
        locationPage.branches.click();
        return this;
    }

    public LocationPageSteps validateVisibilityOfDropDown() {
        Locator dropdown = locationPage.dropdown.first();
        dropdown.scrollIntoViewIfNeeded();
        dropdown.click(new Locator.ClickOptions().setForce(true));
        return this;
    }

    public LocationPageSteps chooseCity() {
        assertThat(locationPage.chooseCity).isVisible();
        locationPage.chooseCity.scrollIntoViewIfNeeded();
        locationPage.chooseCity.click();
        return this;
    }

    public LocationPageSteps chooseLocation() {
        locationPage.chooseLocation.fill("აბაშ");
        locationPage.chooseLocation.press("Enter");
        return this;
    }

    public LocationPageSteps validateNumberOfATMs() {
        Locator items = page.locator("div.tbcx-pw-atm-branches-section__list-item");
        items.first().waitFor(new Locator.WaitForOptions().setTimeout(20000));
        PlaywrightAssertions.assertThat(items).hasCount(1);
        return this;
    }

    public LocationPageSteps searchForATMs() {
        locationPage.thebuttonATMs.click();
        return this;
    }

    public LocationPageSteps validateATMsList() {
        locationPage.listOfATMs.allTextContents().forEach(text -> {
            if (!text.contains("ATM - 24/7")) {
                throw new AssertionError("All ATMs must be 24/7");
            }
        });
        return this;
    }

}

