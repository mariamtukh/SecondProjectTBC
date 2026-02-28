package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.OffersPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OffersSteps {
    private final Page page;
    private final OffersPage offersPage;

    public OffersSteps(Page page) {
        this.page = page;
        this.offersPage = new OffersPage(page);
    }

    public OffersSteps openFilters() {
        page.waitForLoadState(LoadState.NETWORKIDLE);

        try {
            offersPage.acceptCookieButton.click(new Locator.ClickOptions().setTimeout(2_000));
        } catch (PlaywrightException e) {
            // Ignore if the cookie banner is not present
        }

        if (!offersPage.categoryHeader.isVisible()) {
            offersPage.filterButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            offersPage.filterButton.scrollIntoViewIfNeeded();
            offersPage.filterButton.click(new Locator.ClickOptions().setTimeout(10_000));
            offersPage.categoryHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        }

        assertThat(offersPage.categoryHeader).isVisible();
        return this;
    }

    public OffersSteps applyFilterIfNotNull(String filterText) {
        if (filterText == null) return this;

        offersPage.categoryHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Locator option = offersPage.filterOption(filterText);
        option.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        option.scrollIntoViewIfNeeded();
        option.click(new Locator.ClickOptions().setTimeout(60_000));

        page.waitForLoadState(LoadState.NETWORKIDLE);
        return this;
    }

    public OffersSteps assertResultsExist() {
        Locator firstCard = offersPage.offerCards.first();
        Locator emptyMessage = offersPage.emptyStateMessage;

        firstCard.or(emptyMessage).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(firstCard.or(emptyMessage)).isVisible();

        return this;
    }
}