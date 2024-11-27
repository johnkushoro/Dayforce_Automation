//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }
//
//    public void selectRandomFutureDate() {
//        // Wait for the calendar to be visible
//        WebElement calendarPopup = waitForElementToBeVisible(By.id("DateBox_5_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the future (we assume the aria-label attribute contains the full date)
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//            // Add logic to check if the date is in the future
//            if (isFutureDate(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isFutureDate(String ariaLabel) {
//        // Example aria-label: "November 23 2024"
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//        LocalDate currentDate = LocalDate.now();
//        LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//        // Check if the date is in the future
//        return dateFromLabel.isAfter(currentDate);
//    }
//}




//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }
//
//    public void selectRandomFutureDate() {
//        // Wait for the calendar to be visible
//        WebElement calendarPopup = waitForElementToBeVisible(By.id("DateBox_5_popup"));
//
//        // Check if the calendar is visible, if not retry or throw an error
//        if (calendarPopup != null) {
//            System.out.println("Calendar popup is visible");
//
//            // Find all enabled date cells within the calendar
//            List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//            // If no enabled dates are found, print and exit
//            if (enabledDates.isEmpty()) {
//                System.out.println("No enabled dates found in the calendar.");
//                return;
//            }
//
//            // Filter out the dates that are in the future (we assume the aria-label attribute contains the full date)
//            List<WebElement> futureDates = new ArrayList<>();
//            for (WebElement date : enabledDates) {
//                String ariaLabel = date.getAttribute("aria-label");
//                // Add logic to check if the date is in the future
//                if (isFutureDate(ariaLabel)) {
//                    futureDates.add(date);
//                }
//            }
//
//            // Select a random future date
//            if (!futureDates.isEmpty()) {
//                Random rand = new Random();
//                int randomIndex = rand.nextInt(futureDates.size());
//                WebElement randomDate = futureDates.get(randomIndex);
//
//                // Scroll to the date and click it
//                scrollToElement(randomDate);
//
//                // Ensure the selected date is clickable before clicking
//                WebElement clickableDate = waitForElementToBeClickable(By.xpath("//span[text()='" + randomDate.getText() + "']"));
//                if (clickableDate != null) {
//                    clickableDate.click();
//                    System.out.println("Random future date selected: " + randomDate.getText());
//                } else {
//                    System.out.println("Date is not clickable: " + randomDate.getText());
//                }
//            } else {
//                System.out.println("No future dates available to select.");
//            }
//        } else {
//            System.out.println("Calendar popup is not visible.");
//        }
//    }
//
//    private boolean isFutureDate(String ariaLabel) {
//        // Example aria-label: "November 23 2024"
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//        LocalDate currentDate = LocalDate.now();
//        LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//        // Check if the date is in the future
//        return dateFromLabel.isAfter(currentDate);
//    }
//}

//
//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }

//    public void selectRandomFutureDate() {
//        // First, click on the input button to open the date picker using CSS selector
//        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//        datePickerButton.click();
//
//        // Wait for the calendar to be visible after clicking the date picker button
//        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the future (we assume the aria-label attribute contains the full date)
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//            // Check if the date is in the future
//            if (isFutureDate(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isFutureDate(String ariaLabel) {
//        // Example aria-label: "November 23 2024"
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//        LocalDate currentDate = LocalDate.now();
//        LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//        // Check if the date is in the future
//        return dateFromLabel.isAfter(currentDate);
//    }



//
//    public void selectRandomFutureDate() {
//        // First, click on the input button to open the date picker using XPath
//        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//        datePickerButton.click();
//
//        // Wait for the calendar to be visible after clicking the date picker button
//        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the future (we assume the aria-label attribute contains the full date)
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//            // Check if the date is in the future
//            if (isFutureDate(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isFutureDate(String ariaLabel) {
//        try {
//            // Adjusted DateTimeFormatter to handle both "MMMM yyyy" and "MMMM d yyyy" formats
//            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMMM yyyy");
//            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM d yyyy");
//
//            LocalDate currentDate = LocalDate.now();
//            LocalDate dateFromLabel = null;
//
//            try {
//                // Try parsing the date using "MMMM yyyy" format
//                dateFromLabel = LocalDate.parse(ariaLabel, formatter1);
//            } catch (Exception e) {
//                // If parsing fails, try the "MMMM d yyyy" format
//                try {
//                    dateFromLabel = LocalDate.parse(ariaLabel, formatter2);
//                } catch (Exception ex) {
//                    System.out.println("Error parsing date from aria-label: " + ariaLabel);
//                    return false;
//                }
//            }
//
//            // Check if the date is in the future
//            return dateFromLabel.isAfter(currentDate);
//        } catch (Exception e) {
//            System.out.println("Error processing the date: " + e.getMessage());
//            return false;
//        }
//    }
//
//}



