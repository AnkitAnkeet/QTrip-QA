package qtriptest.tests;

import qtriptest.DP;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.homePage;
import java.net.MalformedURLException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_02 extends BaseTest {
    homePage HomePage;
    AdventurePage adventurePage;


    @BeforeClass(alwaysRun = true)
    public void config() throws MalformedURLException{
        //initialization of required classes
        driver = SeleniumWrapper.driver;
         HomePage = new homePage(driver);
         adventurePage = new AdventurePage(driver);
         
        

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
