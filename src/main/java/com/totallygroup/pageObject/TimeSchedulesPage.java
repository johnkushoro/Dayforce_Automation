
package com.totallygroup.pageObject;

import com.totallygroup.utils.DataStore;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
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
    public static final String SELECTED_ORGANISATION_CSS = "div.dijitTreeRowSelected span[role='treeitem']";

    //    public static final String ORGANISATION_BY_TEXT_XPATH = "//div[text()='%s']";


    public static final String EMPLOYEE_LIST_CSS = ".VirtualEmployeeCell";
    public static final String DAY_OF_WEEK_CSS = ".defaultHeaderCell .text";
    public static final String CELL_WITH_ANNUAL_LEAVE_CSS = ".VirtualBackgroundCell.SchedulerBackgroundCell.allDayTAFW";
    public static final String SHIFT_MENU_ITEM_XPATH = "//td[@class='dijitReset dijitMenuItemLabel' and text()='%s']";
    public static final String EMPTY_CELL_CSS = "div.SchedulerVirtualGridEditControls";
    public static final String SCHEDULED_CELL_LOCATOR = ".VirtualScheduleCell";

    public static final String CELL_DROPDOWN_CSS = "td[id^='addButton_Timesheet'] div.dijitReset.dijitArrowButtonInner";
    public static final String EMPLOYEE_LABEL_CLASS = "employeeLabel";
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


    public void selectRandomOrganisationLocation() throws InterruptedException {
        List<WebElement> organisationElements = waitForElementsToBeVisible(By.xpath(HIGHLIGHTED_ORGANISATION_XPATH));

        if (organisationElements.isEmpty()) {
            throw new NoSuchElementException("No organisation location elements found.");
        }

        WebElement randomOrganisation = organisationElements.get(ThreadLocalRandom.current().nextInt(organisationElements.size()));
        wait.until(ExpectedConditions.elementToBeClickable(randomOrganisation)).click();

        waitForElementsToNotBeVisible(By.xpath(HIGHLIGHTED_ORGANISATION_XPATH));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String organisationText = (String) js.executeScript("return arguments[0].innerText;", randomOrganisation);

        if (organisationText == null) {
            organisationText = "Unknown Organisation";
        }

        logger.info("Selected random organisation: " + organisationText);
        dataStore.setValue("selectedOrganisation", organisationText);


        WebElement selectedOrganisation = waitForElementToBeVisible(By.cssSelector(SELECTED_ORGANISATION_CSS));
        String selectedOrganisationText = selectedOrganisation.getText();

        if (!selectedOrganisationText.equals(organisationText)) {
            throw new AssertionError("Selected organisation text does not match the random organisation text. Expected: " + organisationText + ", Found: " + selectedOrganisationText);
        }

        logger.info("Verified selected organisation matches the random organisation: " + organisationText);
    }


    public void searchAndSelectAndClickOrganisation(String organisationText) {
        WebElement searchField = waitForElementToBeVisible(By.cssSelector(ORGANISATION_SEARCH_FIELD_CSS));
        searchField.sendKeys(organisationText);

        WebElement highlightedOrganisation = waitForElementToBeVisible(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
        if (highlightedOrganisation == null) {
            logger.log(Level.WARNING, "No highlighted organisation found for: " + organisationText);
            throw new NoSuchElementException("Organisation not found in search results.");
        }

        click(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
        waitForElementsToNotBeVisible(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));

        WebElement selectedOrganisation = waitForElementToBeVisible(By.cssSelector(SELECTED_ORGANISATION_CSS));
        String selectedOrganisationText = selectedOrganisation.getText();

        if (!selectedOrganisationText.equals(organisationText)) {
            throw new AssertionError("Selected organisation text does not match entered text. Expected: " + organisationText + ", Found: " + selectedOrganisationText);
        }

        logger.info("Verified selected organisation matches the entered text: " + organisationText);
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
            if (targetCell != null && isElementEmptyCell(targetCell)) {
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

    private boolean isElementEmptyCell(WebElement cell) {
        String cellClass = Objects.requireNonNull(cell.getAttribute("class"));

        boolean isScheduledCell = cellClass.contains(SCHEDULED_CELL_LOCATOR);
        boolean isAnnualLeaveCell = cellClass.contains(CELL_WITH_ANNUAL_LEAVE_CSS);

        return cellClass.contains("SchedulerVirtualGridEditControls") && !isScheduledCell && !isAnnualLeaveCell;
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
        List<WebElement> filledCells = waitForElementsToBeVisible(By.cssSelector(SCHEDULED_CELL_LOCATOR));
        return filledCells.contains(day);
    }

    private WebElement getTargetCell(WebElement day) {
        WebElement emptyCell = waitForElementToBeVisible(By.cssSelector(EMPTY_CELL_CSS));
        return emptyCell != null ? emptyCell : waitForElementToBeVisible(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));
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
        WebElement buttonTextLocator = driver.findElement(By.xpath(String.format(BUTTON_BY_TEXT_XPATH, buttonText)));

        scrollToElement(buttonTextLocator);
        waitForElementToBeClickable(By.xpath(String.format(BUTTON_BY_TEXT_XPATH, buttonText)));

        if (!buttonTextLocator.isEnabled()) {
            throw new IllegalStateException("Button with text '" + buttonText + "' is disabled and cannot be clicked.");
        }
        buttonTextLocator.click();
    }


    public Map<String, String> getSelectedEmployeeAndDay() {
        String selectedEmployee = (String) dataStore.getValue("selectedEmployee");
        String selectedDay = (String) dataStore.getValue("selectedDay");

        if (selectedEmployee == null || selectedEmployee.isEmpty()) {
            throw new IllegalStateException("No employee selected.");
        }

        if (selectedDay == null || selectedDay.isEmpty()) {
            throw new IllegalStateException("No day selected.");
        }

        Map<String, String> selection = new HashMap<>();
        selection.put("selectedEmployee", selectedEmployee);
        selection.put("selectedDay", selectedDay);

        return selection;
    }

//    public void performDragAndDropFromSelectedCell() {
//        Map<String, String> selection = getSelectedEmployeeAndDay();
//        String selectedEmployee = selection.get("selectedEmployee");
//        String selectedDay = selection.get("selectedDay");
//
//        WebElement employee = findEmployeeCell(selectedEmployee);
//        WebElement day = findDayCell(selectedDay);
//
//        if (employee != null && day != null) {
//            Actions actions = new Actions(driver);
//            actions.clickAndHold(employee)
//                    .moveToElement(day)
//                    .release()
//                    .perform();
//            logger.info("Dragged and dropped from " + selectedEmployee + " on " + selectedDay);
//        } else {
//            logger.log(Level.WARNING, "Unable to find employee or day for drag and drop.");
//        }
//    }


    public void performDragAndDropFromSelectedCell() {
        Map<String, String> selection = getSelectedEmployeeAndDay();
        String selectedEmployee = selection.get("selectedEmployee");
        String selectedDay = selection.get("selectedDay");

        WebElement employee = findEmployeeCell(selectedEmployee);
        WebElement day = findDayCell(selectedDay);

        if (employee != null && day != null) {
            Actions actions = new Actions(driver);
            actions.clickAndHold(employee)
                    .pause(Duration.ofMinutes(1))
                    .moveToElement(day)
                    .release()
                    .perform();
            logger.info("Dragged and dropped from " + selectedEmployee + " on " + selectedDay);
        } else {
            logger.log(Level.WARNING, "Unable to find employee or day for drag and drop.");
        }
    }



    private WebElement findEmployeeCell(String employeeName) {
        List<WebElement> employees = waitForElementsToBeVisible(By.cssSelector(EMPLOYEE_LIST_CSS));
        for (WebElement employee : employees) {
            if (employee.getText().equals(employeeName)) {
                return employee;
            }
        }
        return null;
    }

    private WebElement findDayCell(String day) {
        List<WebElement> daysOfWeek = waitForElementsToBeVisible(By.cssSelector(DAY_OF_WEEK_CSS));
        for (WebElement dayElement : daysOfWeek) {
            if (dayElement.getText().equals(day)) {
                return dayElement;
            }
        }
        return null;
    }



    public void performDragAndDropToAnotherEmployee() {
        Map<String, String> selection = getSelectedEmployeeAndDay();
        String sourceDay = selection.get("selectedDay");

        // Locate the source element using the given selector
        WebElement sourceElement = waitForElementToBeVisible(By.cssSelector("#content-cInner > div.SchedulerVirtualGridEditControls"));

        if (sourceElement == null) {
            throw new IllegalStateException("Source element for drag operation not found.");
        }

        List<WebElement> employees = waitForElementsToBeVisible(By.cssSelector(EMPLOYEE_LIST_CSS));
        boolean shiftDropped = false;

        for (WebElement targetEmployee : employees) {
            String targetEmployeeName = targetEmployee.findElement(By.className(EMPLOYEE_LABEL_CLASS)).getText();

            if (targetEmployeeName.equals("[Unfilled]")) {
                continue;
            }

            List<WebElement> daysOfWeek = waitForElementsToBeVisible(By.cssSelector(DAY_OF_WEEK_CSS));
            for (WebElement day : daysOfWeek) {
                if (!isDayFilled(day)) {
                    WebElement targetCell = getTargetCell(day);
                    if (targetCell != null) {
                        try {
                            // Perform the drag-and-drop action
                            Actions actions = new Actions(driver);
                            actions.clickAndHold(sourceElement)
                                    .moveToElement(targetCell)
                                    .release()
                                    .perform();

                            logger.info("Shift moved from source element to " + targetEmployeeName +
                                    " on " + day.getText());

                            waitForDOMToStabilize(); // Wait for DOM changes
                            shiftDropped = true;
                            break;
                        } catch (Exception e) {
                            logger.warning("Failed to drop shift on " + targetEmployeeName + " for day " + day.getText() + ": " + e.getMessage());
                        }
                    }
                }
            }

            if (shiftDropped) {
                break;
            }
        }

        if (!shiftDropped) {
            throw new IllegalStateException("Unable to drop the shift to any employee.");
        }

    }

}