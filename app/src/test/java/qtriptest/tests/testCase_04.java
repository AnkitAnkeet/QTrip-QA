package qtriptest.tests;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class testCase_04 {
    RemoteWebDriver driver;
    homePage HomePage;
    RegisterPage registerPage;
    LoginPage loginPage;
    AdventurePage adventurePage;
    AdventureDetailsPage adventureDetailsPage;
    HistoryPage historyPage;


    @BeforeTest
    public void config() throws MalformedURLException{
        driver = DriverSingleton.getDriver("chrome");
        driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


        //initialization of required classes
         HomePage = new homePage(driver);
         adventurePage = new AdventurePage(driver);
         registerPage = new RegisterPage(driver);
         loginPage = new LoginPage(driver);
         adventureDetailsPage = new AdventureDetailsPage(driver);
         historyPage = new HistoryPage(driver);
        

    }
    @AfterTest
    public void endSession(){
     if(driver != null){
     driver.quit();
      }
    }
    @Test(priority = 4, dataProvider = "datasetforQTrip", dataProviderClass = DP.class, groups ={"Reliability Flow"})
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3) throws InterruptedException{
        HomePage.navigateToRegisterPage();
        registerPage.register(NewUserName, Password, Password, true);
        loginPage.logIn(registerPage.getRegisteredEmail(), Password);
        HomePage.search(adventureDetailsPage.getCityFromDataSet(dataset1));
        HomePage.clickOnResult();
        adventurePage.inputAdventureName(adventureDetailsPage.getAdventureFromDataSet(dataset1));
        adventurePage.getActivity().click();
        adventureDetailsPage.inputGuestName(adventureDetailsPage.getGuestNameFromDataSet(dataset1));
        adventureDetailsPage.inputDate(adventureDetailsPage.getDateFromDataSet(dataset1));
        adventureDetailsPage.inputPersonCount(adventureDetailsPage.getPersonCountFromDataSet(dataset1));
        adventureDetailsPage.submitForm();
        HomePage.navigateToHomePage();
        
        HomePage.search(adventureDetailsPage.getCityFromDataSet(dataset2));
        HomePage.clickOnResult();
        adventurePage.inputAdventureName(adventureDetailsPage.getAdventureFromDataSet(dataset2));
        adventurePage.getActivity().click();
        adventureDetailsPage.inputGuestName(adventureDetailsPage.getGuestNameFromDataSet(dataset2));
        adventureDetailsPage.inputDate(adventureDetailsPage.getDateFromDataSet(dataset2));
        adventureDetailsPage.inputPersonCount(adventureDetailsPage.getPersonCountFromDataSet(dataset2));
        adventureDetailsPage.submitForm();
        HomePage.navigateToHomePage();

        HomePage.search(adventureDetailsPage.getCityFromDataSet(dataset3));
        HomePage.clickOnResult();
        adventurePage.inputAdventureName(adventureDetailsPage.getAdventureFromDataSet(dataset3));
        adventurePage.getActivity().click();
        adventureDetailsPage.inputGuestName(adventureDetailsPage.getGuestNameFromDataSet(dataset3));
        adventureDetailsPage.inputDate(adventureDetailsPage.getDateFromDataSet(dataset3));
        adventureDetailsPage.inputPersonCount(adventureDetailsPage.getPersonCountFromDataSet(dataset3));
        adventureDetailsPage.submitForm();

        HomePage.navigateToHistoryPage();
        driver.navigate().refresh();
        List<WebElement> list = historyPage.getReservations();
        historyPage.verifyReservations(list,dataset1);
        historyPage.verifyReservations(list,dataset2);
        historyPage.verifyReservations(list,dataset3);

        HomePage.logOut();

    }
}
