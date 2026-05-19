package pages.clientPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage{

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By checkoutBtn = By.xpath("//button[text()='Checkout']");

    public String getProductName(String productName){
        By product = By.xpath("//h3[text()='" +  productName + "']");
        return getText(product);
    }

    public void checkout(){
        safeClick(findElement(checkoutBtn));
    }

}
