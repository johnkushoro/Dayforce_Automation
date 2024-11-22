
package dayForceTests;

import com.totallygroup.utils.Config;
import com.totallygroup.utils.TestConfig;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = {TestConfig.class})  // Load Spring configuration
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected WebDriver driver;

    private static boolean isHomePageLoaded = false;

    @BeforeMethod
    public void setUpOnceForSuite() {
        if (!isHomePageLoaded) {  // Only navigate once per suite
            Config.loadConfig(null);
            String url = Config.getPropertyWithException("url");
            driver.get(url);
            isHomePageLoaded = true;  // Set flag to avoid re-navigation
        }
    }

    @AfterSuite
    public void tearDownAfterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}