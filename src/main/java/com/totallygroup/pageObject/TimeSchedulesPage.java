
package com.totallygroup.pageObject;

import com.totallygroup.utils.DataStore;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import static com.totallygroup.utils.Config.logger;

@Component
public class TimeSchedulesPage extends CommonPage {

    public static final String LOAD_PANE_CSS = "[aria-label='Toggle Load Pane']";
    public static final String ORG_PICKER_CSS = ".dijitIcon.OrgPicker_icon";
    public static final String DOWN_ARROW_BUTTON_XPATH = "//div[@id='Framework_UI_EntityPicker_EntityTreeSelector_0']//input[@type='button']";
    public static final String LIST_OF_ORGANISATIONS_SELECTOR = "div.dijitReset.dijitMenuItem";
    public static final String ORGANISATION_OK_BUTTON_XPATH = "//*[@id='dijit_TooltipDialog_4']//span[contains(text(), '%s')]";
    public static final String TOOLTIP_LOAD_BUTTON_XPATH = "//*[@id='Button_118']";
    public static final String ORGANISATION_SEARCH_FIELD_CSS = "#UI_ComboBox_2";
    public static final String LOADED_ORGANISATION_TITLE = "//div[@class='dijitOutput left-decoration DecorationText']";
    public static final String HIGHLIGHTED_ORGANISATION_CSS = ".dijitComboBoxHighlightMatch";
    public static final String HIGHLIGHTED_ORGANISATION_XPATH = "//div[@class='dijitReset dijitMenuItem']";
    //    public static final String ORGANISATION_BY_TEXT_XPATH = "//div[text()='%s']";

    private final DataStore dataStore;

    public TimeSchedulesPage(WebDriver driver, DataStore dataStore) {
        super(driver);
        this.dataStore = dataStore;
    }

    public void clickLoadPaneToggle() {
        waitForElementToBeVisibleAndClickable(By.cssSelector(LOAD_PANE_CSS)).click();
    }

    public void clickOrgPicker() {
        click(By.cssSelector(ORG_PICKER_CSS));
    }

    public void clickDownArrowButton() {
        waitForElementToBeVisibleAndClickable(By.xpath(DOWN_ARROW_BUTTON_XPATH)).click();
    }


//    public void selectRandomOrganisationLocation() throws InterruptedException {
//        List<WebElement> organisationElements = waitForElementsToBeVisible(By.xpath(HIGHLIGHTED_ORGANISATION_XPATH));
//
//        if (organisationElements.isEmpty()) throw new NoSuchElementException("No organisation location elements found.");
//
//        WebElement randomOrganisation = organisationElements.get(ThreadLocalRandom.current().nextInt(organisationElements.size()));
//        wait.until(ExpectedConditions.elementToBeClickable(randomOrganisation)).click();
//
//        waitForElementsToNotBeVisible(By.xpath(HIGHLIGHTED_ORGANISATION_XPATH));
//        Thread.sleep(2000);
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        String organisationText = (String) js.executeScript("return arguments[0].innerText;", randomOrganisation);
//
//        if (organisationText == null) {
//            organisationText = "Unknown Organisation";
//        }
//
//        logger.info("Selected random organisation: " + organisationText);
//        dataStore.setValue("selectedOrganisation", organisationText);
//    }




