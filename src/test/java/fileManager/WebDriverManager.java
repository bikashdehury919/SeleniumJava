package fileManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import frameworkFactory.GridSetup;


public class WebDriverManager {

    private  WebDriver driver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;
    private static String gridUrl;
    private GridSetup gridSetup;

    public WebDriverManager() throws IOException {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
        gridUrl= FileReaderManager.getInstance().getConfigReader().getGridUrl();
        
    }

    public WebDriver getDriver() {
        if(driver == null)
            try {
                driver = createDriver();
                DriverFactory.addDriver(driver);
            }catch(IOException e){
                System.out.println("Driver initiation failed");
            }
        return driver;
    }

    private WebDriver createDriver() throws IOException {
        gridSetup = new GridSetup();
        switch (environmentType) {
            case LOCAL:
                driver = createLocalDriver();
                break;
            case REMOTE:
                gridSetup.setUpGrid();
                driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    private WebDriver createRemoteDriver() throws IOException {
        MutableCapabilities capabilities;

        switch (driverType) {
            case FIREFOX:
                capabilities = new FirefoxOptions();
                break;
            case EDGE:
                capabilities = new EdgeOptions();
                break;
            case CHROME:
            default:
                capabilities = new ChromeOptions();
                break;
        }

        WebDriver driver = new RemoteWebDriver(new URL(gridUrl), capabilities);

        if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize()) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));

        return driver;
    }

    private WebDriver createLocalDriver() throws IOException {
        switch (driverType) {
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "path/to/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case CHROME:           
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_settings.popups", 0);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(options);
                break;
            case EDGE:
                System.setProperty("webdriver.ie.driver", "path/to/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
        }

        if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
            driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait()));
        return driver;
    }


   public  void closeDriver() {
        System.out.println("Driver to be closed");
        driver.close();
        driver.quit();
    }

}