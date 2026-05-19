package pages.clientPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrdersPage extends BasePage {

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    private final By orderIds = By.xpath("//tbody //tr //th");
    private final By orderIdDetails = By.cssSelector(".col-text");

    public void openOrder(String orderId){
        List<WebElement> orders = driver.findElements(orderIds);

        for(int i=0; i < orders.size(); i++){
            String currentId = orders.get(i).getText();
            if(orderId.contains(currentId)){
                By viewBtn = By.xpath("//tbody //tr[" + (i + 1) + "] //button");
                safeClick(findElement(viewBtn));
                break;
            }
        }
    }

    public String getOrderDetailsId(){
        return getText(orderIdDetails);
    }
}
