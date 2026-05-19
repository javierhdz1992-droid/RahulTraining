package pages.loginPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By username = By.id("username");
    private By password = By.id("password");
    private By signInBtn = By.id("signInBtn");
    private By errorMessage = By.cssSelector("[style*='block']");
    private By dropdown = By.tagName("select");
    private By userTypes = By.id("usertype");
    private By okayBtn = By.id("okayBtn");
    private By terms = By.id("terms")  ;
    private By blinkingText = By.className("blinkingText");
    private By cards = By.cssSelector(".card-body a");

    // Actions

    public void enterUsername(String user){
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(user);
    }

    public void enterPassword(String pass){
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
    }

    public void login(String user, String  pass) {
        enterUsername(user);
        enterPassword(pass);
        driver.findElement(signInBtn).click();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public void selectRole(String role){
        Select select = new  Select(driver.findElement(dropdown));
        select.selectByValue(role);
    }

    public void selectUserType(String type){
        List<WebElement> types = driver.findElements(userTypes);

        for(WebElement element : types){
            if(element.getAttribute("value").equals(type)){
                element.click();
                break;
            }
        }
    }

    public void acceptAlert(){
        wait.until(ExpectedConditions.elementToBeClickable(okayBtn)).click();
    }

    public void acceptTerms(){
        wait.until(ExpectedConditions.elementToBeClickable(terms)).click();
    }

    public String getBlinkingText(){
        return driver.findElement(blinkingText).getText();
    }

    public List<WebElement> getCards(){
        return driver.findElements(cards);
    }

    public int getCardsCount(){
        return getCards().size();
    }
}
