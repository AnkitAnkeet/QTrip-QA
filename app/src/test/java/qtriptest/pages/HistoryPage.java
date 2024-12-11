
package qtriptest.pages;


import qtriptest.SeleniumWrapper;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;
    
    public HistoryPage(RemoteWebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver,10);
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajx,this);
    }

    @FindBy(xpath ="//table/tbody/tr")
    private List<WebElement> reservations;


    @FindBy(id ="no-reservation-banner")
    private WebElement noReservationBannerElement;


    public List<WebElement> getReservations() {
        driver.navigate().refresh();
        if (reservations == null || reservations.isEmpty()) {
            System.err.println("No reservations found or the list is uninitialized.");
            return Collections.emptyList(); // Return an empty list to avoid null issues
        }
        return reservations;
    }
    



    public void getTransactionID(List<WebElement> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations provided.");
            return;
        }
    
        for (WebElement reservation : reservations) {
            try {
                // Fetch transaction ID element
                WebElement transactionIdElement = reservation.findElement(By.xpath("./th"));
                String transactionId = transactionIdElement.getText();
                System.out.println("Transaction ID is: " + transactionId); // Print immediately
            } catch (NoSuchElementException e) {
                System.err.println("Transaction ID element not found in a reservation: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred while processing a reservation: " + e.getMessage());
            }
        }
    }
    



    public void cancelReservations(List<WebElement> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations provided to cancel.");
            return;
        }
    
        for (WebElement reservation : reservations) {
            try {
                // Locate the cancel button
                WebElement cancelElement = reservation.findElement(By.xpath("./td//button[text()='Cancel']"));
    
                // Attempt to click using wrapper method
                boolean clicked = SeleniumWrapper.clickIfCan(cancelElement);
                if (clicked) {
                    System.out.println("Reservation canceled successfully!");
                } else {
                    System.out.println("Failed to cancel reservation.");
                }
            } catch (NoSuchElementException e) {
                System.err.println("Cancel button not found for a reservation: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred while canceling a reservation: " + e.getMessage());
            }
        }
    }
    

    public boolean noReservationVerification() {
        try {
            wait.until(ExpectedConditions.visibilityOf(noReservationBannerElement));
            
            String bannerText = noReservationBannerElement.getText();
            if (bannerText.contains("Oops!")) {
                System.out.println("No reservations are there!");
                return true; // Indicates no reservations
            } else {
                System.out.println("You have reservations.");
                return false; // Indicates reservations exist
            }
        } catch (NoSuchElementException e) {
            System.err.println("No reservation banner element not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred during reservation verification: " + e.getMessage());
        }
        return false; // Default to reservations present in case of failure
    }
    

    public void verifyReservations(List<WebElement> reservations, String dataset) {
        boolean isVerified = true;
    
        try {
            for (WebElement reservation : reservations) {
                wait.until(ExpectedConditions.visibilityOf(reservation));
    
                WebElement guestNameElement = reservation.findElement(By.xpath("./td[1]"));
                wait.until(ExpectedConditions.visibilityOf(guestNameElement));
                String guestName = guestNameElement.getText().trim();
    
                String[] datas = dataset.split(";");
                if (guestName.equals(datas[2])) {
                    WebElement advNameElement = reservation.findElement(By.xpath("./td[2]"));
                    wait.until(ExpectedConditions.visibilityOf(advNameElement));
                    String advName = advNameElement.getText().trim();
    
                    WebElement countElement = reservation.findElement(By.xpath("./td[3]"));
                    wait.until(ExpectedConditions.visibilityOf(countElement));
                    String count = countElement.getText().trim();
    
                    if (!(advName.equals(datas[1]) && count.equals(datas[4]))) {
                        isVerified = false;
                        break;
                    }
                }
            }
    
            if (isVerified) {
                System.out.println("All the bookings are displayed on the History page.");
            } else {
                System.out.println("All the bookings are not displayed on the History page.");
            }
        } catch (NoSuchElementException e) {
            System.err.println("An element was not found: " + e.getMessage());
            isVerified = false;
        } catch (TimeoutException e) {
            System.err.println("The wait timed out: " + e.getMessage());
            isVerified = false;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            isVerified = false;
        }
    }
    


}