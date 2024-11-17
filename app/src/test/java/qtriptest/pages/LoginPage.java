package qtriptest.pages;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

public class LoginPage{
    RemoteWebDriver driver;
    public LoginPage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver,10);
        PageFactory.initElements(ajax,this);
    }

    @FindBy(xpath="//input[@id='floatingInput']")
    private WebElement inputEmailElement; 
    
    @FindBy(xpath="//input[@id='floatingPassword']")
    private WebElement inputPasswordElement;

    @FindBy(xpath ="//button[contains(text(),'Login to')]")
    private WebElement logInButtonElement;

    //login with registered credentials
    
    public void logIn(String email, String password) throws InterruptedException{
    WebDriverWait wait = new WebDriverWait(driver,10);
    wait.until(ExpectedConditions.visibilityOf(logInButtonElement));
    //creating an instance of RegisterPage class to use that particular email used in registration process
    //RegisterPage registerPage = new RegisterPage(driver);
    Actions action = new Actions(driver);
    action.click(inputEmailElement).perform();
    action.sendKeys(inputEmailElement,email).perform();
    action.click(inputPasswordElement).perform();
    action.sendKeys(inputPasswordElement,password).perform();
    action.click(logInButtonElement).perform();
    Thread.sleep(3000);
    }

    //login with user defined credentials(method overloading)
    // public void logIn(String email,String password){
    //     WebDriverWait wait = new WebDriverWait(driver,10);
    //     wait.until(ExpectedConditions.visibilityOf(logInButtonElement));

    //     Actions action = new Actions(driver);
    //     action.click(inputEmailElement).perform();
    //     action.sendKeys(inputEmailElement,email).perform();
    //     action.click(inputPasswordElement).perform();
    //     action.sendKeys(inputPasswordElement,password).perform();
    //     action.click(logInButtonElement).perform();
    //     System.out.println("User loggedin successfully!");

    }


