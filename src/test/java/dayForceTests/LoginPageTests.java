
package dayForceTests;

import com.totallygroup.pageObject.LoginPage;
import com.totallygroup.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageTests extends BaseTest {

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
    public void accountLoginPageTest(String companyName, String username, String password) {

        loginPage.enterLoginCredentials(companyName, username, password);
    }

    @Test
    public void navigateToAuthenticationPageTest() {
        loginPage.clickLoginButton();
        loginPage.clickCookieButton("Accept All Cookies");
        loginPage.clickSkipButton();
        loginPage.selectRoleRadioButtonByText("Client Administrator No MFA");
        loginPage.clickRolePageNextButton("Next");
        loginPage.clickDayforceNewsOkButton();
        Assert.assertTrue(loginPage.isTotallyLogoDisplayed());

    }
}
