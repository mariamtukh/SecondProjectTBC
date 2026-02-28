package ge.tbc.testautomation.driver;

import com.microsoft.playwright.*;

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
}
