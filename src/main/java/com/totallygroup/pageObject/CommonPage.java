//
//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.util.List;
//
//@Component
//public class CommonPage {
//
//    protected final WebDriver driver;
//    protected final WebDriverWait wait;
//    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(40);
//
//    @Autowired
//    public CommonPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT);
//    }
//
//    public void scrollToElement(WebElement element) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView(true);", element);
//    }
//
//    public WebElement waitForElementToBeVisible(By locator) {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//
//    public List<WebElement> waitForElementsToBeVisible(By locator) {
//        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
//    }
//
//    public WebElement waitForElementToBeClickable(By locator) {
//        return wait.until(ExpectedConditions.elementToBeClickable(locator));
//    }
//}




package com.totallygroup.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class CommonPage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(15);

    @Autowired
    public CommonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> waitForElementsToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    public WebElement waitForElementToBeVisibleAndClickable(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> waitForElementsToBeVisibleAndClickable(By locator) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

}
