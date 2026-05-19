package pages.clientPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductCatalogPage extends BasePage{

    public ProductCatalogPage(WebDriver driver) {
         super(driver);
    }

    private final By products = By.cssSelector(".card-body b");
    private final By addToCartBtn = By.xpath("//button[text()=' Add To Cart']");
    private final By cartBtn = By.cssSelector("button[routerlink='/dashboard/cart']");

    public void addProductToCart(String productName){
        List<WebElement> productList = driver.findElements(products);
        List<WebElement> buttons =  driver.findElements(addToCartBtn);

        for(int i = 0; i < productList.size(); i++){
            String currentProduct = productList.get(i).getText();
            if(currentProduct.equals(productName)){
                safeClick(buttons.get(i));
                break;
            }
        }
    }

    public void goToCart(){
        safeClick(findElement(cartBtn));
    }
}
