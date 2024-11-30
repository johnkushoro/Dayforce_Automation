
package Runners;

import com.totallygroup.utils.Config;
import com.totallygroup.utils.EmailSender;
import com.totallygroup.utils.ExtentReportManager;
import com.totallygroup.utils.TestListener;
import dayForceTests.HomePageTests;
import dayForceTests.LoginPageTests;
import dayForceTests.timeSchedulesAutomation.TimeScheduleTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);
    private TestNG testng;

    @BeforeClass
    public void setup() {
        // Load the config properties
        Config.loadConfig("src/test/resources/config/config.properties");

        testng = new TestNG();
        testng.addListener(new TestListener());
        testng.addListener(new ExtentReportManager());
        logger.info("Setting up TestRunner environment.");
    }

    @Test
    public void runTests() {
        testng.setTestClasses(new Class[] {
                LoginPageTests.class,
                HomePageTests.class,
                TimeScheduleTests.class,
        });

        logger.info("Running tests...");
        testng.run();
    }

    @AfterClass
    public void tearDown() {
        logger.info("Cleaning up TestRunner environment.");

        String reportFilePath = "test-output/extent-report.html";

        String organisationEmail = Config.getProperty("Organisation_Email");
        String subject = Config.getProperty("Subject");
        String mailBody = Config.getProperty("MailBody");

        EmailSender.sendEmail(
                organisationEmail, subject, mailBody, reportFilePath
        );
    }
}