package qtriptest;

import com.relevantcodes.extentreports.ExtentReports;

public class ReportSingleton {
  private static ExtentReports extentReports = null;

  final static String reportFilePath = System.getProperty("user.dir")+"/src/test/java/qtriptest/tests/ExtentReports/ExtentReport.html";

  //private constructor
  private ReportSingleton(){
  }

  //public accessable method
  public static synchronized ExtentReports getExtentReport(){ //synchronized keyword makes the thread "threadSafe"
        if(extentReports == null){
            extentReports = new ExtentReports(reportFilePath, true);
        }
        return extentReports;
    }
}