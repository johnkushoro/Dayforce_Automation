
package com.totallygroup.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Component  // This makes the class a Spring Bean
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    // Constructor to initialize the report
    public ExtentReportManager() {
        String reportPath = "test-output/extent-report.html";  // Modify if needed
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    // Initialize the report
    public static void initializeReport() {
        // The initialization is handled by the constructor now
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.skip("Test skipped");
    }

    @Override
    public void onStart(ITestContext context) {
        initializeReport();
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
