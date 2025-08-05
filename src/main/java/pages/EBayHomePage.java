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
    private WebDriverWait wait;

    public EBayHomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Home Page title is: " + driver.getTitle());
    }

    @FindBy(css = "#gh-ac")
    private WebElement searchBox;

    @FindBy(css = "#srp-river-results > ul > li")
    private List<WebElement> searchList;

    public void searchItem(String searchItem) {
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(searchItem + Keys.ENTER);
    }

    public boolean isListVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("srp-river-results")));
        return !searchList.isEmpty();
    }

    public int getItemsCount() {
        return searchList.size();
    }

    public void selectFirstItemAndSwitchWindow() {
        String originalWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("srp-river-results")));
        WebElement firstItem = searchList.get(0).findElement(By.cssSelector("a[href]"));
        wait.until(ExpectedConditions.visibilityOf(firstItem));
        wait.until(ExpectedConditions.elementToBeClickable(firstItem));
        if (!searchList.isEmpty() && firstItem.isDisplayed()) {
            firstItem.click();
        } else throw new IllegalStateException("No search results found to click.");

        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}
