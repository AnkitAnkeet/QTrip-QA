package qtriptest.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class homePage{
    RemoteWebDriver driver;
    public homePage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver,30);
        PageFactory.initElements(ajax,this);
    }


    @FindBy(xpath = "//a[normalize-space() = 'Register']")
    private WebElement registerButton;

    @FindBy(xpath = "//div[normalize-space() = 'Logout']")
    private WebElement logOutButtonElement;

    @FindBy(xpath = "//a[text()='Login Here']")
    private WebElement loginHereElement;

    public void logOut(){
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(logOutButtonElement)).click();
        //verify if logOut is successful
        wait.until(ExpectedConditions.visibilityOf(registerButton));
        System.out.println("Logged Out successfully!");
        }

    public void navigateToRegisterPage(){
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public void navigateToLogInPage(){
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(loginHereElement)).click();
    }
}

