package qtriptest.tests;

import qtriptest.ExtentTestManager;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected RemoteWebDriver driver;
    
    @BeforeSuite(alwaysRun = true)
    public void init(){
        driver = SeleniumWrapper.driver;
        SeleniumWrapper.navigate("https://qtripdynamic-qa-frontend.vercel.app/");
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforTest(Method method){
        ExtentTestManager.startTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult iTestResult) throws IOException{
        if(iTestResult.getStatus() == ITestResult.SUCCESS ){
            ExtentTestManager.testLogger(LogStatus.PASS, "Test step is passed.");
        }else if(iTestResult.getStatus() == ITestResult.FAILURE){
            ExtentTestManager.testLogger(LogStatus.FAIL, ExtentTestManager.getTest().addScreenCapture(SeleniumWrapper.capture(driver)));
            
        }else{
            ExtentTestManager.testLogger(LogStatus.SKIP, "Test step is skipped.");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void quitDriver(){
        if(driver != null){
        driver.close();
        driver.quit();
        }
        ExtentTestManager.endTest();
        ReportSingleton.getExtentReport().flush();
    }


    
}
