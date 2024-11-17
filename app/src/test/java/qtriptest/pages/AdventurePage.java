package qtriptest.pages;

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

public class AdventurePage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;
    
    public AdventurePage(RemoteWebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
        AjaxElementLocatorFactory ajx = new AjaxElementLocatorFactory(driver,10);
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
    public void selectFilterByDuration(String duration){
        waitForElementToBeClickable(durationDropdown);
        Select select = new Select(durationDropdown);
        select.selectByVisibleText(duration); 
    }

    public void clearDurationFilter(){
        waitForElementToBeClickable(clearDurationFilterButton);
        clearDurationFilterButton.click();
    }

    public void selectFilterByCategory(String category){
        waitForElementToBeClickable(categoryDropdown);
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(category);
    }

    public void clearCategoryFilter(){
        waitForElementToBeClickable(clearCategoryFilterButton);
        clearCategoryFilterButton.click();
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



   public void validateActivitesByCategory(List<WebElement> filteredList, String category){
    boolean validated = true;
    for(WebElement activity:filteredList){
    String categoryStr = activity.findElement(By.xpath(".//div[@class='category-banner']")).getText().toLowerCase();
    category = category.toLowerCase();
    if(!(category.contains(categoryStr))){
        validated = false;
        break;
    }
    }if(validated){
        System.out.println("Appropriate Data are displayed according to category.");
     }else{
        System.out.println("Appropriate Data are not displayed according to category.");
     }

   }


   public void inputAdventureName(String AdventureName){
     waitForElementToBeClickable(searchAdventureTextBox);
     searchAdventureTextBox.click();
     searchAdventureTextBox.clear();
     searchAdventureTextBox.sendKeys(AdventureName);
   }

   public void clearAdventureTextBox(){
    waitForElementToBeClickable(clearAdventureNameButton);
    clearAdventureNameButton.click();
   }
  
}
 