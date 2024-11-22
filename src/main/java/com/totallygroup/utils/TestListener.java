
package com.totallygroup.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    // ANSI color codes for the console
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(RED + "Test failed: " + result.getName() + " - " + result.getThrowable() + RESET);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(GREEN + "Test passed: " + result.getName() + RESET);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(YELLOW + "Test skipped: " + result.getName() + RESET);
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Starting test suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Finished test suite: " + context.getName());
        System.out.println("Total tests run: " + context.getAllTestMethods().length +
                ", " + GREEN + "Passes: " + context.getPassedTests().size() + RESET +
                ", " + RED + "Failures: " + context.getFailedTests().size() + RESET +
                ", " + YELLOW + "Skips: " + context.getSkippedTests().size() + RESET);
    }
}