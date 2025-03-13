package objectLibrary;

import org.openqa.selenium.WebDriver;

public class ApplicationPages {
    public LandingPage Lpage;

    public ApplicationPages(WebDriver driver) {
        Lpage = new LandingPage(driver);
    }
}
