
package com.totallygroup.pageObject;

import com.totallygroup.utils.DataStore;
import org.openqa.selenium.*;
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
    public static final String LIST_OF_REGIONS_SELECTOR = "div.dijitReset.dijitMenuItem";
    public static final String REGION_OK_BUTTON_XPATH = "//*[@id='dijit_TooltipDialog_4']//span[contains(text(), '%s')]";
    public static final String TOOLTIP_LOAD_BUTTON_XPATH = "//*[@id='Button_118']";
    public static final String REGION_SEARCH_FIELD_CSS = "#UI_ComboBox_2";
    public static final String LOADED_REGION_TITLE = "//div[@class='dijitOutput left-decoration DecorationText']";


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


    public void selectRandomRegionLocation() throws InterruptedException {
        List<WebElement> regionElements = waitForElementsToBeVisible(By.cssSelector(LIST_OF_REGIONS_SELECTOR));

        if (regionElements.isEmpty()) {
            logger.log(Level.SEVERE, "No region location elements found for selector: " + LIST_OF_REGIONS_SELECTOR);
            throw new NoSuchElementException("No region location elements found.");
        }

        Thread.sleep(500);

        WebElement randomRegion = regionElements.get(ThreadLocalRandom.current().nextInt(regionElements.size()));
        randomRegion.click();
        Thread.sleep(500);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String regionText = (String) js.executeScript("return arguments[0].innerText;", randomRegion);
        Thread.sleep(500);

        if (regionText == null || regionText.isEmpty()) {
            regionText = randomRegion.getAttribute("title");
        }

        logger.info("Selected random region: " + regionText);
        dataStore.setValue("selectedRegion", regionText);
    }


    public void clickRegionOkButton(String buttonText) {
        waitForElementToBeVisibleAndClickable(By.xpath(String.format(REGION_OK_BUTTON_XPATH, buttonText))).click();
    }

    public void clickTooltipLoadButton() {
        WebElement button = waitForElementToBeVisibleAndClickable(By.xpath(TOOLTIP_LOAD_BUTTON_XPATH));
        button.click();
    }


    public String getLoadedRegionTitle() {
        String regionTitleText = waitForElementToBeVisible(By.xpath(LOADED_REGION_TITLE)).getText();
        if (regionTitleText.isEmpty()) throw new NoSuchElementException("Loaded region title is empty");
        logger.info("Loaded region title: " + regionTitleText);
        return regionTitleText;
    }


}