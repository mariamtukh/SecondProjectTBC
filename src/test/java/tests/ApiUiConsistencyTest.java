package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.models.NormalizedPage;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import com.microsoft.playwright.options.LoadState;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiUiConsistencyTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    private static final String UI_URL = Constants.Consumer_Loan_URL;

    private static final String PAGE_ID = Constants.PAGE_ID;
    private static final String API_URL = Constants.API_URL + PAGE_ID;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setLocale("ka-GE"));
        page = context.newPage();
    }

    private io.restassured.specification.RequestSpecification apiRequest() {
        return given()
                .relaxedHTTPSValidation()
                .header("Accept", "application/json, text/plain, */*")
                .header("User-Agent", Constants.Browsers)
                .header("Referer", UI_URL)
                .header("Origin", Constants.MAIN_URL)
                .queryParam("locale", "ka-GE")
                .log().all();
    }

    private static String norm(String s) {
        if (s == null) return null;
        return s.replace("\u00A0", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private NormalizedPage extractFromApi(Response res) {
        NormalizedPage out = new NormalizedPage();
        JsonPath jp = res.jsonPath();

        List<String> titles = jp.getList("sectionComponents.inputs.title");
        if (titles != null) {
            for (String t : titles) {
                t = norm(t);
                if (t != null && !t.isEmpty()) {
                    out.title = t;
                    break;
                }
            }
        }

        Object rawLabels = jp.get("sectionComponents.inputs.list.label");
        String found = null;

        if (rawLabels instanceof List<?>) {
            List<?> level1 = (List<?>) rawLabels;
            for (Object item : level1) {
                if (item == null) continue;

                if (item instanceof String) {
                    String l = norm((String) item);
                    if (l != null && !l.isEmpty()) { found = l; break; }
                } else if (item instanceof List<?>) {
                    for (Object inner : (List<?>) item) {
                        if (inner instanceof String) {
                            String l = norm((String) inner);
                            if (l != null && !l.isEmpty()) { found = l; break; }
                        }
                    }
                    if (found != null) break;
                }
            }
        }

        out.listItemLabel = found;
        return out;
    }

    @Test(priority = 1)
    public void apiHappyPath_status200_andFieldValidation() {
        Response res = apiRequest()
                .when()
                .get(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .response();

        String body = res.asString();
        System.out.println("==== RAW API BODY (first 800 chars) ====");
        System.out.println(body.substring(0, Math.min(body.length(), 800)));

        NormalizedPage api = extractFromApi(res);

        Assert.assertNotNull(api.title, Constants.API_Title);
        Assert.assertNotNull(api.listItemLabel, Constants.Item_Label);
    }

    @Test(priority = 2)
    public void apiNegative_invalidPageId_returns404() {
        apiRequest()
                .when()
                .get(Constants.Apigw_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(404);
    }

    @Test(priority = 3)
    public void apiToUiConsistency_titleAndOneListLabelExistsOnUi() {
        Response res = apiRequest()
                .when()
                .get(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .response();

        NormalizedPage api = extractFromApi(res);
        Assert.assertNotNull(api.title, Constants.Title_Null);
        Assert.assertNotNull(api.listItemLabel, Constants.Label_Null);

        page.navigate(UI_URL);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        Locator exactTitleH1 = page.getByRole(
                AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(1).setName(api.title).setExact(true)
        );
        exactTitleH1.waitFor();

        String uiTitle = norm(exactTitleH1.textContent());
        Assert.assertEquals(uiTitle, api.title, Constants.Title_Not_Match);

        Locator body = page.locator("body");
        body.waitFor();

        String pageText = norm(body.textContent());
        Assert.assertTrue(pageText.contains(api.listItemLabel),
                Constants.Not_Contain_Label + api.listItemLabel);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}

//comment for commit