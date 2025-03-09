package Base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    private static Process appiumProcess = null;
    public static AppiumDriverLocalService service;

    @BeforeMethod // Ensure initialization before each test
    public void setUp() throws MalformedURLException, IOException, InterruptedException {
        // Check and start Appium server if not running

        startAppiumServerIfNotRunning();

        if (driver.get() == null) {
            driver.set(initializeDriver());

        }
    }

    public AndroidDriver initializeDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:udid", "emulator-5554");
        capabilities.setCapability("appium:appPackage", "com.androidsample.generalstore");
        capabilities.setCapability("appium:appActivity", "com.androidsample.generalstore.SplashActivity");
        capabilities.setCapability("appium:autoGrantPermissions", "true");
        URL url = new URL("http://127.0.0.1:4723/");

        return new AndroidDriver(url, capabilities);
    }

    public AndroidDriver getDriver() {
        return driver.get();
    }




    @AfterClass
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Clean up the ThreadLocal instance
        }
        // Stop Appium server if it was started by this class

    }

    // Function to capture screenshot on failure
    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                AndroidDriver actualDriver = driver.get();
                if (actualDriver != null) {
                    Thread.sleep(2000); // Ensure the UI is fully loaded

                    // Capture screenshot as Base64 and convert to file
                    String base64Screenshot = actualDriver.getScreenshotAs(OutputType.BASE64);
                    byte[] decodedScreenshot = Base64.getDecoder().decode(base64Screenshot);
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File screenshotFile = new File("screenshots/" + result.getName() + "_" + timestamp + ".png");

                    FileUtils.writeByteArrayToFile(screenshotFile, decodedScreenshot);
                    System.out.println("Screenshot saved at: " + screenshotFile.getAbsolutePath());

                } else {
                    System.out.println("Driver is null, cannot take screenshot.");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Error while taking screenshot: " + e.getMessage());
            }
        }
    }

    private static boolean isAppiumServerRunning(String host, int port) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/status");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000); // 3 seconds timeout
            connection.connect();
            return connection.getResponseCode() == 200; // Server is running if status is 200
        } catch (IOException e) {
            return false; // Server is not running
        }
    }


    // Function to check and start Appium server if not running
    private void startAppiumServerIfNotRunning() throws IOException, InterruptedException {
        if (isAppiumServerRunning("127.0.0.1", 4723)) {
            System.out.println("✅ Appium server is already running at http://127.0.0.1:4723");
            return; // Skip starting the server
        }
        service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\anabeh\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .usingDriverExecutable(new File("C:/Program Files/nodejs/node.exe"))
                .withIPAddress("127.0.0.1").usingPort(4723).withTimeout(Duration.ofSeconds(30)).build();
        service.start();

    }



    // Function to stop Appium server
    @AfterSuite
    private void stopAppiumServer() {
        if (appiumProcess != null && appiumProcess.isAlive()) {
            System.out.println("⚠️ Stopping Appium server...");
            appiumProcess.destroy();
            try {
                appiumProcess.waitFor(5, TimeUnit.SECONDS);
                if (appiumProcess.isAlive()) {
                    appiumProcess.destroyForcibly(); // Force stop if still running
                }
                System.out.println("✅ Appium server stopped.");
            } catch (InterruptedException e) {
                System.out.println("❌ Error stopping Appium server: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No Appium server process found to stop.");
        }
    }

}