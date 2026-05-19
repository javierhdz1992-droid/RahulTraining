package pages.clientPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends BasePage{

    public ConfirmationPage(WebDriver driver){
        super(driver);
    }

    private final By thankYouMsg = By.cssSelector(".hero-primary");
    private final By orderId = By.cssSelector(".em-spacer-1 .ng-star-inserted");
    private final By ordersBtn = By.cssSelector("[routerlink='/dashboard/myorders']");

    public String getThankYouMessage(){
        return getText(thankYouMsg);
    }

    public String getOrderId(){
        return getText(orderId);
    }

    public void goToOrdersPage(){
        safeClick(findElement(ordersBtn));
    }
}
