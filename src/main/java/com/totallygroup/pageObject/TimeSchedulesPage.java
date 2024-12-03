
package com.totallygroup.pageObject;

import com.totallygroup.utils.DataStore;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.List;
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


    public static final String EMPLOYEE_LIST_CSS = ".VirtualEmployeeCell";
    public static final String DAY_OF_WEEK_CSS = ".defaultHeaderCell .text";
    public static final String FILLED_CELL_CSS = ".VirtualScheduleCell[style*='opacity: 0.5']";
    public static final String CELL_WITH_VIRTUAL_BACKGROUND_CSS = ".VirtualBackgroundCell.SchedulerBackgroundCell.allDayTAFW";
    public static final String SHIFT_MENU_ITEM_XPATH = "//td[@class='dijitReset dijitMenuItemLabel' and text()='%s']";
    public static final String EMPTY_CELL_CSS = "div.SchedulerVirtualGridEditControls";
    public static final String CELL_DROPDOWN_CSS = "td[id^='addButton_Timesheet'] div.dijitReset.dijitArrowButtonInner";
    public static final String EMPLOYEE_LABEL_CLASS = "employeeLabel";
//    public static final String SEARCH_BAR_BY_LABEL_XPATH = "//label[text()='%s']/parent::div//span[@class='k-searchbar']";
    public static final String SEARCH_BAR_BY_LABEL_XPATH = "//label[text()='%s']/parent::div//span[@class='k-searchbar']/input";
    public static final String BUTTON_BY_TEXT_XPATH = "//div[contains(@class, 'sc-jtHMlw')]//button[normalize-space(text())='%s']";





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
//        WebElement selectedEmployee = getSelectedEmployee();
//        if (selectedEmployee == null) {
//            logger.log(Level.WARNING, "No suitable employee found.");
//            return;
//        }
//
//        logger.info("Selected Employee: " + selectedEmployee.getText());
//
//        clickElement(selectedEmployee);
//
//        List<WebElement> daysOfWeek = waitForElementsToBeVisible(By.cssSelector(DAY_OF_WEEK_CSS));
//        for (WebElement day : daysOfWeek) {
//            logger.info("Processing day: " + day.getText());
//            if (isDayFilled(day)) {
//                logger.info("Day " + day.getText() + " is filled. Skipping.");
//                continue;
//            }
//
//            WebElement targetCell = getTargetCell(day);
//            if (targetCell != null) {
//                clickCellDownArrow();
//               //  clickElement(targetCell);
//                logger.info("Cell clicked for employee: " + selectedEmployee.getText());
//                dataStore.setValue("selectedDay", day.getText());
//                dataStore.setValue("selectedEmployee", selectedEmployee.getText());
//                clickOnAddScheduleMenu();
//                return;
//            }
//        }
//
//        logger.info("No empty cell found for the selected employee.");
//    }



    public void testSelectAvailableDayForEmployee() {
        WebElement selectedEmployee = getSelectedEmployee();
        if (selectedEmployee == null) {
            logger.log(Level.WARNING, "No suitable employee found.");
            return;
        }

        clickElement(selectedEmployee);

        List<WebElement> daysOfWeek = waitForElementsToBeVisible(By.cssSelector(DAY_OF_WEEK_CSS));
        for (WebElement day : daysOfWeek) {
            if (isDayFilled(day)) {
                continue;
            }

            WebElement targetCell = getTargetCell(day);
            if (targetCell != null) {
                if (isElementVisible(By.cssSelector(CELL_DROPDOWN_CSS))) {
                    clickCellDownArrow();
                    clickOnAddScheduleMenu();
                } else {
                    clickElement(targetCell);
                }

                dataStore.setValue("selectedDay", day.getText());
                dataStore.setValue("selectedEmployee", selectedEmployee.getText());
                return;
            }
        }

        logger.info("No empty cell found for the selected employee.");
    }


    private boolean isElementVisible(By selector) {
        try {
            WebElement element = driver.findElement(selector);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement getSelectedEmployee() {
        List<WebElement> employees = waitForElementsToBeVisible(By.cssSelector(EMPLOYEE_LIST_CSS));
        return employees.stream()
                .filter(e -> !e.findElement(By.className(EMPLOYEE_LABEL_CLASS)).getText().equals("[Unfilled]"))
                .findFirst()
                .orElse(null);
    }


    private boolean isDayFilled(WebElement day) {
        List<WebElement> filledCells = waitForElementsToBeVisible(By.cssSelector(FILLED_CELL_CSS));
        return filledCells.contains(day);
    }

    private WebElement getTargetCell(WebElement day) {
        WebElement emptyCell = waitForElementToBeVisible(By.cssSelector(EMPTY_CELL_CSS));
        return emptyCell != null ? emptyCell : waitForElementToBeVisible(By.cssSelector(CELL_WITH_VIRTUAL_BACKGROUND_CSS));
    }

    private void clickOnAddScheduleMenu() {
        waitForElementToBeClickable(By.xpath(String.format(SHIFT_MENU_ITEM_XPATH, "Add Schedule"))).click();
    }

    private void clickCellDownArrow() {
        click(By.cssSelector(CELL_DROPDOWN_CSS));
        logger.info("Cell down arrow clicked.");
    }

    public void verifyDayOfWeekMatchesTitle() {
        String selectedDay = (String) dataStore.getValue("selectedDay");
        if (selectedDay == null || selectedDay.isEmpty()) {
            throw new AssertionError("No day selected for verification.");
        }

        String formattedDay = selectedDay.split(", ")[1];

        WebElement titleElement = waitForElementToBeVisible(By.cssSelector("#scheduleSegmentEditor_title"));
        String titleText = titleElement.getText();

        String formattedTitleDate = titleText.split("-")[1].trim().split(" ")[0] + " " + titleText.split("-")[1].trim().split(" ")[1];

        Assert.assertEquals(formattedDay, formattedTitleDate, "Day of week does not match title date.");
        logger.info("Day " + formattedDay + " matches the title date: " + formattedTitleDate);
    }


    public void verifySearchBarTextMatchesSelectedEmployee() {
        WebElement searchBarInput = waitForElementToBeVisible(
                By.xpath(String.format(SEARCH_BAR_BY_LABEL_XPATH, "Employee"))
        );

        if (searchBarInput == null)
            throw new NoSuchElementException("Search bar input element not found for label 'Employee'");

        String searchBarText = validateNotEmpty(searchBarInput.getAttribute("value"), "Search bar input contains no value.");
        String selectedEmployee = validateNotEmpty((String) dataStore.getValue("selectedEmployee"), "Selected employee value is missing in the data store.");

        logger.info("Search bar text: " + searchBarText + ", Selected employee: " + selectedEmployee);
        Assert.assertEquals(searchBarText, selectedEmployee, "Selected employee does not match the search bar text.");
    }


public void clickButtonByText(String buttonText) {
    By locator = By.xpath(String.format(BUTTON_BY_TEXT_XPATH, buttonText));

    if (!waitForElementToBeClickable(locator).isEnabled()) {
        throw new IllegalStateException("Button with text '" + buttonText + "' is disabled and cannot be clicked.");
    }

    click(locator);
}


}
