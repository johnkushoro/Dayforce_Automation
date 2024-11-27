

package com.totallygroup.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class LoginPage extends CommonPage {

    public static final String COMPANY_INPUT_FIELD_XPATH = "//*[@id='txtCompanyName']";
    public static final String USERNAME_INPUT_FIELD_XPATH = "//*[@id='txtUserName']";
    public static final String PASSWORD_INPUT_FIELD_XPATH = "//*[@id='txtUserPass']";
    public static final String LOGIN_BUTTON_XPATH = "//input[@id='MainContent_loginUI_cmdLogin']";
    public static final String COOKIES_BUTTON_TEXT_XPATH = "//button[text()='%s']";
    public static final String SKIP_BUTTON_XPATH = "//span[@widgetid='Button_2']";
    public static final String ROLE_RADIO_BUTTON_XPATH = "//label[text()='%s']/preceding-sibling::div[@class='radioContainer']//input[@type='radio']";
    public static final String NEXT_BUTTON_TEXT_XPATH = "//span[@id='evrDialog-button_label' and text()='%s']";
    public static final String DAYFORCE_NEWS_OK_BUTTON_XPATH = "//button[@aria-label='OK']";
    public static final String TOTALLY_LOGO_XPATH = "//span[@class='LogoToolbar']//img[@class='Logo']";
    public static final String DAYFORCE_LOGIN_TITLE = "#dayforce_Dialog_1_title.dijitDialogTitle.dialogTitle";


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterLoginCredentials(String companyName, String username, String password) {
        waitForElementToBeVisible(By.xpath(COMPANY_INPUT_FIELD_XPATH)).sendKeys(companyName);
        waitForElementToBeVisible(By.xpath(USERNAME_INPUT_FIELD_XPATH)).sendKeys(username);
        waitForElementToBeVisible(By.xpath(PASSWORD_INPUT_FIELD_XPATH)).sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
        scrollToElement(loginButton);
        waitForElementToBeClickable(By.xpath(LOGIN_BUTTON_XPATH));
        loginButton.click();
    }

    public void clickCookieButton(String buttonText) {
        waitForElementToBeClickable(By.xpath(String.format(COOKIES_BUTTON_TEXT_XPATH, buttonText))).click();
    }

    public void clickSkipButton() {
        waitForElementToBeClickable(By.xpath(SKIP_BUTTON_XPATH)).click();
    }

    public void selectRoleRadioButtonByText(String radioButtonText) {
        waitForElementToBeClickable(By.xpath(String.format(ROLE_RADIO_BUTTON_XPATH, radioButtonText))).click();
    }

    public void clickRolePageNextButton(String buttonText) {
        waitForElementToBeClickable(By.xpath(String.format(NEXT_BUTTON_TEXT_XPATH, buttonText))).click();
    }


    public void clickDayforceNewsOkButton() {
        try {
            WebElement dayforceNewsOkButton = waitForElementToBeClickable(By.xpath(DAYFORCE_NEWS_OK_BUTTON_XPATH));
            if (dayforceNewsOkButton.isDisplayed()) {
                dayforceNewsOkButton.click();
            } else {
                System.out.println("Dayforce News OK button is not visible on the page.");
            }
        } catch (Exception e) {
            System.out.println("Error clicking Dayforce News OK button: " + e.getMessage());
        }
    }

    public boolean isTotallyLogoDisplayed() {
        return waitForElementToBeVisible(By.xpath(TOTALLY_LOGO_XPATH)).isDisplayed();
    }

}
