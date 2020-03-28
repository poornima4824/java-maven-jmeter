package examples.testing.api;

import examples.testing.GeneralTest;
import examples.testing.abstraction.LoginPage;
import examples.testing.webdrivermanager.WebDriverManager;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static examples.testing.Constants.applicationUrl;
import static io.restassured.RestAssured.given;

public class ApiTests extends GeneralTest {

    private static Cookies restCookies;

    /**
     * Using the @BeforeAll annotation, we make sure that we only have to start a webdriver session once,
     * save the cookies and then use these cookies in all following tests.
     */
    @BeforeAll
    static void setupApiTests() {
        WebDriver driver = WebDriverManager.getRemoteChromeDriver();
        driver.get(applicationUrl);
        new LoginPage(driver).login(testEnvUsr, testEnvPwd);
        restCookies = WebDriverManager.getCookies(driver);

        driver.close();
        driver.quit();
    }


    /**
     * Example test using RestAssured and using the restCookies as gotten from a Selenium WebDriver session
     */
    @Test
    void exampleRestApiTest() {
        given().cookies(restCookies)
                .when().get(applicationUrl) //TODO: Add API endpoint to URL for which authentication is necessary
                .then().assertThat()
                .statusCode(200);

    }

}
