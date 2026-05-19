package tests;

import base.EventHubBaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.eventHubPages.*;

public class EventHubRefundTest extends EventHubBaseTest {

    final String email = "TestingEmail@gmail.com";
    final String password = "Admin2026*";
/**
    @Test
    @Story("Single ticket booking is eligible for refund")
    @Severity(SeverityLevel.NORMAL)
    public void refundSingleTicketValidation()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        login();
        driver.findElement(By.id("nav-events")).click();

        List<WebElement> eventCards = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector("[data-testid='event-card']"))));

        safeClick(eventCards.get(0).findElement(By.cssSelector("[data-testid='book-now-btn']")));

        driver.findElement(By.id("customerName")).sendKeys("Test User");
        driver.findElement(By.id("customer-email")).sendKeys("testemail@gmail.com");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        safeClick(driver.findElement(By.id("confirm-booking")));

        safeClick(driver.findElement(By.xpath("//button[text()='View My Bookings']")));
        wait.until(ExpectedConditions.urlContains("bookings"));
        Assert.assertTrue(driver.getCurrentUrl().contains("bookings"));

        List<WebElement> bookingCards = driver.findElements(By.cssSelector("[data-testid='booking-card']"));
        bookingCards.get(0).findElement(By.xpath(".//button[text()='View Details']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Booking Information']")).isDisplayed());

        String bookingRef = driver.findElement(By.cssSelector(".font-mono.font-bold")).getText();
        String eventTitle = driver.findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals(bookingRef.charAt(0), eventTitle.charAt(0));

        safeClick(driver.findElement(By.cssSelector("[data-testid='check-refund-btn']")));
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-testid='refund-spinner']")).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-testid='refund-spinner']"))));

        WebElement refundResult = driver.findElement(By.cssSelector("[data-testid='refund-result']"));

        System.out.println(refundResult.getText());
        Assert.assertTrue(refundResult.isDisplayed());
        Assert.assertTrue(refundResult.getText().contains("Eligible for refund."));
        Assert.assertTrue(refundResult.getText().contains("Single-ticket bookings qualify for a full refund."));

        System.out.println("----------Test refundSingleTicketValidation Completed successfully----------");
    }

    @Test
    @Story("Group ticket booking is NOT eligible for refund")
    @Severity(SeverityLevel.NORMAL)
    public void refundMultipleTicketValidation(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        login();
        driver.findElement(By.id("nav-events")).click();

        List<WebElement> eventCards = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector("[data-testid='event-card']"))));

        safeClick(eventCards.get(0).findElement(By.cssSelector("[data-testid='book-now-btn']")));

        //Increase ticket count to 2
        driver.findElement(By.xpath("//button[text()='+']")).click();
        //Increase ticket count to 3
        driver.findElement(By.xpath("//button[text()='+']")).click();
        driver.findElement(By.id("customerName")).sendKeys("Test User");
        driver.findElement(By.id("customer-email")).sendKeys("testemail@gmail.com");
        driver.findElement(By.id("phone")).sendKeys("1234567890");
        safeClick(driver.findElement(By.id("confirm-booking")));

        safeClick(driver.findElement(By.xpath("//button[text()='View My Bookings']")));
        wait.until(ExpectedConditions.urlContains("bookings"));
        Assert.assertTrue(driver.getCurrentUrl().contains("bookings"));

        List<WebElement> bookingCards = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector("[data-testid='booking-card']"))));
        bookingCards.get(0).findElement(By.xpath(".//button[text()='View Details']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Booking Information']")).isDisplayed());

        String bookingRef = driver.findElement(By.cssSelector(".font-mono.font-bold")).getText();
        String eventTitle = driver.findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals(bookingRef.charAt(0), eventTitle.charAt(0));

        safeClick(driver.findElement(By.cssSelector("[data-testid='check-refund-btn']")));
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-testid='refund-spinner']")).isDisplayed());
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[data-testid='refund-spinner']"))));

        WebElement refundResult = driver.findElement(By.cssSelector("[data-testid='refund-result']"));

        System.out.println(refundResult.getText());
        Assert.assertTrue(refundResult.isDisplayed());
        Assert.assertTrue(refundResult.getText().contains("Not eligible for refund."));
        //Assert.assertTrue(refundResult.getText().contains(" Group bookings (3  tickets) are non-refundable."));

        System.out.println("----------Test refundMultipleTicketValidation Completed successfully----------");
    }
**/
    @Test
    @Story("Single ticket booking is eligible for refund")
    @Severity(SeverityLevel.NORMAL)
    public void refundSingleTicketValidation() {
        EventHubLoginPage loginPage = new EventHubLoginPage(driver);
        EventsPage eventsPage = loginPage.login(email, password);

        eventsPage.goToEventsPage();

        var eventCard = eventsPage.findEventByIndex(0);

        BookingPage bookingPage = eventsPage.bookevent(eventCard);
        bookingPage.completeBooking("Test User", "estuser@gmail.com", "1234567890");

        BookingsPage bookingsPage = bookingPage.goToBookings();

        BookingDetailsPage detailsPage =  bookingsPage.openFirstBooking();

        Assert.assertTrue(detailsPage.isBookingInformationDisplayed());

        String bookingRef = detailsPage.getBookingReference();
        String eventTitle = detailsPage.getEventTitle();

        Assert.assertEquals(bookingRef.charAt(0), eventTitle.charAt(0));

        detailsPage.checkRefundEligibility();

        Assert.assertTrue(detailsPage.isRefundResultDisplayed());
        System.out.println(detailsPage.getRefundResultText());
        Assert.assertTrue(detailsPage.getRefundResultText().contains("Eligible for refund."));
        Assert.assertTrue(detailsPage.getRefundResultText().contains("Single-ticket bookings qualify for a full refund."));

        System.out.println("----------Test refundSingleTicketValidation Completed successfully----------");
    }

    @Test
    @Story("Group ticket booking is NOT eligible for refund")
    @Severity(SeverityLevel.NORMAL)
    public void refundMultipleTicketValidation() {
        EventHubLoginPage loginPage = new EventHubLoginPage(driver);
        EventsPage eventsPage = loginPage.login(email, password);

        eventsPage.goToEventsPage();

        var eventCard = eventsPage.findEventByIndex(0);

        BookingPage bookingPage = eventsPage.bookevent(eventCard);

        bookingPage.increaseTickets(2);
        bookingPage.completeBooking("Test User", "estuser@gmail.com", "1234567890");

        BookingsPage bookingsPage = bookingPage.goToBookings();

        BookingDetailsPage detailsPage =  bookingsPage.openFirstBooking();

        Assert.assertTrue(detailsPage.isBookingInformationDisplayed());

        String bookingRef = detailsPage.getBookingReference();
        String eventTitle = detailsPage.getEventTitle();

        Assert.assertEquals(bookingRef.charAt(0), eventTitle.charAt(0));

        detailsPage.checkRefundEligibility();

        Assert.assertTrue(detailsPage.isRefundResultDisplayed());
        System.out.println(detailsPage.getRefundResultText());
        Assert.assertTrue(detailsPage.getRefundResultText().contains("Not eligible for refund."));
    }
}
