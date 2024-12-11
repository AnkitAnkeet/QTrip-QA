package qtriptest;

import java.util.HashMap;
import java.util.Map;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestManager {
    static ExtentReports extentReports = ReportSingleton.getExtentReport();
    static Map<Object,Object> extentTestMap = new HashMap<>();
    
    public static ExtentTest startTest(String testName){
        ExtentTest extentTest = extentReports.startTest(testName);
        extentTestMap.put((int)(long)Thread.currentThread().getId(),extentTest);

        return extentTest;
    }
    public static ExtentTest getTest(){
        return (ExtentTest) extentTestMap.get((int)(long)Thread.currentThread().getId());
    }

    public static void testLogger(LogStatus logStatus, String description){
        getTest().log(logStatus, description);
    }

    public static void endTest(){
        extentReports.endTest(getTest());
    }
    
}
