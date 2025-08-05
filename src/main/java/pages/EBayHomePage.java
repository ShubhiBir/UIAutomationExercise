package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EBayHomePage extends Page {

    public EBayHomePage(WebDriver driver){
        super(driver);
        System.out.println("Home Page title is: " + driver.getTitle());
    }


    @FindBy(css = "#gh-ac")
    private  WebElement searchBox;

    @FindBy(css = "#srp-river-results > ul > li a[href]")
    private List<WebElement> searchList;


    public void searchItem(String searchItem) {
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(searchItem + Keys.ENTER);
    }


    public boolean isListVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("srp-river-results")));
      return !searchList.isEmpty();
    }

    public int getItemsCount() {
       return searchList.size();
    }

    public void selectFirstItemAndSwitchWindow() {
String originalWindow = driver.getWindowHandle();
        if(!searchList.isEmpty()){
            searchList.get(0).click();
        } else throw new IllegalStateException("No search results found to click.");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for(String windowHandle : driver.getWindowHandles()){
            if(!windowHandle.equals(originalWindow)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}
