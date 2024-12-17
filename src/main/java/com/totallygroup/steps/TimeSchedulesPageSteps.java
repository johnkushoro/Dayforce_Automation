
package com.totallygroup.steps;

import com.totallygroup.pageObject.TimeSchedulesPage;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.logging.Level;

import static com.totallygroup.utils.Config.logger;

@Component
public class TimeSchedulesPageSteps {

    private final TimeSchedulesPage timeSchedulesPage;

    @Autowired
    public TimeSchedulesPageSteps(TimeSchedulesPage timeSchedulesPage) {
        this.timeSchedulesPage = timeSchedulesPage;
    }


    public void searchAndSelectOrganisation(String organisationText) {
        WebElement searchField = timeSchedulesPage.getOrganisationSearchField();
        searchField.sendKeys(organisationText);

        WebElement highlightedOrganisation = timeSchedulesPage.getHighlightedOrganisation();
        if (highlightedOrganisation == null) {
            logger.log(Level.WARNING, "No highlighted organisation found for: " + organisationText);
            throw new NoSuchElementException("Organisation not found in search results.");
        }

        timeSchedulesPage.clickOrganisation(highlightedOrganisation);
        waitForOrganisationSelection(organisationText);
    }

    private void waitForOrganisationSelection(String expectedOrganisationText) {
        WebElement selectedOrganisation = timeSchedulesPage.getSelectedOrganisation();
        String selectedOrganisationText = selectedOrganisation.getText();

        if (!selectedOrganisationText.equals(expectedOrganisationText)) {
            throw new AssertionError("Selected organisation text does not match entered text. Expected: "
                    + expectedOrganisationText + ", Found: " + selectedOrganisationText);
        }

        logger.info("Verified selected organisation matches the entered text: " + expectedOrganisationText);
    }
}
