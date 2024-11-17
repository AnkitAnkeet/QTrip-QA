package qtriptest.pages;
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
        this.wait = new WebDriverWait(driver,10);
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(driver,10);
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

    



    private void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    
    public void inputGuestName(String GuestName){
        waitForElementToBeClickable(nameTextBox);
        nameTextBox.click();
        nameTextBox.clear();
        nameTextBox.sendKeys(GuestName);
    }

    public void inputDate(String Date){
        waitForElementToBeClickable(dateTextBox);
        dateTextBox.click();
        dateTextBox.clear();
        dateTextBox.sendKeys(Date);
    }

    public void inputPersonCount(String count){
        waitForElementToBeClickable(personCountTextBox);
        personCountTextBox.click();
        personCountTextBox.clear();
        personCountTextBox.sendKeys(count);
    }

    public void submitForm(){
        adventureBookingForm.submit();
    }

    public void verifySuccessfulReservation(){
        wait.until(ExpectedConditions.visibilityOf(successAlertBannerElement));
        if(successAlertBannerElement.getText().contains("Greetings!")){
            System.out.println("Reservation is successful!");
        }else{
            System.out.println("Reservation is not successful!");
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