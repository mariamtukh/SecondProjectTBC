package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoanCalculatorPage {
    public final Locator loanPeriodbutton;
    public final Locator monthlyAmountOfLoan;
    public final Locator identificationFormOfLoan;
    public final Locator acceptCookis;

    public LoanCalculatorPage(Page page) {
        this.loanPeriodbutton = page.locator("#standard-calculator-period");
        this.monthlyAmountOfLoan = page.locator("#standard-calculator-result-payment");
        this.identificationFormOfLoan = page.locator("#auth-personal-info .headline.h6");
        this.acceptCookis = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Accept").setExact(false));
    }
}

