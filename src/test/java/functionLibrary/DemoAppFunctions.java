package functionLibrary;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import frameworkFactory.BaseTest;

public class DemoAppFunctions extends BaseTest {
    private ExtentTest test;

    public DemoAppFunctions(ExtentTest test) {
        this.test = test;
    }

    public void LoginApplication(String email, String password) throws IOException {
        // Step 1: Verify presence of email input
        test.log(Status.INFO, "Step 1: Verify presence of email input");
        mIsElementPresent(getPage().Lpage.userEmail);

        // ... add more steps as needed
    }
}
