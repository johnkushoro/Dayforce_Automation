
package com.totallygroup.pageObject;

import com.totallygroup.utils.DataStore;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.*;
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
    /// public static final String DAY_OF_WEEK_CSS = ".defaultHeaderCell .text";
    public static final String DAY_OF_WEEK_CSS = "div.defaultHeaderCell div.text";
    public static final String CELL_WITH_ANNUAL_LEAVE_CSS = ".VirtualBackgroundCell.SchedulerBackgroundCell.allDayTAFW";
    public static final String SHIFT_MENU_ITEM_XPATH = "//td[@class='dijitReset dijitMenuItemLabel' and text()='%s']";
    public static final String EMPTY_CELL_CSS = "div.SchedulerVirtualGridEditControls";
    public static final String SCHEDULED_CELL_LOCATOR = ".VirtualScheduleCell";
    public static final String SHIFT_BOARD_CSS = "#content-cInner";
    public static final String CELL_DROPDOWN_CSS = "td[id^='addButton_Timesheet'] div.dijitReset.dijitArrowButtonInner";
    public static final String EMPLOYEE_LABEL_CLASS = "employeeLabel";
    public static final String SEARCH_BAR_BY_LABEL_XPATH = "//label[text()='%s']/parent::div//span[@class='k-searchbar']/input";
    public static final String BUTTON_BY_TEXT_XPATH = "//div[contains(@class, 'sc-jtHMlw')]//button[normalize-space(text())='%s']";
    private static final String SHIFT_NAME_SELECTOR = "name";
    private static final String EMPLOYEE_NAME_SELECTOR = "employeeLabel";
    private static final String DAY_OF_WEEK_HEADER = ".defaultHeaderCell.headerCellFocus";
    private static final String COLUMN_CSS = "div.VirtualBackgroundCell.selectedBackground";




    private final DataStore dataStore;
    private final Actions actions;


    public TimeSchedulesPage(WebDriver driver, DataStore dataStore) {
        super(driver);
        this.dataStore = dataStore;
        this.actions = new Actions(driver);

    }



    public void clickLoadPaneToggle() {
        WebElement loadPaneToggle = waitForElementToBeVisibleAndClickable(By.cssSelector(LOAD_PANE_CSS));
        loadPaneToggle.click();
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




    public WebElement getOrganisationSearchField() {
        return waitForElementToBeVisible(By.cssSelector(ORGANISATION_SEARCH_FIELD_CSS));
    }

    public WebElement getHighlightedOrganisation() {
        return waitForElementToBeVisible(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
    }

    public WebElement getSelectedOrganisation() {
        return waitForElementToBeVisible(By.cssSelector(SELECTED_ORGANISATION_CSS));
    }

    public void clickOrganisation(WebElement organisation) {
        organisation.click();
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

        WebElement contentInner = driver.findElement(By.cssSelector("#content-cInner"));
        contentInner.click();

        WebElement selectedEmployee = getSelectedEmployee();
        if (selectedEmployee == null) {
            logger.log(Level.WARNING, "No suitable employee found.");
            return;
        }

        List<WebElement> availableDays = getAvailableDaysOfWeek();
        if (!availableDays.isEmpty()) {
            for (WebElement day : availableDays) {
                WebElement targetCell = getTargetCell(day);  // Get the target cell for the day
                if (targetCell != null && isDayFilled(targetCell)) {  // If the cell is filled, skip the day
                    continue;
                }

                System.out.println("Clicking on day: " + day.getText());
                clickElement(day);
                clickElement(selectedEmployee);

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
        } else {
            System.err.println("No available days to click.");
        }

        logger.info("No empty cell found for the selected employee.");
    }

    private boolean isDayFilled(WebElement cell) {
        return !cell.getText().isEmpty();
    }

    public List<WebElement> getAvailableDaysOfWeek() {
        List<WebElement> daysOfWeek = waitForElementsToBeVisible(By.cssSelector(DAY_OF_WEEK_CSS));
        if (daysOfWeek != null && !daysOfWeek.isEmpty()) {
            List<WebElement> availableDays = new ArrayList<>();
            for (WebElement day : daysOfWeek) {
                // Only add days that are not filled
                WebElement targetCell = getTargetCell(day);
                if (targetCell != null && !isDayFilled(targetCell)) {
                    availableDays.add(day);
                }
            }
            return availableDays;
        }
        return Collections.emptyList();  // return an empty list if no available days
    }


    private boolean isElementEmptyCell(WebElement cell) {
        String cellClass = cell.getAttribute("class");

        if (cellClass == null || cellClass.isEmpty()) {
            return false;
        }

        boolean isScheduledCell = cellClass.contains(SCHEDULED_CELL_LOCATOR);
        boolean isAnnualLeaveCell = cellClass.contains(CELL_WITH_ANNUAL_LEAVE_CSS);

        return cellClass.contains("SchedulerVirtualGridEditControls") && !isScheduledCell && !isAnnualLeaveCell;
    }

    public String validateNotEmpty(String value, String errorMessage) {
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException(errorMessage);
        }
        return value;
    }


    private WebElement getSelectedEmployee() {
        List<WebElement> employees = waitForElementsToBeVisible(By.cssSelector(EMPLOYEE_LIST_CSS));
        return employees.stream()
                .filter(e -> !e.findElement(By.className(EMPLOYEE_LABEL_CLASS)).getText().equals("[Unfilled]"))
                .findFirst()
                .orElse(null);
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

    public void performDragAndDropFromSelectedCell() {
        Map<String, String> selection = getSelectedEmployeeAndDay();
        String selectedEmployee = selection.get("selectedEmployee");
        String selectedDay = selection.get("selectedDay");

        WebElement employee = findEmployeeCell(selectedEmployee);
        WebElement day = findDayCell(selectedDay);

        if (employee != null && day != null) {
            Actions actions = new Actions(driver);
            actions.clickAndHold(employee)
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


    public void moveThroughAnnualLeaveColumnCell() {
        WebElement contentInner = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
        contentInner.click();

        List<WebElement> annualLeaveCells = waitForElementsToBeVisible(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));

        Actions actions = new Actions(driver);

        for (int i = 0; i < annualLeaveCells.size(); i++) {
            WebElement column = annualLeaveCells.get(i);
            actions.moveToElement(column).perform();

            // Check if the "name" element exists within the column
            List<WebElement> shiftNames = column.findElements(By.className("name"));
            if (!shiftNames.isEmpty()) {
                System.out.println("Shift Name: " + shiftNames.get(0).getText());
            } else {
                System.out.println("No shift name found in this cell.");
            }

            if (i + 1 < annualLeaveCells.size()) {
                WebElement nextColumn = annualLeaveCells.get(i + 1);
                actions.clickAndHold(column).moveToElement(nextColumn).release().perform();
            }
        }
    }






//    public void matchDayOfWeek() {
//        WebElement scheduleBoard = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
//        scrollToElement(scheduleBoard);
//        scheduleBoard.click();
//
//        List<WebElement> scheduleCells = waitForElementsToBeVisible(By.cssSelector(".VirtualScheduleCell"));
//
//        Actions actions = new Actions(driver);
//
//        for (int i = 0; i < scheduleCells.size(); i++) {
//            WebElement currentCell = scheduleCells.get(i);
//            actions.moveToElement(currentCell).perform();
//            WebElement shiftNameElement = currentCell.findElement(By.className("name"));
//            System.out.println("Shift Name: " + shiftNameElement.getText());
//
//            String leftPosition = currentCell.getCssValue("left");
//
//            WebElement correspondingHeader = findHeaderByPosition(leftPosition);
//            if (correspondingHeader != null) {
//                String dayOfWeek = correspondingHeader.getText();
//                System.out.println("Day of the Week: " + dayOfWeek);
//            }
//
//            if (i + 1 < scheduleCells.size()) {
//                WebElement nextCell = scheduleCells.get(i + 1);
//                actions.clickAndHold(currentCell).moveToElement(nextCell).release().perform();
//            }
//        }
//    }
//
//    private WebElement findHeaderByPosition(String leftPosition) {
//        List<WebElement> headers = waitForElementsToBeVisible(By.cssSelector(".defaultHeaderCell.headerCellFocus"));
//
//        for (WebElement header : headers) {
//            String headerLeftPosition = header.getCssValue("left");
//            if (headerLeftPosition.equals(leftPosition)) {
//                return header;
//            }
//        }
//
//        return null;
//    }
//
//
//
//




















































//    public void moveThroughScheduledColumnCell() {
//        WebElement scheduleBoard = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
//        clickUsingJavaScript(scheduleBoard);
//
//        waitForElementToBeVisible(By.cssSelector(".VirtualScheduleCell"));
//        List<WebElement> scheduledCells = driver.findElements(By.cssSelector(".VirtualScheduleCell"));
//
//        waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//        List<WebElement> employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//        Set<WebElement> processedEmployees = new HashSet<>();
//
//        for (int i = 0; i < scheduledCells.size(); i++) {
//            WebElement column = scheduledCells.get(i);
//            actions.moveToElement(column).perform();
//
//            WebElement shiftName = column.findElement(By.className("name"));
//
//            String topPosition = column.getCssValue("top");
//            String leftPosition = column.getCssValue("left");
//
//            waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//
//            employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//            WebElement employeeCell = findEmployeeByTopAndLeftPosition(topPosition, leftPosition, employeeCells, processedEmployees);
//
//            if (employeeCell != null && !processedEmployees.contains(employeeCell)) {
//                String employeeName = employeeCell.findElement(By.className("employeeLabel")).getText();
//                System.out.println("Employee: " + employeeName + " | Shift: " + shiftName.getText());
//
//                processedEmployees.add(employeeCell);
//            }
//
//            if (i + 1 < scheduledCells.size()) {
//                WebElement nextColumn = scheduledCells.get(i + 1);
//                actions.clickAndHold(column).moveToElement(nextColumn).release().perform();
//            }
//        }
//    }
//
//
//    private WebElement findEmployeeByTopAndLeftPosition(String topPosition, String leftPosition, List<WebElement> employeeCells, Set<WebElement> processedEmployees) {
//        double targetTop = Double.parseDouble(topPosition.replace("px", ""));
//        double targetLeft = Double.parseDouble(leftPosition.replace("px", ""));
//
//        for (WebElement employee : employeeCells) {
//            if (processedEmployees.contains(employee)) {
//                continue;
//            }
//
//            String employeeTop = employee.getCssValue("top").replace("px", "");
//            String employeeLeft = employee.getCssValue("left").replace("px", "");
//            double employeeTopValue = Double.parseDouble(employeeTop);
//            double employeeLeftValue = Double.parseDouble(employeeLeft);
//
//            if (targetTop == employeeTopValue && targetLeft == employeeLeftValue) {
//                return employee;
//            }
//        }
//
//        return null;
//    }

































//        public void moveThroughScheduledColumnCell() {
//        WebElement scheduleBoard = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
//        clickUsingJavaScript(scheduleBoard);
//
//        waitForElementToBeVisible(By.cssSelector(".VirtualScheduleCell"));
//        List<WebElement> scheduledCells = driver.findElements(By.cssSelector(".VirtualScheduleCell"));
//
//        waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//        List<WebElement> employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//        waitForElementToBeVisible(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));
//        List<WebElement> annualLeaveCells = driver.findElements(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));
//
//
//        Set<WebElement> processedEmployees = new HashSet<>();
//
//        for (int i = 0; i < scheduledCells.size(); i++) {
//            WebElement column = scheduledCells.get(i);
//            actions.moveToElement(column).perform();
//
//            WebElement shiftName = column.findElement(By.className("name"));
//
//            String topPosition = column.getCssValue("top");
//            String leftPosition = column.getCssValue("left");
//
//            waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//
//            employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//            WebElement employeeCell = findEmployeeByTopAndLeftPosition(topPosition, leftPosition, employeeCells, processedEmployees);
//
//            if (employeeCell != null && !processedEmployees.contains(employeeCell)) {
//                String employeeName = employeeCell.findElement(By.className("employeeLabel")).getText();
//                System.out.println("Employee: " + employeeName + " | Shift: " + shiftName.getText());
//
//                processedEmployees.add(employeeCell);
//            }
//
//            if (i + 1 < scheduledCells.size()) {
//                WebElement nextColumn = scheduledCells.get(i + 1);
//                actions.clickAndHold(column).moveToElement(nextColumn).release().perform();
//            }
//        }
//    }
//
//
//    private WebElement findEmployeeByTopAndLeftPosition(String topPosition, String leftPosition, List<WebElement> employeeCells, Set<WebElement> processedEmployees) {
//        double targetTop = Double.parseDouble(topPosition.replace("px", ""));
//        double targetLeft = Double.parseDouble(leftPosition.replace("px", ""));
//
//        for (WebElement employee : employeeCells) {
//            if (processedEmployees.contains(employee)) {
//                continue;
//            }
//
//            String employeeTop = employee.getCssValue("top").replace("px", "");
//            String employeeLeft = employee.getCssValue("left").replace("px", "");
//            double employeeTopValue = Double.parseDouble(employeeTop);
//            double employeeLeftValue = Double.parseDouble(employeeLeft);
//
//            if (targetTop == employeeTopValue && targetLeft == employeeLeftValue) {
//                return employee;
//            }
//        }
//
//        return null;
//    }






//
//    public void moveThroughScheduledColumnCell() {
//        WebElement scheduleBoard = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
//        clickUsingJavaScript(scheduleBoard);
//
//        waitForElementToBeVisible(By.cssSelector(".VirtualScheduleCell"));
//        List<WebElement> scheduledCells = driver.findElements(By.cssSelector(".VirtualScheduleCell"));
//
//        waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//        List<WebElement> employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//       // waitForElementToBeVisible(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));
//        waitForElementToBeVisible(By.cssSelector("div.VirtualBackgroundCell.SchedulerBackgroundCell.holiday"));
//        List<WebElement> annualLeaveCells = driver.findElements(By.cssSelector("div.VirtualBackgroundCell.SchedulerBackgroundCell.holiday"));
//        //List<WebElement> annualLeaveCells = driver.findElements(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));
//
//        Set<WebElement> processedEmployees = new HashSet<>();
//
//        for (int i = 0; i < scheduledCells.size(); i++) {
//            WebElement column = scheduledCells.get(i);
//
//            // Skip the column if it's part of annual leave cells
//            if (annualLeaveCells.contains(column)) {
//                continue; // Skip the annual leave cells
//            }
//
//            actions.moveToElement(column).perform();
//
//            WebElement shiftName = column.findElement(By.className("name"));
//
//            String topPosition = column.getCssValue("top");
//            String leftPosition = column.getCssValue("left");
//
//            waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//
//            employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//            WebElement employeeCell = findEmployeeByTopAndLeftPosition(topPosition, leftPosition, employeeCells, processedEmployees);
//
//            if (employeeCell != null && !processedEmployees.contains(employeeCell)) {
//                String employeeName = employeeCell.findElement(By.className("employeeLabel")).getText();
//                System.out.println("Employee: " + employeeName + " | Shift: " + shiftName.getText());
//
//                processedEmployees.add(employeeCell);
//            }
//
//            if (i + 1 < scheduledCells.size()) {
//                WebElement nextColumn = scheduledCells.get(i + 1);
//                actions.clickAndHold(column).moveToElement(nextColumn).release().perform();
//            }
//        }
//    }
//







//    public void moveThroughScheduledColumnCell() {
//        WebElement scheduleBoard = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
//        clickUsingJavaScript(scheduleBoard);
//
//        waitForElementToBeVisible(By.cssSelector(SCHEDULED_CELL_LOCATOR));
//        List<WebElement> scheduledCells = driver.findElements(By.cssSelector(SCHEDULED_CELL_LOCATOR));
//
//        Set<WebElement> processedEmployees = new HashSet<>();
//
//        for (int i = 0; i < scheduledCells.size(); i++) {
//            WebElement column = scheduledCells.get(i);
//
//            List<WebElement> annualLeaveCells = driver.findElements(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));
//            if (annualLeaveCells.contains(column)) {
//                continue;
//            }
//
//            actions.moveToElement(column).perform();
//            WebElement shiftName = column.findElement(By.className("name"));
//
//            String topPosition = column.getCssValue("top");
//            String leftPosition = column.getCssValue("left");
//
//            waitForElementToBeVisible(By.cssSelector(".VirtualEmployeeCell"));
//            List<WebElement> employeeCells = driver.findElements(By.cssSelector(".VirtualEmployeeCell"));
//
//            WebElement employeeCell = findEmployeeByTopAndLeftPosition(topPosition, leftPosition, employeeCells, processedEmployees);
//
//            if (employeeCell != null && !processedEmployees.contains(employeeCell)) {
//                String employeeName = employeeCell.findElement(By.className("employeeLabel")).getText();
//                System.out.println("Employee: " + employeeName + " | Shift: " + shiftName.getText());
//
//                processedEmployees.add(employeeCell);
//            }
//
//            if (i + 1 < scheduledCells.size()) {
//                WebElement nextColumn = scheduledCells.get(i + 1);
//                actions.clickAndHold(column).moveToElement(nextColumn).release().perform();
//            }
//        }
//    }
//
//    private WebElement findEmployeeByTopAndLeftPosition(String topPosition, String leftPosition, List<WebElement> employeeCells, Set<WebElement> processedEmployees) {
//        double targetTop = Double.parseDouble(topPosition.replace("px", ""));
//        double targetLeft = Double.parseDouble(leftPosition.replace("px", ""));
//
//        for (WebElement employee : employeeCells) {
//            if (processedEmployees.contains(employee)) {
//                continue;
//            }
//
//            String employeeTop = employee.getCssValue("top").replace("px", "");
//            String employeeLeft = employee.getCssValue("left").replace("px", "");
//            double employeeTopValue = Double.parseDouble(employeeTop);
//            double employeeLeftValue = Double.parseDouble(employeeLeft);
//
//            if (targetTop == employeeTopValue && targetLeft == employeeLeftValue) {
//                return employee;
//            }
//        }
//
//        return null;
//    }




















    public void matchDayOfWeekAndMoveThroughScheduledColumnCell() {
        WebElement scheduleBoard = waitForElementToBeVisible(By.cssSelector(SHIFT_BOARD_CSS));
        clickUsingJavaScript(scheduleBoard);

        waitForElementToBeVisible(By.cssSelector(SCHEDULED_CELL_LOCATOR));
        List<WebElement> scheduledCells = driver.findElements(By.cssSelector(SCHEDULED_CELL_LOCATOR));

        waitForElementToBeVisible(By.cssSelector(EMPLOYEE_LIST_CSS));
        List<WebElement> employeeCells = driver.findElements(By.cssSelector(EMPLOYEE_LIST_CSS));

        // List<WebElement> annualLeaveCells = waitForElementsToBeVisible(By.cssSelector(CELL_WITH_ANNUAL_LEAVE_CSS));

        Set<WebElement> processedEmployees = new HashSet<>();

        for (int i = 0; i < scheduledCells.size(); i++) {
            WebElement currentCell = scheduledCells.get(i);
            actions.moveToElement(currentCell).perform();

            WebElement shiftNameElement = currentCell.findElement(By.className(SHIFT_NAME_SELECTOR));
            logger.info("Shift Name: " + shiftNameElement.getText());

            String leftPosition = currentCell.getCssValue("left");

            WebElement correspondingHeader = findHeaderByPosition(leftPosition);
            if (correspondingHeader != null) {
                String dayOfWeek = correspondingHeader.getText();
                logger.info("Day of the Week: " + dayOfWeek);
            }

            String topPosition = currentCell.getCssValue("top");

            WebElement employeeCell = findEmployeeByTopAndLeftPosition(topPosition, leftPosition, employeeCells, processedEmployees);
            if (employeeCell != null && !processedEmployees.contains(employeeCell)) {
                String employeeName = employeeCell.findElement(By.className(EMPLOYEE_NAME_SELECTOR)).getText();
                logger.info("Employee: " + employeeName + " | Shift: " + shiftNameElement.getText());

                processedEmployees.add(employeeCell);

                break;
            }

            if (i + 1 < scheduledCells.size()) {
                WebElement nextCell = scheduledCells.get(i + 1);
                actions.clickAndHold(currentCell).moveToElement(nextCell).release().perform();
            }
        }
    }

    private WebElement findHeaderByPosition(String leftPosition) {
        List<WebElement> headers = waitForElementsToBeVisible(By.cssSelector(DAY_OF_WEEK_HEADER));
        for (WebElement header : headers) {
            String headerLeftPosition = header.getCssValue("left");
            if (headerLeftPosition.equals(leftPosition)) {
                return header;
            }
        }
        return null;
    }


    private WebElement findEmployeeByTopAndLeftPosition(String topPosition, String leftPosition, List<WebElement> employeeCells, Set<WebElement> processedEmployees) {
        double targetTop = Double.parseDouble(topPosition.replace("px", ""));
        double targetLeft = Double.parseDouble(leftPosition.replace("px", ""));

        for (WebElement employee : employeeCells) {
            if (processedEmployees.contains(employee)) {
                continue;
            }

            String employeeTop = employee.getCssValue("top").replace("px", "");
            String employeeLeft = employee.getCssValue("left").replace("px", "");
            double employeeTopValue = Double.parseDouble(employeeTop);
            double employeeLeftValue = Double.parseDouble(employeeLeft);


            if (targetTop == employeeTopValue && targetLeft == employeeLeftValue) {
                return employee;
            }
        }

        return null;
    }




}