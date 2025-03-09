package Tests;

import Base.BaseTest;
import PageObjects.CartPage;
import PageObjects.ProductPage;
import PageObjects.RegisterPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class CartPageTest extends BaseTest {
    ProductPage PG;
    RegisterPage RP;
    CartPage CP;

    @BeforeMethod
    public void SetUp() throws IOException {
        AndroidDriver driver = getDriver();
        PG = new ProductPage(driver);
        RP = new RegisterPage(driver);
        CP = new CartPage(driver);

    }

    @Test(priority = 1)
    public void ComparePrices() throws InterruptedException {
        RP.InsertName();
        PG.SelectProducts();
        PG.goToCart();
        CP.getCartPrices();
        Assert.assertEquals(CP.stringList,PG.ProductsPrice);
    }

    @Test(priority = 2)
    public void TotalPrice() throws InterruptedException {
        CP.getCartPrices();
        CP.totalPrice();
        Assert.assertEquals(CP.totalPriceFromList,CP.totalPriceLocated);

    }






}
