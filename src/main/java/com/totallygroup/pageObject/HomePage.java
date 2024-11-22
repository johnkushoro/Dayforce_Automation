
package com.totallygroup.pageObject;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class HomePage extends CommonPage {


    public static final String HAMBURGER_ICON_CSS = "span.dijitIcon > svg.icon-hamburger.icon-everest-menu-toggle";

    public HomePage(WebDriver driver)
    {
        super(driver);
    }


    public void clickHamburgerIcon() {

        waitForElementToBeClickable(By.cssSelector(HAMBURGER_ICON_CSS)).click();

    }

}
