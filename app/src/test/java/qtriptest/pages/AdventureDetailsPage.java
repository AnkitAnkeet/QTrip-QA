package qtriptest.pages;
import qtriptest.SeleniumWrapper;
import java.util.NoSuchElementException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventureDetailsPage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;
    
    public AdventureDetailsPage(RemoteWebDriver driver){
         this.driver = driver;
        this.wait = new WebDriverWait(this.driver,10);
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajx,this);
    }

    @FindBy(name ="name")
    private WebElement nameTextBox;

    @FindBy(name ="date")
    private WebElement dateTextBox;

    @FindBy(name ="person")
    private WebElement personCountTextBox;

    @FindBy(id= "myForm")
    private WebElement adventureBookingForm;

    @FindBy(id= "reserved-banner")
    private WebElement successAlertBannerElement;

    
    public void inputGuestName(String GuestName){
       SeleniumWrapper.sendKeysIfCan(nameTextBox,GuestName);
    }

    public void inputDate(String Date){
        SeleniumWrapper.sendKeysIfCan(dateTextBox,Date);
    }

    public void inputPersonCount(String count){
        SeleniumWrapper.sendKeysIfCan(personCountTextBox,count);
    }

    public void submitForm() {
        try {
            adventureBookingForm.submit(); // Attempt to submit the form
            System.out.println("Booking Form submitted successfully.");
        } catch (ElementNotInteractableException e) {
            System.err.println("Booking Form cannot be interacted with: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.err.println("Booking Form element not found: " + e.getMessage());
        } catch (WebDriverException e) {
            System.err.println("WebDriver encountered an issue: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    

    public void verifySuccessfulReservation() {
        try {
            // Wait for the success banner to be visible
            wait.until(ExpectedConditions.visibilityOf(successAlertBannerElement));
    
            // Check the content of the success banner
            if (successAlertBannerElement.getText().contains("Greetings!")) {
                System.out.println("Reservation is successful!");
            } else {
                System.out.println("Reservation is not successful!");
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while verifying reservation: " + e.getMessage());
        }
    }
    

    public String getCityFromDataSet(String dataset){
        String[] dataArr = dataset.split(";");
        return dataArr[0];
    }

    public String getAdventureFromDataSet(String dataset){
        String[] dataArr = dataset.split(";");
        return dataArr[1];
    }
    public String getGuestNameFromDataSet(String dataset){
        String[] dataArr = dataset.split(";");
        return dataArr[2];
    }

    public String getDateFromDataSet(String dataset){
        String[] dataArr = dataset.split(";");
        return dataArr[3];
    }

    public String getPersonCountFromDataSet(String dataset){
        String[] dataArr = dataset.split(";");
        return dataArr[4];
    }


    

}