package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.DepositPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DepositPageSteps {
    private final Page page;
    private final DepositPage depositPage;

    public DepositPageSteps(Page page) {
        this.page = page;
        this.depositPage = new DepositPage(page);
    }

    public DepositPageSteps navigateToDepositMyGoal() {
        depositPage.myGoalButton.scrollIntoViewIfNeeded();
        depositPage.myGoalButton.click();
        return this;
    }

    public DepositPageSteps openTheDepositMyGoal() {
        assertThat(depositPage.openingTheDeposit).isVisible();
        depositPage.openingTheDeposit.click();
        return this;
    }

    public DepositPageSteps validateIFrame() {
        assertThat(depositPage.iframe)
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10_000));
        return this;
    }

    public DepositPageSteps assertQRVisibility() {
        assertThat(page.frameLocator(Constants.iFrame).locator(".qr-message-body"))
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10_000));
        return this;
    }

    public DepositPageSteps assertQRText() {
        assertThat(page.frameLocator(Constants.iFrame).locator(".qr-message-body"))
                .containsText(Constants.QRCodeText,
                        new LocatorAssertions.ContainsTextOptions().setTimeout(20_000));
        return this;
    }

}
