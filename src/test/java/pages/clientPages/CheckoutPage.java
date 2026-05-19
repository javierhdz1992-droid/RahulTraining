package pages.clientPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class CheckoutPage extends BasePage{

    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    public final By countryInput = By.cssSelector("[placeholder*='Country']");
    public final By countryDropdown = By.cssSelector(".ta-results");
    public final By emailConfirmation = By.cssSelector(".user__name label");
    public final By submitBtn = By.cssSelector(".action__submit");

    public void selectCountry(String countrySearch, String countryName){
        WebElement country = findElement(countryInput);

        Actions actions = new Actions(driver);
        actions.click(country).sendKeys(countrySearch).perform();

        WebElement dropdown = findElement(countryDropdown);

        List<WebElement> options = dropdown.findElements(By.tagName("button"));

        for(WebElement option : options){
            if(option.getText().equalsIgnoreCase(countryName)){
                option.click();
                break;
            }
        }
    }

    public String getEmailConfirmation(){
        return getText(emailConfirmation);
    }

    public void submitOrder(){
        safeClick(findElement(submitBtn));
    }
}