//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//    @Component
//    public class RandomDateSelector extends CommonPage {
//
//        public RandomDateSelector(WebDriver driver) {
//            super(driver);
//        }
//
//        public void selectRandomFutureDate() {
//            // First, click on the input button to open the date picker using CSS selector
//            WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//            datePickerButton.click();
//
//            // Wait for the calendar to be visible after clicking the date picker button
//            WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//            // Find all enabled date cells within the calendar
//            List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//            // Filter out the dates that are in the future (we assume the aria-label attribute contains the full date)
//            List<WebElement> futureDates = new ArrayList<>();
//            for (WebElement date : enabledDates) {
//                String ariaLabel = date.getAttribute("aria-label");
//                // Check if the date is in the future
//                if (isFutureDate(ariaLabel)) {
//                    futureDates.add(date);
//                }
//            }
//
//            // Select a random future date
//            if (!futureDates.isEmpty()) {
//                Random rand = new Random();
//                int randomIndex = rand.nextInt(futureDates.size());
//                WebElement randomDate = futureDates.get(randomIndex);
//
//                // Scroll to the date and click it
//                scrollToElement(randomDate);
//                randomDate.click();
//            } else {
//                System.out.println("No future dates available to select.");
//            }
//        }
//
//        private boolean isFutureDate(String ariaLabel) {
//            try {
//                // Adjusted DateTimeFormatters to handle both "MMMM yyyy" and "MMMM d yyyy" formats
//                DateTimeFormatter formatterWithMonthAndYear = DateTimeFormatter.ofPattern("MMMM yyyy");
//                DateTimeFormatter formatterWithFullDate = DateTimeFormatter.ofPattern("MMMM d yyyy");
//
//                LocalDate currentDate = LocalDate.now();
//                LocalDate dateFromLabel = null;
//
//                try {
//                    // Try parsing the date using "MMMM yyyy" format for month-year only (e.g., January 2024)
//                    dateFromLabel = LocalDate.parse(ariaLabel, formatterWithMonthAndYear).withDayOfMonth(1); // Set to the first day of the month
//                } catch (Exception e) {
//                    // If parsing fails, try the "MMMM d yyyy" format for full dates (e.g., November 23 2024)
//                    try {
//                        dateFromLabel = LocalDate.parse(ariaLabel, formatterWithFullDate);
//                    } catch (Exception ex) {
//                        System.out.println("Error parsing date from aria-label: " + ariaLabel);
//                        return false;
//                    }
//                }
//
//                // Check if the date is in the future
//                return dateFromLabel.isAfter(currentDate);
//            } catch (Exception e) {
//                System.out.println("Error processing the date: " + e.getMessage());
//                return false;
//            }
//        }
//    }



//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }
//
//    public void selectRandomFutureDate() {
//        // First, click on the input button to open the date picker using XPath
//        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//        datePickerButton.click();
//
//        // Wait for the calendar to be visible after clicking the date picker button
//        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the future (next day or next two days)
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//
//            // Check if the aria-label corresponds to the next day or two days from now
//            if (isNextDayOrTwoDays(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isNextDayOrTwoDays(String ariaLabel) {
//        try {
//            // Example aria-label: "November 25 2024"
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//            LocalDate currentDate = LocalDate.now();
//            LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//            // Calculate next day or next two days
//            LocalDate nextDay = currentDate.plusDays(1);
//            LocalDate nextTwoDays = currentDate.plusDays(2);
//
//            // Check if the date is the next day or next two days
//            return dateFromLabel.isEqual(nextDay) || dateFromLabel.isEqual(nextTwoDays);
//        } catch (Exception e) {
//            // Ignore parsing errors (invalid formats)
//            System.out.println("Error parsing date from aria-label: " + ariaLabel);
//            return false;
//        }
//    }
//}



