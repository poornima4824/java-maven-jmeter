package examples.polteq.webdrivermanager;

import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static examples.polteq.Constants.hubUrl;

public class WebDriverManager {

    /**
     * Gets the remote WebDriver from the Selenium Hub.
     *
     * @return
     */
    public static WebDriver getRemoteChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");

        RemoteWebDriver remoteWebDriver = null;
        System.out.println("Connecting to Hub URL = " + hubUrl);
        try {
            remoteWebDriver = new RemoteWebDriver(new URL(hubUrl), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return remoteWebDriver;
    }

    /**
     * Gets all the cookies from the current WebDriver session
     * and translates them to Cookies that can be used in RestAssured.
     *
     * @param driver
     * @return
     */
    public static Cookies getCookies(WebDriver driver) {
        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        List restAssuredCookies = new ArrayList();

        for (Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }
        return new Cookies(restAssuredCookies);
    }

}
