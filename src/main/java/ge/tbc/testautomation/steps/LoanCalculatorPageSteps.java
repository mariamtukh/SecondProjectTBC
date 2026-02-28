package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.LoanCalculatorPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoanCalculatorPageSteps {
    private final Page page;
    private final LoanCalculatorPage loanCalculatorPage;

    public LoanCalculatorPageSteps(Page page) {
        this.page = page;
        this.loanCalculatorPage = new LoanCalculatorPage(page);
    }

    public LoanCalculatorPageSteps deleteDefaultValueOfPeriod() {
        loanCalculatorPage.loanPeriodbutton.clear();
        return this;
    }

    public LoanCalculatorPageSteps definePeriodOfLoan() {
        loanCalculatorPage.loanPeriodbutton.fill("12");
        return this;
    }

    public LoanCalculatorPageSteps validateMonthlyAmountOfLoan() {
        assertThat(loanCalculatorPage.monthlyAmountOfLoan)
                .hasText("264");
        return this;
    }

    public LoanCalculatorPageSteps validateIdentificationFormOfLoan() {
        assertThat(loanCalculatorPage.identificationFormOfLoan)
                .hasText("გაიარე იდენტიფიკაცია");
        return this;
    }
}

