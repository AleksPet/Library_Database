package com.cydeo.utility;

import org.openqa.selenium.By;

public class BrowserUtil {

    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void logIn(){
        String url = ConfigReader.read("url");
        String username = ConfigReader.read("username");
        String password = ConfigReader.read("password");

        Driver.getDriver().get(url);
        Driver.getDriver().findElement(By.id("inputEmail")).sendKeys(username);
        Driver.getDriver().findElement(By.id("inputPassword")).sendKeys(password);
        Driver.getDriver().findElement(By.xpath("//button[@type=\"submit\"]")).click();
    }

    public static String bookCount(){
        return Driver.getDriver().findElement(By.xpath("//h2[@id='borrowed_books']")).getText();
    }



}
