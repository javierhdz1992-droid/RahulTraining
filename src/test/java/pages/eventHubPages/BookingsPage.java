package pages.eventHubPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookingsPage {

    private WebDriver driver;

    public BookingsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By bookingCards = By.cssSelector("[data-testid='booking-card']");

    public List<WebElement> getEventCards(){
        return driver.findElements(bookingCards);
    }

    public BookingDetailsPage openFirstBooking(){
        getEventCards().get(0).findElement(By.xpath(".//button[text()='View Details']")).click();

        return new BookingDetailsPage(driver);
    }
}
