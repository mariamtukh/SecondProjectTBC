package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LocationPage {
    public final Locator branches;
    public final Locator dropdown;
    public final Locator chooseCity;
    public final Locator chooseLocation;
    public final Locator listOfLocations;
    public final Locator thebuttonATMs;
    public final Locator listOfATMs;

    public LocationPage(Page page) {
        this.branches = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ფილიალები"));
        this.dropdown = page.locator("div.tbcx-dropdown-selector button.tbcx-field");
        this.chooseCity = page.locator("div.cdk-overlay-pane tbcx-dropdown-popover-item",
                new Page.LocatorOptions().setHasText("თბილისი"));
        this.chooseLocation = page.locator("input.search-input");
        this.listOfLocations = page.locator("div.tbcx-pw-atm-branches-section__list-item-description")
                .filter(new Locator.FilterOptions().setHasText("აბაშ"));
        this.thebuttonATMs= page.getByText("ბანკომატები");
        this.listOfATMs = page.locator("div.tbcx-pw-atm-branches-section__list-item-description");
    }
}
