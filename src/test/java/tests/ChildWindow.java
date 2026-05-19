package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.childWindowPages.LoginPageChildWindow;

public class ChildWindow extends BaseTest {

    @Test
    public void verifyChildWindow(){

        LoginPageChildWindow loginPage = new LoginPageChildWindow(driver);

        String mainWindow = loginPage.getMainWindow();
        loginPage.clickBlinkingText();

        loginPage.switchToChidlWindow(mainWindow);

        String email = loginPage.getEmailFromText();

        loginPage.switchToMainWindow(mainWindow);
        loginPage.enterUsername(email);

        System.out.println(driver.getTitle());
        System.out.println(loginPage.getEnteredUsername());
    }
}
