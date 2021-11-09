package com.cydeo.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Driver {

    // private static WebDriver obj ;   singleton
    private  static InheritableThreadLocal<WebDriver> driverPool=new InheritableThreadLocal<>();   // parallel

    private Driver(){ }

    /**
     * Return obj with only one WebDriver instance
     * @return same WebDriver if exists , new one if null
     */
    public static WebDriver getDriver(){
        // read the browser type you want to launch from properties file
        //String browserName = ConfigReader.read("browser") ;
        String browserName = System.getProperty("browser") != null ? browserName = System.getProperty("browser") : ConfigReader.read("browser");

        // if(obj == null){   singleton
        if(driverPool.get() == null){ //PARALLEL

            // according to browser type set up driver correctly
            switch (browserName ){
                case "remote-chrome":
                    try {
                        // assign your grid server address
                        String gridAddress = "54.91.250.94";
                        URL url = new URL("http://"+ gridAddress + ":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("chrome");
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                        //driverPool.set(new RemoteWebDriver(new URL("http://0.0.0.0:4444/wd/hub"),desiredCapabilities));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "chrome" :
                    WebDriverManager.chromedriver().setup();
                    //  obj = new ChromeDriver();  singleton
                    driverPool.set(new ChromeDriver());  // parallel
                    break;
                case "firefox" :
                    WebDriverManager.firefoxdriver().setup();
                    // obj = new FirefoxDriver();  singleton
                    driverPool.set(new FirefoxDriver()); // parallel
                    break;
                // other browsers omitted
                default:
                    // obj = null ;  singleton
                    driverPool.set(null);  // parallel
                    System.out.println("UNKNOWN BROWSER TYPE!!! " + browserName);
            }
            // return obj ; singleton
            return driverPool.get(); // parallel



        }else{
//            System.out.println("You have it just use existing one");
            // return obj ;  singleton
            return driverPool.get(); // parallel


        }

    }

    /**
     * Quitting the browser and setting the value of
     * WebDriver instance to null because you can re-use already quitted driver
     */
    public static void closeBrowser(){

        // check if we have WebDriver instance or not
        // basically checking if obj is null or not
        // if not null
        // quit the browser
        // make it null , because once quit it can not be used
       /* if(obj != null ){   SINGLETON
            obj.quit();
            // so when ask for it again , it gives us not quited fresh driver
            obj = null ;
        }

        */

        if(driverPool.get() != null ){   //PARALLEL
            driverPool.get().quit();
            // so when ask for it again , it gives us not quited fresh driver
            driverPool.set(null);
        }

    }



}
