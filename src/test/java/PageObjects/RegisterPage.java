package PageObjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.applicationcache.model.ApplicationCacheStatusUpdated;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    public AndroidDriver driver;
    public RegisterPage(AndroidDriver driver){
        this.driver = driver;
    }

    public String ErrorMessage;

    public void SelectCountry() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@text=\"Select the country where you want to shop\"]")));


        try {
            // Click to expand DDL
            WebElement dropdown = driver.findElement(By.xpath("//android.widget.Spinner[@resource-id=\"com.androidsample.generalstore:id/spinnerCountry\"]")); // Replace with your DDL's locator
            dropdown.click();

            boolean isElementFound = false;
            int maxScrollAttempts = 10; // Maximum number of scroll attempts
            int scrollCount = 0;

            while (!isElementFound && scrollCount < maxScrollAttempts) {
                try {
                    // Use UiAutomator to scroll and find element
                    String scrollCommand = "new UiScrollable(new UiSelector().scrollable(true).instance(0))"
                            + ".scrollIntoView(new UiSelector().text(\"Egypt\"))";

                    WebElement targetElement = driver.findElement(new AppiumBy.ByAndroidUIAutomator(scrollCommand));

                    // Verify the element's text matches exactly
                    if (targetElement.getText().equals("Egypt")) {
                        targetElement.click();
                        isElementFound = true;
                    } else {
                        // If no exact match, scroll again
                        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward()"
                        ));
                        scrollCount++;
                         // Wait for scroll to settle
                    }
                } catch (NoSuchElementException e) {
                    // Scroll forward if element not found
                    driver.findElement(new AppiumBy.ByAndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollForward()"
                    ));
                    scrollCount++;
                    try {
                        Thread.sleep(1000); // Wait for scroll to settle
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            if (!isElementFound) {
                try {
                    throw new Exception("Value '" + "Egypt" + "' not found in dropdown after " + maxScrollAttempts + " scrolls");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }




    public void getToastMessage(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@text=\"Select the country where you want to shop\"]")));


        WebElement LetsShopBtn = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.androidsample.generalstore:id/btnLetsShop\"]"));
        LetsShopBtn.click();
        WebElement ToastMessage = driver.findElement(By.xpath("//android.widget.Toast[@text='Please enter your name']"));
        ErrorMessage = ToastMessage.getText();
        System.out.println(ErrorMessage);


    }

    public void InsertName(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//android.widget.TextView[@text=\"Select the country where you want to shop\"]")));
        WebElement NameField = driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.androidsample.generalstore:id/nameField\"]"));
        NameField.sendKeys("Abanoub");
        WebElement LetsShopBtn = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.androidsample.generalstore:id/btnLetsShop\"]"));
        LetsShopBtn.click();

    }









}
