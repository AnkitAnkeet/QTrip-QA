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
    WebDriverWait wait;
    public homePage(RemoteWebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver,30);
        PageFactory.initElements(ajax,this);
    }


    @FindBy(xpath = "//a[normalize-space() = 'Register']")
    private WebElement registerButton;

    @FindBy(xpath = "//div[normalize-space() = 'Logout']")
    private WebElement logOutButtonElement;

    @FindBy(xpath = "//a[text()='Login Here']")
    private WebElement loginHereElement;

    @FindBy(xpath = "//input[@id='autocomplete']")
    private WebElement searchElement;

    @FindBy(xpath = "(//ul[@id='results']/*)[1]")
    private WebElement searchResult;

    @FindBy(xpath = "//a[text()='Reservations']")
    private WebElement ReservationsButton;

    @FindBy(xpath = "//a[text()='Home']")
    private WebElement homePageButton;


    private void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void logOut(){
        waitForElementToBeClickable(logOutButtonElement);
        logOutButtonElement.click();
        //verify if logOut is successful
        wait.until(ExpectedConditions.visibilityOf(registerButton));
        System.out.println("Logged Out successfully!");
        }

    public void navigateToRegisterPage(){
        waitForElementToBeClickable(registerButton);
        registerButton.click();
    }

    public void navigateToLogInPage(){
       waitForElementToBeClickable(loginHereElement);
       loginHereElement.click();
    }

    public void search(String toSearch) throws InterruptedException{
        waitForElementToBeClickable(searchElement);
        searchElement.click();
        searchElement.clear();
        searchElement.sendKeys(toSearch);
        Thread.sleep(2000);
        // Actions action = new Actions(driver);
        // action.sendKeys(Keys.BACK_SPACE).perform();
       // wait.until(ExpectedConditions.visibilityOf(searchResult));

        searchElement.click();
        searchElement.clear();
        searchElement.sendKeys(toSearch);
        Thread.sleep(2000);
        // Actions action = new Actions(driver);
        // action.sendKeys(Keys.BACK_SPACE).perform();
        wait.until(ExpectedConditions.visibilityOf(searchResult));



    }

    public void clickOnResult(){
        wait.until(ExpectedConditions.visibilityOf(searchResult));
        if(!(searchResult.getText().equals("No City found"))){
        String nameOfCity = searchResult.getText();
        System.out.println("Name Of the city is found to be:"+nameOfCity);
        searchResult.click();
        }else{
          System.out.println(searchResult.getText());  
        }
    }

    public void cityVerifiedAsPerSearched(String toSearch){
        if(toSearch.equals(searchResult.getText())){
            System.out.println("City name is same as displayed on auto complete");
        }else if(searchResult.getText().equals("No City found")){
            System.out.println("No City found");
        }else{
            System.out.println("City name is not same as displayed on auto complete");
        }
    }
    public void navigateToHistoryPage(){
        waitForElementToBeClickable(ReservationsButton);
        ReservationsButton.click();
        wait.until(ExpectedConditions.urlContains("reservations/"));
    }

    public void navigateToHomePage(){
        waitForElementToBeClickable(homePageButton);
        homePageButton.click();
        waitForElementToBeClickable(searchElement);
    }


}

