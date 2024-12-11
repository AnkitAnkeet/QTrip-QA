package qtriptest.tests;


import qtriptest.pages.homePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.DP;

import java.net.MalformedURLException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class TestCase_01 extends BaseTest{
   homePage HomePage;
   LoginPage loginPage;
   RegisterPage registerPage;
   SoftAssert asserts;


  @BeforeClass(alwaysRun = true)
  public void config() throws MalformedURLException{

    
     HomePage = new homePage(driver);
     loginPage = new LoginPage(driver);
     registerPage = new RegisterPage(driver);
     asserts = new SoftAssert();

  }
 
 
  @Test(priority = 1, dataProvider = "datasetforQTrip", dataProviderClass = DP.class, groups={"Login Flow"})
  public void TestCase01(String email, String password) throws InterruptedException{
    HomePage.navigateToRegisterPage();
    registerPage.register(email,password,password,true);
    HomePage.navigateToLogInPage();
    loginPage.logIn(registerPage.getRegisteredEmail(),registerPage.getRegisteredPassword());
    Thread.sleep(2000);
    HomePage.logOut();
    Thread.sleep(2000);
  }
  
}

