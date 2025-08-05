package utils;

import org.openqa.selenium.WebDriver;

public class Manager {

    private WebDriver driver;

    public void setDriver(WebDriver driver) {

        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