//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }
//
//    public void selectRandomFutureDate() {
//        // First, click on the input button to open the date picker using XPath
//        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//        datePickerButton.click();
//
//        // Wait for the calendar to be visible after clicking the date picker button
//        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the future (next day or next two days)
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//
//            // Check if the aria-label corresponds to the next day or two days from now
//            if (isNextDayOrTwoDays(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//
//            // Log the selected date
//            String selectedDate = randomDate.getAttribute("aria-label");
//            System.out.println("Selected future date: " + selectedDate);
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isNextDayOrTwoDays(String ariaLabel) {
//        try {
//            // Example aria-label: "November 26 2024"
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//            LocalDate currentDate = LocalDate.now();
//            LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//            // Calculate next day or next two days
//            LocalDate nextDay = currentDate.plusDays(1);
//            LocalDate nextTwoDays = currentDate.plusDays(2);
//
//            // Log the dates being compared
//            System.out.println("Comparing date: " + dateFromLabel + " with nextDay: " + nextDay + " and nextTwoDays: " + nextTwoDays);
//
//            // Check if the date is the next day or next two days
//            return dateFromLabel.isEqual(nextDay) || dateFromLabel.isEqual(nextTwoDays);
//        } catch (Exception e) {
//            // Ignore parsing errors (invalid formats)
//            System.out.println("Error parsing date from aria-label: " + ariaLabel);
//            return false;
//        }
//    }
//}



//
//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }
//
//    public void selectRandomFutureDate() {
//        // First, click on the input button to open the date picker using CSS selector
//        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//        datePickerButton.click();
//
//        // Wait for the calendar to be visible after clicking the date picker button
//        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the next day or next two days
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//            // Check if the date is the next day or next two days
//            if (isNextDayOrTwoDays(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//            System.out.println("Selected future date: " + randomDate.getAttribute("aria-label"));
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isNextDayOrTwoDays(String ariaLabel) {
//        try {
//            // Skip non-date labels like "2020 Year", "2021 Year", etc.
//            if (ariaLabel.contains("Year")) {
//                return false;  // Skip non-date labels like "2020 Year"
//            }
//
//            // Example aria-label: "November 26 2024"
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//            LocalDate currentDate = LocalDate.now();
//            LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//            // Calculate next day or next two days
//            LocalDate nextDay = currentDate.plusDays(1);
//            LocalDate nextTwoDays = currentDate.plusDays(2);
//
//            // Log the dates being compared
//            System.out.println("Comparing date: " + dateFromLabel + " with nextDay: " + nextDay + " and nextTwoDays: " + nextTwoDays);
//
//            // Check if the date is the next day or next two days
//            return dateFromLabel.isEqual(nextDay) || dateFromLabel.isEqual(nextTwoDays);
//        } catch (Exception e) {
//            // Ignore parsing errors for non-date labels
//            System.out.println("Error parsing date from aria-label: " + ariaLabel);
//            return false;
//        }
//    }
//}



//package com.totallygroup.pageObject;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class RandomDateSelector extends CommonPage {
//
//    public RandomDateSelector(WebDriver driver) {
//        super(driver);
//    }
//
//    public void selectRandomFutureDate() {
//        // First, click on the input button to open the date picker using CSS selector
//        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
//        datePickerButton.click();
//
//        // Wait for the calendar to be visible after clicking the date picker button
//        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));
//
//        // Find all enabled date cells within the calendar
//        List<WebElement> enabledDates = calendarPopup.findElements(By.cssSelector(".dijitCalendarEnabledDate"));
//
//        // Filter out the dates that are in the next day or next two days
//        List<WebElement> futureDates = new ArrayList<>();
//        for (WebElement date : enabledDates) {
//            String ariaLabel = date.getAttribute("aria-label");
//            // Check if the date is the next day or next two days
//            if (isNextDayOrTwoDays(ariaLabel)) {
//                futureDates.add(date);
//            }
//        }
//
//        // Select a random future date
//        if (!futureDates.isEmpty()) {
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(futureDates.size());
//            WebElement randomDate = futureDates.get(randomIndex);
//
//            // Scroll to the date and click it
//            scrollToElement(randomDate);
//            randomDate.click();
//            System.out.println("Selected future date: " + randomDate.getAttribute("aria-label"));
//        } else {
//            System.out.println("No future dates available to select.");
//        }
//    }
//
//    private boolean isNextDayOrTwoDays(String ariaLabel) {
//        try {
//            // Log aria-label to see what the date looks like
//            System.out.println("Parsing aria-label: " + ariaLabel);
//
//            // Skip non-date labels like "2020 Year", "2021 Year", etc.
//            if (ariaLabel.contains("Year")) {
//                return false;  // Skip non-date labels like "2020 Year"
//            }
//
//            // Example aria-label: "November 26 2024"
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
//            LocalDate currentDate = LocalDate.now();
//            LocalDate dateFromLabel = LocalDate.parse(ariaLabel, formatter);
//
//            // Log the dates being compared
//            System.out.println("Comparing date: " + dateFromLabel + " with current date: " + currentDate);
//
//            // Calculate next day or next two days
//            LocalDate nextDay = currentDate.plusDays(1);
//            LocalDate nextTwoDays = currentDate.plusDays(2);
//
//            // Log the dates being compared
//            System.out.println("Comparing date: " + dateFromLabel + " with nextDay: " + nextDay + " and nextTwoDays: " + nextTwoDays);
//
//            // Check if the date is the next day or next two days
//            return dateFromLabel.isEqual(nextDay) || dateFromLabel.isEqual(nextTwoDays);
//        } catch (Exception e) {
//            // Ignore parsing errors for non-date labels
//            System.out.println("Error parsing date from aria-label: " + ariaLabel);
//            return false;
//        }
//    }
//}
//


package com.totallygroup.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class RandomDateSelector extends CommonPage {

    @Autowired
    public RandomDateSelector(WebDriver driver) {
        super(driver);
    }

    public void selectRandomFutureDate() {
        // Wait for the date picker input field to be clickable and click to open the date picker
        WebElement datePickerButton = waitForElementToBeClickable(By.xpath("//*[@id='widget_DateBox_2']/div[1]/input"));
        datePickerButton.click();

        // Wait for the calendar to be visible
        WebElement calendarPopup = waitForElementToBeVisible(By.cssSelector("#DateBox_2_popup"));

        // Use FluentWait to wait for the date elements to be visible and clickable (allowing for slower loads)
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(40)) // Increase the wait time
                .pollingEvery(Duration.ofMillis(500)) // Set polling frequency
                .ignoring(Exception.class);

        // Locate all the date elements on the calendar
        List<WebElement> dateElements = fluentWait.until(driver ->
                driver.findElements(By.xpath("//td[@class='dijitCalendarEnabledDate dijitCalendarDateTemplate']"))
        );

        // Debugging output
        System.out.println("Found " + dateElements.size() + " date elements.");

        // Get today's date
        LocalDate today = LocalDate.now();

        // Create a list to hold future dates
        List<WebElement> futureDates = new ArrayList<>();

        // DateTimeFormatter for the input format (DD/MM/YYYY)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Filter the dates that are within the next 7 days
        for (WebElement date : dateElements) {
            String dateLabel = date.getAttribute("aria-label");

            // Skip the element if the dateLabel is null
            if (dateLabel != null) {
                try {
                    // Parse the date using the specified format (DD/MM/YYYY)
                    LocalDate dateLocal = LocalDate.parse(dateLabel, formatter);

                    // Add the date to the list if it is within the next 7 days
                    if (!dateLocal.isBefore(today) && !dateLocal.isAfter(today.plusDays(7))) {
                        futureDates.add(date);
                    }
                } catch (Exception e) {
                    // Handle any parsing errors (in case of incorrect date format)
                    System.out.println("Error parsing date: " + dateLabel);
                }
            }
        }

        // Log the number of future dates found
        System.out.println("Found " + futureDates.size() + " future dates within the next 7 days.");

        // Pick a random date from the filtered future dates
        if (!futureDates.isEmpty()) {
            Random rand = new Random();
            WebElement selectedDate = futureDates.get(rand.nextInt(futureDates.size()));

            // Move the mouse to the selected date before clicking
            Actions actions = new Actions(driver);
            actions.moveToElement(selectedDate).perform();

            // Click on the selected date
            selectedDate.click();
        } else {
            System.out.println("No available dates within the next 7 days.");
        }

        // Handle possible popups (e.g., "OK" button in case of modal)
        try {
            WebElement okButton = waitForElementToBeClickable(By.xpath("//button[@aria-label='OK']"));
            if (okButton.isDisplayed()) {
                okButton.click();
                System.out.println("Clicked OK button to close popup.");
            }
        } catch (Exception e) {
            System.out.println("No popup found, or failed to click OK button.");
        }

        // Handle click interception (e.g., overlay blocking the button click)
        try {
            WebElement targetButton = waitForElementToBeClickable(By.xpath("//*[@id='Button_118']"));
            targetButton.click();
        } catch (Exception e) {
            System.out.println("Element click intercepted: " + e.getMessage());
        }

        // Wait for the region title to match the selected region
        try {
            WebElement regionTitle = waitForElementToBeVisible(By.xpath("//div[@class='dijitOutput left-decoration DecorationText']"));
            System.out.println("Region title found: " + regionTitle.getText());
        } catch (Exception e) {
            System.out.println("Failed to find region title: " + e.getMessage());
        }
    }
}
