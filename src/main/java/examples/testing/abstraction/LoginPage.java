package examples.testing.abstraction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final WebDriver driver;

    /**
     * CONSTRUCTOR
     *
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    /**
     * @param userName
     * @param password
     * TODO: Implement assignment specific login method
     */
    public void login(String userName, String password) {
//        driver.findElement(By.cssSelector("input[type=username]")).sendKeys(userName);
//        driver.findElement(By.cssSelector("input[type=password]")).sendKeys(password);
//        driver.findElement(By.cssSelector("input[type=submit")).click();
    }

}
