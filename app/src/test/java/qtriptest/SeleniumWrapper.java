package qtriptest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {
    public static RemoteWebDriver driver;
    private static WebDriverWait wait;
    static {
        try {
            driver = DriverSingleton.getDriver("chrome"); // Initialize driver
            wait = new WebDriverWait(driver, 10); // Initialize wait
        } catch (MalformedURLException e) {
            System.err.println("Failed to initialize the WebDriver: " + e.getMessage());
        }
    }

    private static void waitForElementToBeClickable(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean clickIfCan(WebElement elementToClick) {
        try {
           waitForElementToBeClickable(elementToClick); // Wait 
           elementToClick.click();
            return true; // Indicate success
        } catch (ElementNotInteractableException e) {
            System.err.println("The element is not interactable: " + e.getMessage());
        } catch (WebDriverException e) {
            System.err.println("WebDriver encountered an issue: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
        return false; // Indicate failure
    }

    public static boolean sendKeysIfCan(WebElement inputBox, String keysToSend) {
        try {
            waitForElementToBeClickable(inputBox); // Wait 
            inputBox.click();// click
            inputBox.clear(); // Clear the existing text
            inputBox.sendKeys(keysToSend); // Send the provided keys
            return true; // Success
        } catch (ElementNotInteractableException e) {
            System.err.println("The input box is not interactable: " + e.getMessage());
        } catch (WebDriverException e) {
            System.err.println("WebDriver encountered an issue: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
        return false; // Failure
    }

    public static boolean navigate(String url) {
        try {
            driver.get(url);
            wait.until(ExpectedConditions.urlContains(url)); // Wait until the URL contains the expected part
            return true; // Successfully navigated
        } catch (WebDriverException e) {
            System.err.println("WebDriver encountered an issue while navigating to: " + url);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while navigating to: " + url);
            e.printStackTrace();
        }
        return false; // Return false if navigation fails
    }


    public static WebElement findElementWithRetry(By by, int retryCount) {
        WebElement element = null;
        for (int i = 1; i <= retryCount; i++) {
            try {
                element = driver.findElement(by);
                if (element != null) {
                    break; // Exit loop if the element is found
                }
            } catch (NoSuchElementException e) {
                System.out.println("Attempt " + i + " failed. Retrying...");
            }
        }
        return element;
    }

    public static String capture(RemoteWebDriver driver) throws IOException{
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir")+"/Reports/"+System.currentTimeMillis()+".png");
        String absPath = dest.getAbsolutePath();
        FileUtils.copyFile(srcFile,dest);
        System.out.println(absPath);
        return absPath;
    }


}
