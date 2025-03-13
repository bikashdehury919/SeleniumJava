package testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import frameworkFactory.BaseTest;
import frameworkFactory.TestListener;
import functionLibrary.DemoAppFunctions;

public class LoginTest extends BaseTest {

    @Test(testName = "Login To Demo Application", description = "The Test will Login to demo application")
    public void LoginDemoApplication() throws IOException {
        DemoAppFunctions demofunc = new DemoAppFunctions(TestListener.testReport.get());
        demofunc.LoginApplication("bikash@gmail.com", "abc@1234");
    }

    @Test(testName = "Login To Demo1 Application", description = "The Test will Login to demo1 application")
    public void LoginDemoApplication1() throws IOException {
        DemoAppFunctions demofunc = new DemoAppFunctions(TestListener.testReport.get());
        demofunc.LoginApplication("bikash@gmail.com", "abc@1234");
    }
}
