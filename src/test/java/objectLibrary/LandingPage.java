package objectLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

    public LandingPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userEmail")
    public WebElement userEmail;

    @FindBy(id = "userPassword")
    public WebElement passwordEle;

    @FindBy(id = "login")
    public WebElement submit;

    @FindBy(css = "[class*='flyInOut']")
    public WebElement errorMessage;
}
