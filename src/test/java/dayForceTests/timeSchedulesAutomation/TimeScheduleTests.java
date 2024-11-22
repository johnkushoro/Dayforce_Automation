

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
    public void testLoadRegion() throws InterruptedException {
        sideMenuItems.selectMenuItem("Schedules", null, null);
        timeSchedulePage.clickOrgPicker();
        timeSchedulePage.clickDownArrowButton();
        timeSchedulePage.selectRandomRegionLocation();
        timeSchedulePage.clickRegionOkButton("OK");
        timeSchedulePage.clickTooltipLoadButton();

    }

    @Test
    public void verifyRegionTitleMatchesSelectedRegion() {
        assertEquals(timeSchedulePage.getLoadedRegionTitle().trim(), ((String) dataStore.getValue("selectedRegion")).trim(),
                "Loaded region title does not match the selected region.");
    }

}