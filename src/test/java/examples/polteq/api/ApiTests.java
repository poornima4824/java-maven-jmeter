package examples.polteq.api;

import examples.polteq.webdrivermanager.WebDriverManager;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static examples.polteq.Constants.applicationUrl;
import static io.restassured.RestAssured.given;

public class ApiTests {

    private static Cookies restCookies;

    /**
     * Using the @BeforeAll annotation, we make sure that we only have to start a webdriver session once,
     * save the cookies and then use these cookies in all following tests.
     */
    @BeforeAll
    static void setupApiTests() {
        WebDriver driver = WebDriverManager.getRemoteChromeDriver();
        restCookies = WebDriverManager.getCookies(driver);
    }


    /**
     * Example test using RestAssured and using the restCookies as gotten from a Selenium WebDriver session
     */
    @Test
    void exampleRestApiTest() {
        given().cookies(restCookies)
                .when().get(applicationUrl)
                .then().assertThat()
                .statusCode(200);

    }

}
