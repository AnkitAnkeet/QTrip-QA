package qtriptest.tests;

import qtriptest.DP;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.*;
import java.net.MalformedURLException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class testCase_03 extends BaseTest {
    homePage HomePage;
    RegisterPage registerPage;
    LoginPage loginPage;
    AdventurePage adventurePage;
    AdventureDetailsPage adventureDetailsPage;
    HistoryPage historyPage;


    @BeforeClass(alwaysRun = true)
    public void config() throws MalformedURLException{

        //initialization of required classes
        driver = SeleniumWrapper.driver;
         HomePage = new homePage(driver);
         adventurePage = new AdventurePage(driver);
         registerPage = new RegisterPage(driver);
         loginPage = new LoginPage(driver);
         adventureDetailsPage = new AdventureDetailsPage(driver);
         historyPage = new HistoryPage(driver);
        

    }
     
    @Test(priority = 3, dataProvider = "datasetforQTrip", dataProviderClass = DP.class, groups ={"Booking and Cancellation Flow"})
    public void TestCase03(String NewUserName, String Password, String SearchCity, String AdventureName, String GuestName, String Date, String count) throws InterruptedException{
        HomePage.navigateToRegisterPage();
        registerPage.register(NewUserName, Password, Password, true);
        loginPage.logIn(registerPage.getRegisteredEmail(), Password);
        HomePage.search(SearchCity);
        HomePage.clickOnResult();
        adventurePage.inputAdventureName(AdventureName);
        adventurePage.getActivity().click();
        adventureDetailsPage.inputGuestName(GuestName);
        adventureDetailsPage.inputDate(Date);
        adventureDetailsPage.inputPersonCount(count);
        adventureDetailsPage.submitForm();
        adventureDetailsPage.verifySuccessfulReservation();
        HomePage.navigateToHistoryPage();
        historyPage.getTransactionID(historyPage.getReservations());
        historyPage.cancelReservations(historyPage.getReservations());
        Thread.sleep(2000);
        driver.navigate().refresh();
        historyPage.noReservationVerification();
        HomePage.logOut();
        
    }
     
}
