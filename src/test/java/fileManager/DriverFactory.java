package fileManager;

import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;

public class DriverFactory {
	
	 private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

	    // To quit the drivers and browsers at the end only.
	    private static List<WebDriver> storedDrivers = new ArrayList<>();

	    static {
	        Runtime.getRuntime().addShutdownHook(new Thread() {
	            public void run() {
	                storedDrivers.forEach(WebDriver::quit);
	            }
	        });
	    }

	    public DriverFactory() {}

	    public static WebDriver getDriver() {
	        WebDriver driver=drivers.get();
	        return driver;

	    }

	    public static void addDriver(WebDriver driver) {
	        storedDrivers.add(driver);
	        drivers.set(driver);
	    }

	    public static void removeDriver() {
	        storedDrivers.remove(drivers.get());
	        drivers.remove();
	    }

}
