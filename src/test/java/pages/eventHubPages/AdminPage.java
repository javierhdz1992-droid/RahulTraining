package pages.eventHubPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPage {

    private WebDriver driver;
    private ElementUtils utils;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new ElementUtils(driver);
    }

    private By adminBtn = By.xpath("//button[text()='Admin']");
    private By manageEvents = By.xpath("//*[text()='Manage Events']");
    private By title = By.cssSelector("#event-title-input");
    private By description = By.cssSelector("textarea[placeholder='Describe the event…']");
    private By city = By.id("city");
    private By venue = By.id("venue");
    private By date = By.id("event-date-&-time");
    private By price = By.id("price-($)");
    private By seats = By.id("total-seats");
    private By addEventBtn = By.id("add-event-btn");

    public void openManageEvents(){
        utils.safeClick(utils.findElement(adminBtn));
        utils.safeClick(utils.findElement(manageEvents));
    }

    public void createEvent(String eventTitle){
        utils.typeText(title,eventTitle);
        utils.typeText(description,"Test event description");
        utils.typeText(city,"City Test");
        utils.typeText(venue,"Venue Test");

        WebElement dateInput = utils.findElement(date);

        utils.setDateTimeReact(dateInput, "2027-12-31T00:00");

        utils.typeText(price,"100");
        utils.typeText(seats,"50");
        utils.safeClick(utils.findElement(addEventBtn));
    }

}
