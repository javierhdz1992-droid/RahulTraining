package pages.eventHubPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookingDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ElementUtils utils;

    public BookingDetailsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.utils = new ElementUtils(driver);
    }

    private By bookingInformation = By.xpath("//*[text()='Booking Information']");
    private By bookingRef = By.cssSelector(".font-mono.font-bold");
    private By eventTitle = By.cssSelector("h1");
    private By refundBtn = By.cssSelector("[data-testid='check-refund-btn']");
    private By refunndSpinner = By.cssSelector("[data-testid='refund-spinner']");
    private By refundResult = By.cssSelector("[data-testid='refund-result']");

    public boolean isBookingInformationDisplayed(){
        return utils.findElement(bookingInformation).isDisplayed();
    }

    public String getBookingReference(){
        return utils.findElement(bookingRef).getText();
    }

    public String getEventTitle(){
        return utils.findElement(eventTitle).getText();
    }

    public void checkRefundEligibility(){
        utils.safeClick(utils.findElement(refundBtn));

        wait.until(ExpectedConditions.visibilityOfElementLocated(refunndSpinner));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(refunndSpinner));
    }

    public String getRefundResultText(){
        return utils.findElement(refundResult).getText();
    }

    public boolean isRefundResultDisplayed(){
        return utils.findElement(refundResult).isDisplayed();
    }
}
