package pages.clientPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By emailInput = By.id("userEmail");
    private final By passwordInput = By.id("userPassword");
    private final By loginBtn = By.cssSelector("[value='Login']");
    private final By homeBtn = By.xpath("//button[contains(text(),'HOME')]");

    public void login(String email, String password){
        typeText(emailInput, email);
        typeText(passwordInput, password);
        safeClick(findElement(loginBtn));
        Assert.assertTrue(isElementDisplayed(homeBtn), "Login failed.");
    }

    public boolean isElementDisplayed(By locator){

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(10));

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            ).isDisplayed();

        } catch (Exception e){

            return false;
        }
    }
}
