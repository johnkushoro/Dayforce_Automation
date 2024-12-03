
package com.totallygroup.pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static com.totallygroup.utils.Config.logger;

@Component
public class CommonPage {

    protected final WebDriver driver;
    protected WebDriverWait wait;

    @Value("${page.load.timeout:20}")
    protected long timeoutInSeconds;

    @Autowired
    public CommonPage(WebDriver driver) {
        this.driver = Objects.requireNonNull(driver, "WebDriver cannot be null");
    }

    @PostConstruct
    private void initialize() {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
      //  logger.info("Page load timeout set to: " + timeoutInSeconds + " seconds.");
    }

    public void waitForPageLoad() {
        try {
            wait.until(
                    webDriver -> Objects.equals(
                            ((JavascriptExecutor) webDriver).executeScript("return document.readyState"),
                            "complete"
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Page did not load within " + timeoutInSeconds + " seconds.", e);
        }
    }

    public void waitForElementToDisappear(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            throw new RuntimeException("Element did not disappear: " + locator, e);
        }
    }

    public void scrollToElement(WebElement element) {
        try {
            Objects.requireNonNull(element, "Element to scroll to cannot be null");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to scroll to element.", e);
        }
    }

    public WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }

    public List<WebElement> waitForElementsToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            throw new RuntimeException("Elements not visible: " + locator, e);
        }
    }

    public WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }

    public WebElement waitForElementToBeVisibleAndClickable(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return element;
        } catch (Exception e) {
            throw new RuntimeException("Element not visible and clickable: " + locator, e);
        }
    }

    public void waitForElementsToNotBeVisible(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            throw new RuntimeException("Elements not visible: " + locator, e);
        }
    }

    public void click (By locator){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
    public void clickElementUsingJavaScript(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public String validateNotEmpty(String value, String errorMessage) {
        if (value == null || value.isEmpty()) {
            throw new NoSuchElementException(errorMessage);
        }
        return value;
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
            logger.info("Clicked on element: " + element.getText());
        } catch (ElementClickInterceptedException e) {
            clickElementUsingJavaScript(element);
            logger.info("Element clicked using JavaScript: " + element.getText());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click on element: " + element.getText(), e);
        }
    }

}
