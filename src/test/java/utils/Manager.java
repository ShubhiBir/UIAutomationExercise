package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Manager {

    private WebDriver driver;
    public void setDriver(WebDriver driver){

        this.driver = driver;
    }

    public WebDriver getDriver(){
return driver;
    }
}
