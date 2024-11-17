package qtriptest.tests;


import qtriptest.pages.homePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class TestCase_01{
   RemoteWebDriver driver;
   homePage HomePage;
   LoginPage loginPage;
   RegisterPage registerPage;
   SoftAssert asserts;


  @BeforeTest(alwaysRun = true)
  public void config() throws MalformedURLException{

        //final DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setBrowserName(BrowserType.CHROME);
        // driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        //  driver.manage().window().maximize();
        //  driver.manage().deleteAllCookies();


    // ChromeOptions options = new ChromeOptions();
    // options.addArguments("--no-sandbox");
    // options.addArguments("--disable-gpu");
    // driver = new RemoteWebDriver(new URL("http://localhost:8082/wb/hub"),options);

    

    driver = DriverSingleton.getDriver("chrome");

    driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
    driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


     HomePage = new homePage(driver);
     loginPage = new LoginPage(driver);
     registerPage = new RegisterPage(driver);
     asserts = new SoftAssert();

  }
   
    @AfterTest
    public void endSession(){
     if(driver != null){
     driver.quit();
      }
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

