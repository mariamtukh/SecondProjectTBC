package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.pages.LoanPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoanPageSteps {
    private final Page page;
    private final LoanPage loanPage;

    public LoanPageSteps(Page page) {
        this.page = page;
        this.loanPage = new LoanPage(page);
    }

    public LoanPageSteps assertLoanButtonIsDisplayed() {
        assertThat(loanPage.loanRequestButton)
                .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10_000));
        return this;
    }

    public LoanPageSteps requestLoan() {
        loanPage.loanRequestButton.click();
        return this;
    }

}

