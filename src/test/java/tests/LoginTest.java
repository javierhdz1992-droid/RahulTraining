package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.loginPages.LoginPage;

import java.time.Duration;
import java.util.List;

public class LoginTest extends BaseTest {

    @Test
    public void verifyLoginError(){

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(driver.getTitle().contains("LoginPage"));

        loginPage.login("rahulshetty", "learning");

        Assert.assertEquals(loginPage.getErrorMessage(), "Incorrect username/password.");
        System.out.println("----------Test verifyLoginError Completed successfully----------");

    }

    @Test
    public void verifyLoginSuccess(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("rahulshettyacademy", "Learning@830$3mK2");

        Assert.assertEquals(loginPage.getCardsCount(), 4);

        System.out.println("----------Test verifyLoginSuccess Completed successfully----------");
    }

    @Test
    public void UIElements() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("rahulshettyacademy", "Learning@830$3mK2");

        loginPage.selectRole("consult");

        loginPage.selectUserType("user");

        loginPage.acceptAlert();

        loginPage.acceptTerms();

        Assert.assertTrue(loginPage.getBlinkingText().contains("Free Access"));

        //System.out.println("Good");
        System.out.println("----------Test UIElements Completed successfully----------");

    }
}
