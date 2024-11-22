package dayForceTests;


import com.totallygroup.pageObject.HomePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;


public class HomePageTests extends BaseTest{

    @Autowired
    private HomePage homePage;



    @Test
    public void testHamburgerToOpenSideMenu() {
        homePage.clickHamburgerIcon();

    }
}