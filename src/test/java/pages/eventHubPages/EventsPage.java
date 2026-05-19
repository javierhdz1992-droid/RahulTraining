package pages.eventHubPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EventsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ElementUtils utils;

    public EventsPage(WebDriver driver){
        this.driver = driver;
        this.utils = new ElementUtils(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By navEvents = By.id("nav-events");
    private By eventCards = By.cssSelector("[data-testid='event-card']");

    public WebElement findEventByTitle(String title){
        List<WebElement> cards = driver.findElements(eventCards);
        wait.until(ExpectedConditions.visibilityOf(cards.get(0)));

        for (WebElement card : cards){
            if(card.getText().contains(title)){
                return card;
            }
        }
        return null;
    }

    public int getAvailableSeats(WebElement eventCard){
        WebElement seatsElement = eventCard.findElement(By.cssSelector("span[class='text-xs font-semibold text-emerald-600']"));

        String seatsText = seatsElement.getText();
        return Integer.parseInt(seatsText.replaceAll("\\D+", ""));
    }

    public BookingPage bookevent(WebElement eventCard){
        utils.safeClick(eventCard.findElement(By.cssSelector("[data-testid='book-now-btn']")));
        return new BookingPage(driver);
    }

    public void goToEventsPage(){
        utils.findElement(navEvents).click();
        wait.until(ExpectedConditions.urlToBe("https://eventhub.rahulshettyacademy.com/events"));
        driver.navigate().refresh();
    }

    public WebElement findEventByIndex(int index){
        List<WebElement> cards = driver.findElements(eventCards);
        return cards.get(index);
    }
}
