package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    //private and static variable with null value
    private static RemoteWebDriver driver = null;

    //private constructor have to be explicitly specified to restrict the object creation
    private DriverSingleton(){
    }

    //public method for driver modification
    public static RemoteWebDriver getDriver(String browser) throws MalformedURLException{
        if(driver==null){
        browser = browser.toUpperCase();
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        switch(browser){
            case "CHROME":
            capabilities.setBrowserName(BrowserType.CHROME);
            break;

            case "FIREFOX" :
            capabilities.setBrowserName(BrowserType.FIREFOX);
            break;

            case "EDGE" :
            capabilities.setBrowserName(BrowserType.EDGE);
            break;

            default:
            throw new IllegalArgumentException("Unsupported browser: " + browser);

        }
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        }
        return driver;
    
}


}