package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.driver.PlaywrightFactory;
import org.testng.annotations.*;

import java.nio.file.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.github.romankh3.image.comparison.ImageComparison;

public class VisualHomePageTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("chromium") String browserName,
                      @Optional("true") String headless) {

        playwright = Playwright.create();
        browser = PlaywrightFactory.launch(playwright, browserName, Boolean.parseBoolean(headless));

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setLocale("ka-GE")
        );

        page = context.newPage();
        page.setDefaultTimeout(60_000);
        page.setDefaultNavigationTimeout(60_000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test(description = "Visual Regression - Offers Page")
    public void offersVisualTest() throws IOException {
        page.navigate(
                Constants.Offers_URL,
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE)
        );

        Locator acceptButton = page.locator("button:has-text('Accept')");
        if (acceptButton.isVisible()) {
            acceptButton.click();
        }

        byte[] screenshotBytes = page.screenshot();
        Path currentPath = Paths.get("screenshots/offers-main.png");
        Files.createDirectories(currentPath.getParent());
        Files.write(currentPath, screenshotBytes);

        BufferedImage baselineImage = ImageIO.read(Paths.get("screenshots/offers-main.png").toFile());
        BufferedImage currentImage = ImageIO.read(currentPath.toFile());

        ImageComparison comparison = new ImageComparison(baselineImage, currentImage);
        com.github.romankh3.image.comparison.model.ImageComparisonResult result = comparison.compareImages();

        if (result.getDifferencePercent() > 0.0) {
            throw new AssertionError(result.getDifferencePercent() + "%");
        }
    }
}
