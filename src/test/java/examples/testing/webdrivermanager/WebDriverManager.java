package examples.testing.webdrivermanager;

import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static examples.testing.Constants.hubUrl;

public class WebDriverManager {

    private static WebDriver driver;

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
        driver = remoteWebDriver;
        return driver;
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

    /**
     * Writes a Cookies.txt file in target dir.
     *
     * @param driver
     */
    public static void setCookiesInJmeterFormat(WebDriver driver) {
        // create file named Cookies to store Login Information
        File file = new File("target/cookies.txt");
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            String cookieString;

            // loop for getting the cookie information
            for (Cookie ck : driver.manage().getCookies()) {
                if (ck.getExpiry() == null) {
                    cookieString = ck.getDomain() + "\t true \t" + ck.getPath() + "\t" + ck.isSecure() + "\t" +
                            2000000000000L + "\t" + ck.getName() + "\t" + ck.getValue();
                } else {
                    cookieString = ck.getDomain() + "\t true \t" + ck.getPath() + "\t" + ck.isSecure() + "\t" +
                            ck.getExpiry().getTime() + "\t" + ck.getName() + "\t" + ck.getValue();
                }

                cookieString = cookieString.replaceAll("null", "").trim();
                Bwrite.write(cookieString);
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
