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
////        assertEquals(timeSchedulePage.getLoadedPageTitle().trim(), ((String) dataStore.getValue("selectedOrganisation")).trim(),
////                "Loaded organisation title does not match the selected organisation.");
//
//}


    @Test
    public void verifyDayOfWeekMatchesTitle() {
        timeSchedulePage.testSelectAvailableDayForEmployee();
        timeSchedulePage.verifyDayOfWeekMatchesTitle();
        timeSchedulePage.verifySearchBarTextMatchesSelectedEmployee();
        timeSchedulePage.clickButtonByText("OK");

    }


}
