package qtriptest.pages;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.UUID; 

public class RegisterPage {
    RemoteWebDriver driver;
    private String email;
    private String password;
    public RegisterPage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver,10);
        PageFactory.initElements(ajax,this);
    }


    @FindBy(xpath = "//a[normalize-space() = 'Register']")
    private WebElement registerButton;

    @FindBy(xpath ="//input[@id='floatingInput']")
    private WebElement inputEmailElement;
    
    @FindBy(xpath ="//input[@id='floatingPassword' and @name='password']")
    private WebElement inputPasswordElement;

    @FindBy(xpath ="//input[@id='floatingPassword' and @name='confirmpassword']")
    private WebElement inputConfirmPasswordElement;

    @FindBy(xpath ="//button[normalize-space()='Register Now']")
    private WebElement registerNowButton;


    public void register(String email, String password, String confirmPassword, boolean isDynamic){
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOf(registerNowButton));
        if(isDynamic){
            String uuidStr = UUID.randomUUID().toString();
            email = String.format("%s"+email,uuidStr);
            this.email = email;
            this.password = password;
        }else{
            this.email=email;
            this.password=password;
        }
       

        Actions action = new Actions(driver);
        action.click(inputEmailElement).perform();
        action.sendKeys(inputEmailElement, email).perform();
        action.click(inputPasswordElement).perform();
        action.sendKeys(inputPasswordElement, password).perform();
        action.click(inputConfirmPasswordElement).perform();
        action.sendKeys(inputConfirmPasswordElement, confirmPassword).perform();
        action.click(registerNowButton).perform();
    }

    public String getRegisteredEmail(){
        return this.email;
    }
    public String getRegisteredPassword(){
        return this.password;
    }

}
