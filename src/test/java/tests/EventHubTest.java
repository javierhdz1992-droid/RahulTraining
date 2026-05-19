package tests;

import base.EventHubBaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.eventHubPages.AdminPage;
import pages.eventHubPages.BookingPage;
import pages.eventHubPages.EventHubLoginPage;
import pages.eventHubPages.EventsPage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Epic("EventHub Tests")
@Feature("Event Creation and Booking")
public class EventHubTest extends EventHubBaseTest {

    final String email = "TestingEmail@gmail.com";
    final String password = "Admin2026*";

    @Test
    @Story("Create an event, book it and validate the booking reference and seats availability")
    @Severity(SeverityLevel.NORMAL)
    public void eventValidation() {
        EventHubLoginPage loginPage = new EventHubLoginPage(driver);
        EventsPage eventsPage = loginPage.login(email, password);
        AdminPage adminPage = new AdminPage(driver);

        adminPage.openManageEvents();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String eventTitle = "Test Event " + LocalDateTime.now().format(format);

        adminPage.createEvent(eventTitle);

        eventsPage.goToEventsPage();

        var myEvent = eventsPage.findEventByTitle(eventTitle);
        int seatsBeforeBooking = eventsPage.getAvailableSeats(myEvent);
        System.out.println("Seats available before booking in the event: \"" + eventTitle + "\": " + seatsBeforeBooking);

        BookingPage bookingPage = eventsPage.bookevent(myEvent);
        bookingPage.completeBooking("Test User", "estuser@gmail.com", "1234567890");

        String bookingRef = bookingPage.getBookingReference();
        Assert.assertTrue(bookingRef.charAt(0) == eventTitle.charAt(0));
        System.out.println("Booking Reference: " +  bookingRef);

        eventsPage.goToEventsPage();

        myEvent = eventsPage.findEventByTitle(eventTitle);

        int seatsAfterBooking = eventsPage.getAvailableSeats(myEvent);
        Assert.assertEquals(seatsAfterBooking, seatsBeforeBooking - 1);
        System.out.println("Seats available after booking in the event: \"" + eventTitle + "\": " + seatsAfterBooking);
    }
}
