package ge.tbc.testautomation.driver;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;

public final class PlaywrightFactory {
    private PlaywrightFactory() {}

    public static Browser launch(Playwright playwright, String browserName, boolean headless) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        return switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit"  -> playwright.webkit().launch(options);
            default        -> playwright.chromium().launch(options);
        };
    }

    public static BrowserContext newContext(Browser browser, String locale, boolean mobile) {
        Browser.NewContextOptions ctxOptions = new Browser.NewContextOptions()
                .setLocale(locale == null ? "ka-GE" : locale);

        if (mobile) {
            ctxOptions
                    .setViewportSize(390, 844) // iPhone 12/13-ish
                    .setDeviceScaleFactor(3)
                    .setIsMobile(true)
                    .setHasTouch(true)
                    .setUserAgent(Constants.User_Agent);
        }

        return browser.newContext(ctxOptions);
    }
}

//comment for commit