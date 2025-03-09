package Tests;

import Base.BaseTest;
import PageObjects.ProductPage;
import PageObjects.RegisterPage;

import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProductPageTest extends BaseTest {


    ProductPage PG;
    RegisterPage RP;

    @BeforeMethod
    public void SetUp() throws IOException {
        AndroidDriver driver = getDriver();
        PG = new ProductPage(driver);
        RP = new RegisterPage(driver);

    }



    @Test
    public void AddProducts() throws Exception {

        RP.InsertName();
        PG.SelectProducts();


    }









}
