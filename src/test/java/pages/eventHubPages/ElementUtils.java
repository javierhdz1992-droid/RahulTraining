package pages.eventHubPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void safeClick(WebElement element) {

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                element
        );

        element = wait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void setDateTimeReact(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "const input = arguments[0];" +
                        "const value = arguments[1];" +

                        // Setter nativo
                        "const proto = window.HTMLInputElement.prototype;" +
                        "const desc = Object.getOwnPropertyDescriptor(proto, 'value');" +
                        "const setter = desc.set;" +
                        "setter.call(input, value);" +

                        // Eventos necesarios para React
                        "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                        "input.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "input.dispatchEvent(new Event('blur', { bubbles: true }));",
                element, value
        );
    }

    public WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void typeText(By locator, String text){
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return findElement(locator).getText();
    }
}
