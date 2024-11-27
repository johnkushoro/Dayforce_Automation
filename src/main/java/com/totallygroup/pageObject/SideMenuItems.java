
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
        selectItem(itemText);

        if (subItemText != null && !subItemText.isEmpty()) {
            selectItem(subItemText);
        }

        if (thirdSubItemText != null && !thirdSubItemText.isEmpty()) {
            selectItem(thirdSubItemText);
        }
    }

    private void selectItem(String itemText) {
        String itemXpath = String.format(SIDE_MENU_ITEM_XPATH, itemText);
        WebElement menuItem = waitForElementToBeVisible(By.xpath(itemXpath));
        menuItem.click();
    }
}
