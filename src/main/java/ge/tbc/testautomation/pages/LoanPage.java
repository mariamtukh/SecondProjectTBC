package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoanPage {
    public final Locator loanRequestButton;

    public LoanPage(Page page) {
        this.loanRequestButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("სესხის მოთხოვნა")).first();
    }
}

//comment for commit
