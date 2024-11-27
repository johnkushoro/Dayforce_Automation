
package com.totallygroup.pageObject;

import com.totallygroup.utils.DataStore;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import java.util.List;
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
    //    public static final String HIGHLIGHTED_ORGANISATION_XPATH = "//div[@class='dijitReset dijitMenuItem']";
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
        waitForElementToBeVisibleAndClickable(By.cssSelector(ORG_PICKER_CSS)).click();
    }

    public void clickDownArrowButton() {
        waitForElementToBeVisibleAndClickable(By.xpath(DOWN_ARROW_BUTTON_XPATH)).click();
    }

    public void selectRandomOrganisationLocation() throws InterruptedException {
        List<WebElement> organisationElements = waitForElementsToBeVisible(By.cssSelector(LIST_OF_ORGANISATIONS_SELECTOR));

        if (organisationElements.isEmpty()) {
            logger.log(Level.SEVERE, "No organisation location elements found for selector: " + LIST_OF_ORGANISATIONS_SELECTOR);
            throw new NoSuchElementException("No organisation location elements found.");
        }

        Thread.sleep(500);
        WebElement randomOrganisation = organisationElements.get(ThreadLocalRandom.current().nextInt(organisationElements.size()));
        wait.until(ExpectedConditions.elementToBeClickable(randomOrganisation));
        randomOrganisation.click();
        Thread.sleep(500);


        Thread.sleep(500);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String organisationText = (String) js.executeScript("return arguments[0].innerText;", randomOrganisation);


        if (organisationText == null || organisationText.isEmpty()) {
            organisationText = randomOrganisation.getAttribute("title");
        }

        if (organisationText == null || organisationText.isEmpty()) {
            logger.log(Level.SEVERE, "Organisation text is null or empty after selection.");
            throw new NoSuchElementException("No valid organisation text found.");
        }

        logger.info("Selected random organisation: " + organisationText);
        dataStore.setValue("selectedOrganisation", organisationText);
    }

    public void searchAndSelectAndClickOrganisation(String organisationText) throws InterruptedException {
        WebElement searchField = waitForElementToBeVisible(By.cssSelector(ORGANISATION_SEARCH_FIELD_CSS));
        var pre = driver.getPageSource();
        searchField.sendKeys(organisationText);

        WebElement highlightedOrganisation = waitForElementToBeVisible(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
        if (highlightedOrganisation == null) {
            logger.log(Level.WARNING, "No highlighted organisation found for: " + organisationText);
            throw new NoSuchElementException("Organisation not found in search results.");
        }

        Thread.sleep(10000);
        highlightedOrganisation = waitForElementToBeClickable(By.cssSelector(HIGHLIGHTED_ORGANISATION_CSS));
        highlightedOrganisation.click();
        Thread.sleep(10000);
        var post = driver.getPageSource();

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

}
