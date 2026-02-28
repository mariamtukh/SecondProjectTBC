package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class OffersPage {
    public final Locator categoryHeader;
    public final Locator offerCards;
    public final Locator emptyStateMessage;
    public final Locator filterButton;
    public final Locator acceptCookieButton;
    private final Page page;

    public OffersPage(Page page) {
        this.page = page;

        this.categoryHeader = page.getByText("კატეგორია", new Page.GetByTextOptions().setExact(true));
        this.offerCards = page.locator("a[href*='/offers/all-offers/']:visible");
        this.emptyStateMessage = page.getByText("ვერ მოიძებნა", new Page.GetByTextOptions().setExact(false));
        this.acceptCookieButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("თანხმობა"));

        Locator byRole = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ფილტრი"))
                .or(page.locator("div.marketing__filter-chip").filter(new Locator.FilterOptions().setHasText("ფილტრი")));
        Locator byChip = page.locator("div.marketing__filter-chip", new Page.LocatorOptions().setHasText("ფილტრი"));
        this.filterButton = byRole.or(byChip);
    }

    public Locator filterOption(String text) {
        return page.getByText(text, new Page.GetByTextOptions().setExact(true)).locator(":visible").first();
    }
}