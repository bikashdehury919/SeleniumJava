package frameworkFactory;

import enums.EnvironmentType;
import fileManager.FileReaderManager;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Properties;

public class GridSetup {



        public static String GridPort=null;
        public static String ChromeNodes=null;
        public static String ParellelGridType=null;
        public static String driverPath=null;
        public static String TestSuit=null;
        public static String Browser=null;
        public static String zeleniumContainer =null;
        public static String parrallelRunType=null;



        public void setUpGrid() throws MalformedURLException, UnknownHostException {


            try {


               GridPort = FileReaderManager.getInstance().getConfigReader().getGridPort();
               ChromeNodes = FileReaderManager.getInstance().getConfigReader().getChromeNodes();
               parrallelRunType= FileReaderManager.getInstance().getConfigReader().getParralelRunType();
               zeleniumContainer = FileReaderManager.getInstance().getConfigReader().getZeleniumContainerCount();

                String path;
                Runtime rn;
                Process pr;

                try {
                    if(parrallelRunType.equalsIgnoreCase("zelenium")) {

                        String zeleniumContainer = GridSetup.zeleniumContainer;
                        path = "cmd /c start src\\resources\\Drivers\\DockerFiles\\zelenium.bat " + zeleniumContainer;
                        rn = Runtime.getRuntime();
                        pr = rn.exec(path);
                        Thread.sleep(10000);
                        String pathZaleniumOpen = "cmd /c start src\\resources\\Drivers\\DockerFiles\\openZeleniumURL.bat";
                        Runtime rn1 = Runtime.getRuntime();
                        Process p1r = rn1.exec(pathZaleniumOpen);

                    }else{

                        String chromeNodes = GridSetup.ChromeNodes;
                        path = "cmd /c start src\\resources\\Drivers\\DockerFiles\\DockerUp.bat " + chromeNodes;
                        rn = Runtime.getRuntime();
                        pr = rn.exec(path);
                        Thread.sleep(20000);

                    }

                } catch (Exception e) {
                    System.out.println("Grid start issue in Docker");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        public static void gridCleanUp() throws IOException
        {

            String path;
            Runtime rn;
            Process pr;
            try {
                if (parrallelRunType.equalsIgnoreCase("zelenium")) {
                    path = "cmd /c start src\\resources\\Drivers\\DockerFiles\\StopZelenium.bat";
                    rn = Runtime.getRuntime();
                    pr = rn.exec(path);
                } else {
                    path = "cmd /c start src\\resources\\Drivers\\DockerFiles\\DockerDown.bat";
                    rn = Runtime.getRuntime();
                    pr = rn.exec(path);
                }
            }catch(Exception e) {
                System.out.println("Error in closing Grid Setup");

            }




        }
}
