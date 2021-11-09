package com.cydeo.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Driver {

    private  static InheritableThreadLocal<WebDriver> driverPool=new InheritableThreadLocal<>();   // parallel

    private Driver(){ }

    public static WebDriver getDriver(){
        String browserName = System.getProperty("browser") != null ? browserName = System.getProperty("browser") : ConfigReader.read("browser");

        if(driverPool.get() == null){

            switch (browserName ){
                case "remote-chrome":
                    try {
                        String gridAddress = "54.91.250.94";
                        URL url = new URL("http://"+ gridAddress + ":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("chrome");
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "chrome" :
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver());
                    break;
                case "firefox" :
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                default:
                    driverPool.set(null);
                    System.out.println("UNKNOWN BROWSER TYPE!!! " + browserName);
            }
            return driverPool.get();

        }else{
            return driverPool.get();
        }
    }


    public static void closeBrowser(){
        if(driverPool.get() != null ){
            driverPool.get().quit();
            driverPool.set(null);
        }
    }

}