    public void searchAndSelectAndClickOrganisation(String organisationText) throws InterruptedException {
        WebElement searchField = waitForElementToBeVisible(By.cssSelector(ORGANISATION_SEARCH_FIELD_CSS));
        searchField.sendKeys(organisationText);

        WebElement highlightedOrganisation = waitForElementToBeVisible(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
        if (highlightedOrganisation == null) {
            logger.log(Level.WARNING, "No highlighted organisation found for: " + organisationText);
            throw new NoSuchElementException("Organisation not found in search results.");
        }

        click(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));

        waitForElementsToNotBeVisible(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
        Thread.sleep(2000);

        logger.info("Organisation clicked: " + organisationText);
    }


    public void clickOrganisationOkButton(String buttonText) {
        waitForElementToBeVisibleAndClickable(By.xpath(String.format(ORGANISATION_OK_BUTTON_XPATH, buttonText))).click();
    }

    public void clickTooltipLoadButton() {
        WebElement button = waitForElementToBeVisibleAndClickable(By.xpath(TOOLTIP_LOAD_BUTTON_XPATH));
        button.click();
    }

    public String getLoadedPageTitle() {
        String pageTitleText = waitForElementToBeVisible(By.xpath(LOADED_ORGANISATION_TITLE)).getText();
        if (pageTitleText.isEmpty()) throw new NoSuchElementException("Loaded organisation title is empty");
        logger.info("Loaded page title: " + pageTitleText);
        return pageTitleText;
    }



//    public void testSelectAvailableDayForEmployee() {
//        List<WebElement> employeeCells = waitForElementsToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//        Random random = new Random();
//        WebElement selectedEmployee;
//        String employeeName;
//
//        do {
//            selectedEmployee = employeeCells.get(random.nextInt(employeeCells.size()));
//            employeeName = selectedEmployee.findElement(By.className("employeeLabel")).getText();
//            logger.info("Selected Employee: " + employeeName);
//        } while (employeeName.equals("[Unfilled]"));
//
//        boolean daySelected = false;
//        List<WebElement> daysOfWeek = driver.findElements(By.xpath("//*[@id='header-cInner']/div[position()>1 and position()<=7]"));
//
//        for (WebElement day : daysOfWeek) {
//            if (checkAndSelectAvailableDay(selectedEmployee)) {
//                daySelected = true;
//                break;
//            }
//        }
//
//        if (!daySelected) {
//            logger.info("No available day for " + employeeName + ", moving to the next employee.");
//        }
//
//        Assert.assertTrue(daySelected, "No available day was selected.");
//    }
//
//
//    private boolean checkAndSelectAvailableDay(WebElement employee) {
//        List<WebElement> availableShifts = employee.findElements(By.cssSelector("div[class*='shiftBody']"));
//        for (WebElement shift : availableShifts) {
//            if (isShiftAvailable(shift)) {
//                selectDay(shift);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean isShiftAvailable(WebElement shift) {
//        // Check if the shift is available (based on opacity or any other condition)
//        return shift.getCssValue("opacity").equals("1"); // Assuming opacity 1 means available
//    }
//
//    private void selectDay(WebElement shift) {
//        // Click on the available shift to select the day
//        try {
//            WebElement clickableShift = wait.until(ExpectedConditions.elementToBeClickable(shift));
//            clickableShift.click();
//            logger.info("Day selected.");
//        } catch (ElementNotInteractableException e) {
//            logger.info("Failed to select shift, retrying...");
//            wait.until(ExpectedConditions.elementToBeClickable(shift)).click();
//        }
//    }


    public void testSelectAvailableDayForEmployee() {
        List<WebElement> employeeCells = waitForElementsToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
        Random random = new Random();
        WebElement selectedEmployee;
        String employeeName;

        do {
            selectedEmployee = employeeCells.get(random.nextInt(employeeCells.size()));
            employeeName = selectedEmployee.findElement(By.className("employeeLabel")).getText();
            logger.info("Selected Employee: " + employeeName);
        } while (employeeName.equals("[Unfilled]"));

        boolean daySelected = false;
        List<WebElement> daysOfWeek = driver.findElements(By.xpath("//*[@id='header-cInner']/div[position()>1 and position()<=7]"));

        for (WebElement day : daysOfWeek) {
            logger.info("Checking availability for " + employeeName + " on day: " + day.getText());
            if (checkAndSelectAvailableDay(selectedEmployee)) {
                daySelected = true;
                break;
            }
        }

        if (!daySelected) {
            logger.info("No available day for " + employeeName + ", moving to the next employee.");
        }

        Assert.assertTrue(daySelected, "No available day was selected.");
    }

    private boolean checkAndSelectAvailableDay(WebElement employee) {
        List<WebElement> availableShifts = employee.findElements(By.cssSelector("div[class*='shiftBody']"));

        if (availableShifts.isEmpty()) {
            logger.info("No available shifts for this employee.");
        }

        for (WebElement shift : availableShifts) {
            logger.info("Checking shift availability.");
            if (isShiftAvailable(shift)) {
                logger.info("Available shift found, selecting.");
                selectDay(shift);
                return true;
            }
        }
        return false;
    }

    private boolean isShiftAvailable(WebElement shift) {
        // Check if the shift is available (could be based on class name, visibility, etc.)
        String opacity = shift.getCssValue("opacity");
        logger.info("Shift opacity: " + opacity);
        return opacity.equals("1"); // Assuming opacity 1 means available
    }

    private void selectDay(WebElement shift) {
        try {
            WebElement clickableShift = wait.until(ExpectedConditions.elementToBeClickable(shift));
            clickableShift.click();
            logger.info("Day selected.");
        } catch (ElementNotInteractableException e) {
            logger.info("Failed to select shift, retrying...");
            wait.until(ExpectedConditions.elementToBeClickable(shift)).click();
        }
    }

}
