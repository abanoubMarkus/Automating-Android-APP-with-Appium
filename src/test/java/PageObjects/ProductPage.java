package PageObjects;

import Data.ExcelDataReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductPage {

    public AndroidDriver driver;

    public ProductPage(AndroidDriver driver) throws IOException {
        this.driver = driver;
    }

    public String pro1 = ExcelDataReader.GetDataFromExcel("DataExcels/products.xlsx",0,0,0);
    public String pro2 = ExcelDataReader.GetDataFromExcel("DataExcels/products.xlsx",0,0,1);
    public String pro3 = ExcelDataReader.GetDataFromExcel("DataExcels/products.xlsx",0,0,2);

    public List<String > ProductsFromExcel = new ArrayList<>(List.of(pro1,pro2,pro3));
    //---------------------


    public List<String > ProductsPrice = new ArrayList<>(List.of());
    public int counter;

    public void SelectProducts(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.support.v7.widget.RecyclerView[@resource-id=\"com.androidsample.generalstore:id/rvProductList\"]/android.widget.RelativeLayout[1]")));
        List<WebElement> products = driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView[@resource-id=\"com.androidsample.generalstore:id/rvProductList\"]/android.widget.RelativeLayout"));



        for(String i :ProductsFromExcel){
        try {

            boolean isElementFound = false;
            int maxScrollAttempts = 2; // Maximum number of scroll attempts
            int scrollCount = 0;

            while (!isElementFound && scrollCount < maxScrollAttempts) {
                try {
                    // Use UiAutomator to scroll and find element
                    String scrollCommand = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"" + i + "\"))";

                    WebElement targetElement = driver.findElement(new AppiumBy.ByAndroidUIAutomator(scrollCommand));

                    // Verify the element's text matches exactly
                    if (targetElement.getText().equalsIgnoreCase(i)) {

                        //----------------------------------------------------
                        String AddToCartXpath = "//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/productName\" and @text=\""+i+"\"]//ancestor::android.widget.LinearLayout[1]//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/productAddCart\"]";
                        WebElement AddToCartBTN = driver.findElement(By.xpath(AddToCartXpath));
                        AddToCartBTN.click();
                        WebElement proPrice = driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/productName\" and @text=\""+i+"\"]//ancestor::android.widget.LinearLayout[1]//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/productPrice\"]"));

                        ProductsPrice.add(counter, proPrice.getText());
                        counter++;


                        //-----------------------------------------------------
                        isElementFound = true;

                    }else {
                        // If no exact match, scroll again
                        System.out.println("no match found");
                        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollBackward()"
                        ));
                        scrollCount++;
                        // Wait for scroll to settle
                        Thread.sleep(1000);
                    }
                } catch (NoSuchElementException e) {
                    // Scroll forward if element not found
                    driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward()"));
                    scrollCount++;


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }



        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }
        //------------
    }


    public void goToCart(){
        WebElement cartButton = driver.findElement(By.xpath("//android.widget.ImageButton[@resource-id=\"com.androidsample.generalstore:id/appbar_btn_cart\"]"));
        cartButton.click();
    }


}
