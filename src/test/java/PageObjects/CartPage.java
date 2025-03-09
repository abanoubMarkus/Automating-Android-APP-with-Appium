package PageObjects;

import Data.ExcelDataReader;
import com.aventstack.extentreports.gherkin.model.And;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {



    public AndroidDriver driver;
    public String totalPriceFromList;
    public String totalPriceLocated;

    public CartPage(AndroidDriver driver) throws IOException {
        this.driver = driver;
    }

    public List<String > stringList = new ArrayList<>(List.of());
    public int counter = 0;
    public void getCartPrices() throws InterruptedException {

        Thread.sleep(Duration.ofSeconds(3));
        List<WebElement> cartPrices = driver.findElements(By.xpath("//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/productPrice\"]"));
        for(WebElement k : cartPrices){

            stringList.add(counter,k.getText());
            counter++;
        }
        System.out.println(stringList);
    }

    public void totalPrice(){

        totalPriceLocated = driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.androidsample.generalstore:id/totalAmountLbl\"]")).getText();


        double sum = 0.0;
        for (String price : stringList) {


            // Remove the '$' and convert to double
            String cleanedPrice = price.replace("$", "");


            double value = Double.parseDouble(cleanedPrice);


            sum += value;


        }
        totalPriceFromList = "$ " + String.format("%.2f", sum);
        System.out.println("Total price: " + totalPriceFromList);

        System.out.println("this the total located " + totalPriceLocated);
    }




}
