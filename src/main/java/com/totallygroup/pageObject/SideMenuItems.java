
package com.totallygroup.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class SideMenuItems extends CommonPage {

    public static final String SIDE_MENU_ITEM_XPATH = "//span[@class='evrBodyText EverestDisplayName' and normalize-space(text())='%s']";

    public SideMenuItems(WebDriver driver) {
        super(driver);
    }

    public void selectMenuItem(String itemText, String subItemText, String thirdSubItemText) {
        String itemXpath = String.format(SIDE_MENU_ITEM_XPATH, itemText);
        WebElement menuItem = waitForElementToBeVisible(By.xpath(itemXpath));
        menuItem.click();

        if (subItemText != null && !subItemText.isEmpty()) {
            String subItemXpath = String.format(SIDE_MENU_ITEM_XPATH, subItemText);
            WebElement subMenuItem = waitForElementToBeVisible(By.xpath(subItemXpath));
            subMenuItem.click();
        }

        if (thirdSubItemText != null && !thirdSubItemText.isEmpty()) {
            String thirdSubItemXpath = String.format(SIDE_MENU_ITEM_XPATH, thirdSubItemText);
            WebElement thirdSubMenuItem = waitForElementToBeVisible(By.xpath(thirdSubItemXpath));
            thirdSubMenuItem.click();
        }
    }
}
