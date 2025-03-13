package fileManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import enums.DriverType;
import enums.EnvironmentType;

public class ConfigFileReader {
    private Properties properties;
    private final String propertyFilePath = "src\\resources\\Config\\config.properties";

    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getDriverPath() {
        String driverPath = properties.getProperty("driverPath");
        if (driverPath != null) return driverPath;
        else
            throw new RuntimeException("Driver Path not specified in the Configuration.properties file for the Key:driverPath");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait != null) {
            try {
                return Long.parseLong(implicitlyWait);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
            }
        }
        return 30;
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if (url != null) return url;
        else
            throw new RuntimeException("Application Url not specified in the Configuration.properties file for the Key:url");
    }

    public DriverType getBrowser() {
        String browserName = properties.getProperty("browser");
        if (browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
        else if (browserName.equals("iexplorer")) return DriverType.EDGE;
        else
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    public EnvironmentType getEnvironment() {
        String environmentName = properties.getProperty("environment");
        if (environmentName == null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL;
        else if (environmentName.equals("remote")) return EnvironmentType.REMOTE;
        else
            throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }

    public Boolean getBrowserWindowSize() {
        String windowSize = properties.getProperty("windowMaximize");
        if (windowSize != null) return Boolean.valueOf(windowSize);
        return true;
    }

    public String getGridUrl() {
        String hostName = properties.getProperty("host");
        String port = properties.getProperty("port");
        if (hostName != null && port != null) {
            String url = "http://" + hostName + ":" + port + "/wd/hub";
            return url;
        } else
            throw new RuntimeException("Invalid Value specified in the Configuration.properties file for the Key Host:Port");
    }

    public String getGridPort() {

        String port = properties.getProperty("port");
        if (port != null) {
            return port;
        } else
            throw new RuntimeException("Invalid Value specified in the Configuration.properties file for the Key Port");
    }


    public String getChromeNodes() {
        String chromeNodes = properties.getProperty("ChromeNodes");

        if (chromeNodes != null) {

            return chromeNodes;
        } else
            throw new RuntimeException("Invalid Value specified in the Configuration.properties file for the Key ChromeNodes");
    }

    public String getZeleniumContainerCount() {
        String zaleniumContainerCount = properties.getProperty("zaleniumContainerCount");

        if (zaleniumContainerCount != null) {

            return zaleniumContainerCount;
        } else return "2";
    }


    public String getParralelRunType() {
        String parallelRunType = properties.getProperty("parallelRunType");
        if (parallelRunType != null) {
            return parallelRunType;
        } else
            throw new RuntimeException("Invalid Value specified in the Configuration.properties file for the Key parallelRunType");

    }

    public String getReportConfigPath(){
    	String reportConfigPath = properties.getProperty("reportConfigPath");
    	if(reportConfigPath!= null) return reportConfigPath;
    	else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");		
    }
    
    public String getTestDataPath() {
        String testDataPath = properties.getProperty("testDataPath");
        if (testDataPath != null) {
            return testDataPath;
        } else
            throw new RuntimeException("Invalid Value specified in the Configuration.properties file for the Key TestDataPath");

    }
}