package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageSteps {
    private final Page page;
    private final HomePage homePage;

    public HomePageSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
    }

    public HomePageSteps digitalBanking() {
        homePage.digitalBankButton.click();
        return this;
    }

    public HomePageSteps assertPersonalBanking() {
        assertThat(homePage.personalButton).isVisible();
        return this;
    }

    public HomePageSteps personalBanking() {
        homePage.personalButton.click();
        return this;
    }

    public HomePageSteps assertIFrame() {
        assertThat(homePage.iframe)
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10_000));
        return this;
    }

    public HomePageSteps assertIFrameLoaded() {
        assertThat(page.locator(Constants.iFrame))
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(15_000));

        assertThat(page.frameLocator(Constants.iFrame).locator("body"))
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(15_000));

        return this;
    }

    public HomePageSteps assertQRVisibility() {
        assertThat(page.frameLocator(Constants.iFrame).locator(".qr-message-body"))
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10_000));
        return this;
    }

    public HomePageSteps assertQRText() {
        assertThat(page.frameLocator(Constants.iFrame).locator(".qr-message-body"))
                .containsText(Constants.QRCodeText,
                        new LocatorAssertions.ContainsTextOptions().setTimeout(20_000));
        return this;
    }

    public HomePageSteps navigateToLoansPage() {
        homePage.loanButton.scrollIntoViewIfNeeded();
        homePage.loanButton.click();
        return this;
    }

    public HomePageSteps navigateToDepositPage() {
        homePage.depositButton.scrollIntoViewIfNeeded();
        homePage.depositButton.click();
        return this;
    }

    public HomePageSteps navigateToLocationsPage() {
        assertThat(homePage.addresses).isVisible();
        homePage.addresses.click();
        return this;
    }

}

//comment for commit