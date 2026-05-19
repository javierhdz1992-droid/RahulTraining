package pages.eventHubPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookingPage {

    private WebDriver driver;
    private ElementUtils utils;

    public BookingPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new ElementUtils(driver);
    }

    private By customerName = By.id("customerName");
    private By customerEmail = By.id("customer-email");
    private By customerPhone = By.id("phone");
    private By confirmBooking = By.id("confirm-booking");
    private By bookingRef = By.cssSelector(".booking-ref");
    private By viewBookins = By.xpath("//button[text()='View My Bookings']");
    private By increaseTicketBtn = By.xpath("//button[text()='+']");

    public void completeBooking(String name, String email, String phone){
        utils.typeText(customerName, name);
        utils.typeText(customerEmail, email);
        utils.typeText(customerPhone, phone);
        utils.safeClick(utils.findElement(confirmBooking));
    }

    public String getBookingReference(){
        return utils.findElement(bookingRef).getText();
    }

    public BookingsPage goToBookings(){
        utils.safeClick(driver.findElement(viewBookins));

        return new BookingsPage(driver);
    }

    public void increaseTickets(int ticketsNum){
        for(int i = 1; i <= ticketsNum; i++){
            utils.findElement(increaseTicketBtn).click();
        }
    }
}
