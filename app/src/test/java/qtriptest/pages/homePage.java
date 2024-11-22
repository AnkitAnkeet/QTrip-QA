package qtriptest.pages;

import qtriptest.SeleniumWrapper;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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


    

    public void logOut() {
        try {
            // Attempt to click on the log out button
            boolean isClicked = SeleniumWrapper.clickIfCan(logOutButtonElement);  // This will click if the element is clickable
            if (isClicked) {
                // Wait for the register button to become visible to confirm logout
                wait.until(ExpectedConditions.visibilityOf(registerButton));
                System.out.println("Logged out successfully!");
            } else {
                System.out.println("Log out button was not clickable.");
            }
        } catch (NoSuchElementException e) {
            System.err.println("The log out button or register button was not found: " + e.getMessage());
        } catch (TimeoutException e) {
            System.err.println("Timeout while waiting for the register button: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during log out: " + e.getMessage());
        }
    }
    

    public void navigateToRegisterPage(){
        SeleniumWrapper.clickIfCan(registerButton);
    }

    public void navigateToLogInPage(){
       SeleniumWrapper.clickIfCan(loginHereElement);
    }

    public void search(String toSearch) throws InterruptedException{
        SeleniumWrapper.sendKeysIfCan(searchElement, toSearch);
        Thread.sleep(2000);
        SeleniumWrapper.sendKeysIfCan(searchElement, toSearch);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(searchResult));
    }

    public void clickOnResult() {
        try {
            // Wait for the search result to be visible
            wait.until(ExpectedConditions.visibilityOf(searchResult));
            
            // Check if the result contains "No City found"
            if (!(searchResult.getText().equals("No City found"))) {
                String nameOfCity = searchResult.getText();
                System.out.println("Name Of the city is found to be: " + nameOfCity);
                
                // Click on the search result if not "No City found"
                if (SeleniumWrapper.clickIfCan(searchResult)) {
                    System.out.println("Successfully clicked on the city result.");
                } else {
                    System.out.println("Failed to click on the city result.");
                }
            } else {
                // Log the case when no city is found
                System.out.println("No City found.");
            }
        } catch (Exception e) {
            // Handle unexpected exceptions
            System.err.println("An error occurred while interacting with the search result: " + e.getMessage());
        }
    }
    

    public void cityVerifiedAsPerSearched(String toSearch) {
        try {
            // Wait for search result to be visible
            wait.until(ExpectedConditions.visibilityOf(searchResult));
    
            // Check if search result matches the searched city
            String displayedCity = searchResult.getText().trim();
    
            if (toSearch.equals(displayedCity)) {
                System.out.println("City name is the same as displayed in auto-complete.");
            } else if (displayedCity.equals("No City found")) {
                System.out.println("No city found.");
            } else {
                System.out.println("City name is not the same as displayed in auto-complete.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while verifying the city: " + e.getMessage());
        }
    }
    

    public void navigateToHistoryPage(){
        SeleniumWrapper.clickIfCan(ReservationsButton);
        wait.until(ExpectedConditions.urlContains("reservations/"));
    }

    public void navigateToHomePage(){
        SeleniumWrapper.clickIfCan(homePageButton);
        wait.until(ExpectedConditions.elementToBeClickable(searchElement));
    }


}

