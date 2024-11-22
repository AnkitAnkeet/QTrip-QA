package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;
    
    public AdventurePage(RemoteWebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver,10);
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(this.driver,10);
        PageFactory.initElements(ajx,this);
    }


    @FindBy(xpath="//select[@id='duration-select']")
    private  WebElement durationDropdown;

    @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
    private WebElement clearDurationFilterButton;


    @FindBy(xpath ="//select[@id='category-select']")
    private WebElement categoryDropdown;

    @FindBy(xpath ="//div[@onclick='clearCategory(event)']")
    private WebElement clearCategoryFilterButton;


    @FindBy(xpath ="//input[@id='search-adventures']")
    private WebElement searchAdventureTextBox;

    @FindBy(xpath ="//div[@onclick = 'resetAdventuresData()']")
    private WebElement clearAdventureNameButton;

    @FindBy(xpath ="//div[@class='col-6 col-lg-3 mb-4']")
    private List<WebElement> activities;

    @FindBy(xpath ="//div[@class='col-6 col-lg-3 mb-4']/a")
    private WebElement activity;



    private void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public void selectFilterByDuration(String duration) {
        try {
            waitForElementToBeClickable(durationDropdown); // Ensure dropdown is ready to be interacted with
            Select select = new Select(durationDropdown);
            select.selectByVisibleText(duration); // Select the desired duration by visible text
            System.out.println("Duration '" + duration + "' selected successfully.");
        } catch (NoSuchElementException e) {
            System.err.println("Error: The specified duration '" + duration + "' is not available in the dropdown. " + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.err.println("Error: The duration dropdown is not interactable. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while selecting the duration: " + e.getMessage());
        }
    }
    

    public void clearDurationFilter(){
        SeleniumWrapper.clickIfCan(clearDurationFilterButton);
    }

    public void selectFilterByCategory(String category) {
        try {
            waitForElementToBeClickable(categoryDropdown); // Ensure the dropdown is clickable
            Select select = new Select(categoryDropdown);
            select.selectByVisibleText(category); // Select the desired category by visible text
            System.out.println("Category '" + category + "' selected successfully.");
        } catch (NoSuchElementException e) {
            System.err.println("Error: The specified category '" + category + "' is not available in the dropdown. " + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.err.println("Error: The dropdown is not interactable. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while selecting the category: " + e.getMessage());
        }
    }
    

    public void clearCategoryFilter(){
        SeleniumWrapper.clickIfCan(clearCategoryFilterButton);
    }

    public List<WebElement> getActivitiyList(){
        return activities;
    }

    public WebElement getActivity(){
        return activity;
    }

    public void validateActivitesByDuration(List<WebElement> filteredList, String duration){
        boolean validated = true;
        for(WebElement activity:filteredList){
        String durationStr = activity.findElement(By.xpath(".//p[contains(text(),'Hours')]")).getText();
        int durationInt = extractNumericValue(durationStr);

        switch(duration){
            case "0-2 Hours":
            if(!(durationInt>0 && durationInt<=2)){
            validated = false;
            }
            break;
            case "2-6 Hours":
            if(!(durationInt>2 && durationInt<=6)){
            validated = false;
            }
            break;
            case "6-12 Hours":
            if(!(durationInt>6 && durationInt<=12)){
            validated = false;
            }
            break;
            case "12+ Hours":
            if(!(durationInt>12)){
            validated = false;
            }
            break;
            default:
            validated = true;
            break;
        }
     }
     if(validated){
        System.out.println("Appropriate Data are displayed according to duration.");
     }else{
        System.out.println("Appropriate Data are not displayed according to duration.");
     }
  }

   public static int extractNumericValue(String str){
    String result = "";
    for(int i=0;i<str.length()-1;i++){
        if(Character.isDigit(str.charAt(i))){
            result += str.charAt(i);
        } 
    }return Integer.valueOf(result);
   
   }



   public void validateActivitesByCategory(List<WebElement> filteredList, String category) {
    try {
        boolean validated = true;
        for (WebElement activity : filteredList) {
            // Fetch the category from the activity element
            String categoryStr = activity.findElement(By.xpath(".//div[@class='category-banner']")).getText().toLowerCase();
            category = category.toLowerCase();
            
            // Validate if the category string contains the expected text
            if (!category.contains(categoryStr)) {
                validated = false;
                break;
            }
        }
        if (validated) {
            System.out.println("Appropriate data are displayed according to the category.");
        } else {
            System.out.println("Appropriate data are NOT displayed according to the category.");
        }
    } catch (NoSuchElementException e) {
        System.err.println("Error: Category element not found in one or more activities. " + e.getMessage());
    } catch (Exception e) {
        System.err.println("An unexpected error occurred while validating activities: " + e.getMessage());
    }
}



   public void inputAdventureName(String AdventureName){
    SeleniumWrapper.sendKeysIfCan(searchAdventureTextBox,AdventureName);
   }

   public void clearAdventureTextBox(){
   SeleniumWrapper.clickIfCan(clearAdventureNameButton);
   }
  
}
 
