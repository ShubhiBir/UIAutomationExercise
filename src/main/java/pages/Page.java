package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class Page {
    protected WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitForPageLoad();
    }

    public void waitForPageLoad(){
       try{
           Thread.sleep(Duration.ofSeconds(5));
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }
}
