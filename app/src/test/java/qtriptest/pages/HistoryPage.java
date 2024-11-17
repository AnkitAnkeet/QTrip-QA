
package qtriptest.pages;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.compress.archivers.dump.DumpArchiveException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;
    
    public HistoryPage(RemoteWebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(driver,10);
        PageFactory.initElements(ajx,this);
    }

    @FindBy(xpath ="//table/tbody/tr")
    private List<WebElement> reservations;


    @FindBy(id ="no-reservation-banner")
    private WebElement noReservationBannerElement;


    public List<WebElement> getReservations(){
        return reservations;
    }

    private void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void getTransactionID(List<WebElement> reservations){
        List<String> transactionIds = new ArrayList<>();
        for(WebElement reservation: reservations){
        WebElement transactionIdElement = reservation.findElement(By.xpath("./th"));
       transactionIds.add(transactionIdElement.getText());
    }
    for(String transactionId: transactionIds){
        System.out.println("Transaction ID is "+ transactionId);
    }
   }



   public void cancelReservations(List<WebElement> reservations){
    for(WebElement reservation: reservations){
        WebElement cancelElement = reservation.findElement(By.xpath("./td//button[text()='Cancel']"));
        waitForElementToBeClickable(cancelElement);
        cancelElement.click();
        System.out.println("Reservation is cancelled!");
    }
   }

   public void noReservationVerification(){
    wait.until(ExpectedConditions.visibilityOf(noReservationBannerElement));
    if(noReservationBannerElement.getText().contains("Oops!")){
        System.out.println("No reservations are there!");
    }else{
        System.out.println("You have reservations.");
    }
   }

   public void verifyReservations(List<WebElement> reservations,String dataset){
    boolean isVerified = true;
    for(WebElement reservation: reservations){
        wait.until(ExpectedConditions.visibilityOf(reservation));
        WebElement guestNameElement = reservation.findElement(By.xpath("./td[1]"));
        wait.until(ExpectedConditions.visibilityOf(guestNameElement));
        String guestName = guestNameElement.getText().trim();
        String[] datas = dataset.split(";");
        if(guestName.equals(datas[2])){
        WebElement advNameElement = reservation.findElement(By.xpath("./td[2]"));
        wait.until(ExpectedConditions.visibilityOf(advNameElement));
        String advName = advNameElement.getText().trim();
        WebElement countElement = reservation.findElement(By.xpath("./td[3]"));
        wait.until(ExpectedConditions.visibilityOf(countElement));
        String count = countElement.getText().trim();
        if(!(advName.equals(datas[1]) && count.equals(datas[4]))){
            isVerified = false;
            break;
        }
    }

    }
    if(isVerified){
        System.out.println("All the bookings are displayed in History page");
    }else{
        System.out.println("All the bookings are not displayed in History page");
    }
   }


}