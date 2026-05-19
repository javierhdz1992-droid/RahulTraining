package pages.eventHubPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventHubLoginPage {

    private WebDriver driver;
    private ElementUtils utils;

    public EventHubLoginPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new ElementUtils(driver);
    }

    private By emailInput = By.cssSelector("input[placeholder='you@email.com']");
    private By passwordInput = By.id("password");
    private By loginBtn = By.cssSelector("button[type='submit']");
    private By browseEvents = By.xpath("//span[text()='Browse Events →']");

    public EventsPage login(String email, String password) {
        utils.typeText(emailInput, email);
        utils.typeText(passwordInput, password);
        utils.safeClick(utils.findElement(loginBtn));

        utils.findElement(browseEvents).isDisplayed();

        return new EventsPage(driver);
    }

}
