
package dayForceTests;

import com.totallygroup.pageObject.LoginPage;
import com.totallygroup.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPageTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginPageTests.class);

    @Autowired
    private LoginPage loginPage;

    @DataProvider(name = "loginDataProvider")
    public Object[][] loginData() {
        return new Object[][] {
                {
                        Config.getPropertyWithException("companyName"),
                        Config.getPropertyWithException("userName"),
                        Config.getPropertyWithException("passWord")
                }
        };
    }

    @Test(dataProvider = "loginDataProvider")
    public void testAccountLogin(String companyName, String username, String password) {
        logger.info("Starting login test with companyName: {}, username: {}", companyName, username);

        loginPage.enterLoginCredentials(companyName, username, password);
        loginPage.clickLoginButton();
    }

    @Test
    public void testNavigateToHomePage() {
        logger.info("Starting navigation to home page");

        loginPage.clickCookieButton("Accept All Cookies");
        loginPage.clickSkipButton();
        loginPage.selectRoleRadioButtonByText("Client Administrator");
        loginPage.clickRolePageNextButton("Next");
      //  loginPage.clickDayforceNewsOkButton();

        boolean isLogoDisplayed = loginPage.isTotallyLogoDisplayed();
        Assert.assertTrue(isLogoDisplayed, "The Totally logo was not displayed on the home page.");

        logger.info("Navigation to home page completed successfully.");
    }
}