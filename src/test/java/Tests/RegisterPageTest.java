package Tests;

import Base.BaseTest;
import PageObjects.RegisterPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterPageTest extends BaseTest {
    private RegisterPage RP;

    @BeforeMethod
    public void SetUp(){
        AndroidDriver driver = getDriver();
        RP = new RegisterPage(driver);
    }


    @Test(priority = 2)
    public void SelectCountryRP() throws Exception {
        RP.SelectCountry();
        RP.InsertName();

    }

    @Test(priority = 1)
    public void AssertEmptyNameEM(){
        RP.getToastMessage();

        Assert.assertEquals(RP.ErrorMessage, "Please enter your name");
    }




}
