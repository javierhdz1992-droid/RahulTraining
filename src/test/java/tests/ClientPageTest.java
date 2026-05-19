package tests;

import base.ClientBaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.clientPages.*;

public class ClientPageTest extends ClientBaseTest {

    /**
    @DataProvider(name = "testData")
    public Object[][] getTestdata(){
        return new Object[][]{
                {"TestingEmail@gmail.com", "Admin2026*", "ZARA COAT 3"}
                //{"AnotherEmail@gmail.com", "ZARA COAT 3"},
                //{"ThirdEmail@gmail.com", "ZARA COAT 3"}
        };
    }
**/

    @Test(dataProvider = "testData", dataProviderClass = dataProviders.TestDataProvider.class)
    @Story("Validate Client Page")
    @Severity(SeverityLevel.NORMAL)
    public void validClientPage(String email, String password, String productName) {

        LoginPage loginPage = new LoginPage(driver);
        ProductCatalogPage productPage =  new ProductCatalogPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        OrdersPage ordersPage = new OrdersPage(driver);

        // Login
        loginPage.login(email, password);

        // Product Selection
        productPage.addProductToCart(productName);
        productPage.goToCart();

        cartPage.checkout();

        // Checkout
        checkoutPage.selectCountry("ind", "India");

        Assert.assertEquals(checkoutPage.getEmailConfirmation(), email, "Email confirmation does not match the logged in email.");

        checkoutPage.submitOrder();

        // Confirmation
        Assert.assertEquals(confirmationPage.getThankYouMessage(), "THANKYOU FOR THE ORDER.", "Thank you message is not displayed as expected.");

        String orderId = confirmationPage.getOrderId();

        // Orders
        confirmationPage.goToOrdersPage();

        ordersPage.openOrder(orderId);
        Assert.assertTrue(orderId.contains(ordersPage.getOrderDetailsId()), "Order ID in order details does not match the order ID from confirmation page.");
    }


}