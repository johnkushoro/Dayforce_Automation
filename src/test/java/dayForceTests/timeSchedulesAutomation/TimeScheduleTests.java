//
//package dayForceTests.timeSchedulesAutomation;
//
//import com.totallygroup.pageObject.RandomDateSelector;
//import com.totallygroup.pageObject.SideMenuItems;
//import com.totallygroup.pageObject.TimeSchedulesPage;
//import com.totallygroup.utils.DataStore;
//import dayForceTests.BaseTest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.testng.annotations.Test;
//import static org.testng.Assert.assertEquals;
//
//public class TimeScheduleTests extends BaseTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(TimeScheduleTests.class);
//
//    @Autowired
//    private TimeSchedulesPage timeSchedulesPage;
//
//    @Autowired
//    private SideMenuItems sideMenuItems;
//
//    @Autowired
//    private DataStore dataStore;
//
//    @Autowired
//    private RandomDateSelector randomDateSelector;
//
//    private static final String REGION_OK_BUTTON_TEXT = "OK";
//    private static final String SCHEDULES_MENU_ITEM = "Schedules";
//
//    @Test
//    public void testLoadRegion() throws InterruptedException {
//        logger.info("Starting testLoadRegion...");
//
//        sideMenuItems.selectMenuItem(SCHEDULES_MENU_ITEM, null, null);
//        timeSchedulesPage.clickOrgPicker();
//        timeSchedulesPage.clickDownArrowButton();
//        timeSchedulesPage.selectRandomRegionLocation();
//        timeSchedulesPage.clickRegionOkButton(REGION_OK_BUTTON_TEXT);
//        timeSchedulesPage.clickTooltipLoadButton();
//
//        logger.info("testLoadRegion completed.");
//    }
//
//    @Test
//    public void verifyRegionTitleMatchesSelectedRegion() {
//        logger.info("Starting verifyRegionTitleMatchesSelectedRegion...");
//
//        String expectedRegion = (String) dataStore.getValue("selectedRegion");
//        String actualRegion = timeSchedulesPage.getLoadedRegionTitle().trim();
//
//        assertEquals(actualRegion, expectedRegion.trim(), "Loaded region title does not match the selected region.");
//
//        logger.info("verifyRegionTitleMatchesSelectedRegion completed successfully.");
//    }
//
//}




package dayForceTests.timeSchedulesAutomation;

import com.totallygroup.pageObject.SideMenuItems;
import com.totallygroup.pageObject.TimeSchedulesPage;
import com.totallygroup.utils.DataStore;
import dayForceTests.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TimeScheduleTests extends BaseTest {

    @Autowired
    private TimeSchedulesPage timeSchedulePage;

    @Autowired
    private SideMenuItems sideMenuItems;

    @Autowired
    private DataStore dataStore;

    @Test
    public void testLoadOrganisation() throws InterruptedException {
        sideMenuItems.selectMenuItem("Schedules", null, null);
        timeSchedulePage.clickOrgPicker();
        timeSchedulePage.clickDownArrowButton();
//        timeSchedulePage.selectRandomOrganisationLocation();
//        timeSchedulePage.clickOrganisationOkButton("OK");
//        timeSchedulePage.clickTooltipLoadButton();

        timeSchedulePage.searchAndSelectAndClickOrganisation("Arun House - YAS 999");
        timeSchedulePage.clickOrganisationOkButton("OK");
        timeSchedulePage.clickTooltipLoadButton();
        assertEquals(timeSchedulePage.getLoadedPageTitle(), "Arun House - YAS 999", "Shift assignment failed.");
    }

//    @Test
//    public void verifyOrganisationTitleMatchesSelectedOrganisation() {
//        assertEquals(timeSchedulePage.getLoadedOrganisationTitle().trim(), ((String) dataStore.getValue("selectedOrganisation")).trim(),
//                "Loaded organisation title does not match the selected organisation.");
//    }

//        @Test
//        public void testAssignShiftToEmployees() {
//            logger.info("Starting testAssignShiftToEmployees...");
//
//
//            logger.info("testAssignShiftToEmployees completed.");
//        }
//

}
