package pages.childWindowPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Set;

public class LoginPageChildWindow {
    protected WebDriver driver;

    public LoginPageChildWindow(WebDriver driver){
        this.driver = driver;
    }

    private By username = By.id("username");
    private By password = By.id("password");
    private By blinkingText = By.className("blinkingText");
    private By redText = By.className("red");

    public void enterUsername(String user){
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(user);
    }

    public void enterPassword(String pass){
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
    }

    public void clickBlinkingText(){
        driver.findElement(blinkingText).click();
    }

    public String getMainWindow(){
        return driver.getWindowHandle();
    }

    public void switchToChidlWindow(String mainWindow){
        Set<String> windows = driver.getWindowHandles();

        for(String window : windows){
            if(!window.equals(mainWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void switchToMainWindow(String mainWindow){
        driver.switchTo().window(mainWindow);
    }

    public String getEnteredUsername(){
        return driver.findElement(username).getAttribute("value");
    }

    public String getEmailFromText(){
        String text = driver.findElement(redText).getText();
        String[] words = text.split("@");
        return words[1].split(" ")[0];
    }


}
