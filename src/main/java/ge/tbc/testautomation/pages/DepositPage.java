package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.data.Constants;

public class DepositPage {
    public final Locator myGoalButton;
    public final Locator openingTheDeposit;
    public final Locator iframe;
    public final Locator QRCode;

    public DepositPage(Page page) {
        this.myGoalButton = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("ანაბარი „ჩემი მიზანი“"));
        this.openingTheDeposit = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("ანაბრის გახსნა")).first();
        this.iframe = page.locator(Constants.iFrame); // e.g. "iframe#..."
        this.QRCode = page.locator(".qr-message-body");
    }
}

