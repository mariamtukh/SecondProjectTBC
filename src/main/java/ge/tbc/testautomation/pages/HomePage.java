package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.data.Constants;

public class HomePage {
    public final Locator digitalBankButton;
    public final Locator personalButton;
    public final Locator iframe;
    public final Locator qr;
    public final Locator loanButton;
    public final Locator depositButton;
    public final Locator addresses;

    public HomePage(Page page) {
        this.digitalBankButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ციფრული ბანკი"));
        this.personalButton = page.locator(":text('პერსონალური')");
        this.iframe = page.locator(Constants.iFrame);
        this.qr = page.locator(".qr-message-body");
        this.loanButton = page.locator("span.tbcx-pw-footer-sub-item__title")
                .filter(new Locator.FilterOptions().setHasText("სესხები"));
        this.depositButton = page.locator("span.tbcx-pw-footer-sub-item__title")
                .filter(new Locator.FilterOptions().setHasText("ანაბრები"));
        this.addresses = page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("მისამართები"));
    }
}

