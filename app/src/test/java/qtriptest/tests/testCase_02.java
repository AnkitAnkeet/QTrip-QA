package qtriptest.tests;

import qtriptest.DP;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.homePage;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class testCase_02 {
    RemoteWebDriver driver;
    homePage HomePage;
    AdventurePage adventurePage;


    @BeforeTest(alwaysRun = true)
    public void config() throws MalformedURLException{
        driver = SeleniumWrapper.driver;
        SeleniumWrapper.navigate("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

       
        //initialization of required classes
         HomePage = new homePage(driver);
         adventurePage = new AdventurePage(driver);
         
        

    }
    @AfterTest(alwaysRun = true)
    public void endSession(){
     if(driver != null){
     driver.quit();
      }
    }

     
    @Test(priority = 2, dataProvider = "datasetforQTrip", dataProviderClass = DP.class, groups={"Search and Filter flow"})
    public void TestCase02(String CityName,String Category_Filter,String DurationFilter,String ExpectedFilteredResults,String ExpectedUnFilteredResults) throws InterruptedException{
        HomePage.search(CityName);
        HomePage.cityVerifiedAsPerSearched(CityName);
        HomePage.clickOnResult();
        adventurePage.selectFilterByDuration(DurationFilter);
        List<WebElement> filteredListAccToDuration = adventurePage.getActivitiyList();
        adventurePage.validateActivitesByDuration(filteredListAccToDuration, DurationFilter);
        adventurePage.selectFilterByCategory(Category_Filter);
        List<WebElement> filteredListAccToCatAndDur = adventurePage.getActivitiyList();
        System.out.println("filtered-in successfully"+ (filteredListAccToCatAndDur.size() == Integer.valueOf(ExpectedFilteredResults)));
        adventurePage.validateActivitesByCategory(filteredListAccToCatAndDur, Category_Filter);
        adventurePage.clearCategoryFilter();
        adventurePage.clearDurationFilter();
        List<WebElement> filteredOutList = adventurePage.getActivitiyList();
        System.out.println("filtered-out successfully"+ (filteredOutList.size() == Integer.valueOf(ExpectedUnFilteredResults)));
        driver.navigate().back();  
    }
}
