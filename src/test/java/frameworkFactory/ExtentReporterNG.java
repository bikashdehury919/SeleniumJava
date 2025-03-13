package frameworkFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {
    private static ExtentReports extent;

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);

        // Configure the ExtentSparkReporter
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle(fileName);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setReportName(fileName);

        // Attach the reporter to ExtentReports and set system information
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Automation Tester", "Bikash Dehury");
        extent.setSystemInfo("Organization", "Practice");

        return extent;
    }
}
