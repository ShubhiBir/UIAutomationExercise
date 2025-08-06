package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EBayHomePage extends Page {
    private WebDriverWait wait;

    public EBayHomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
        wait.until(driver -> {
            try {
                WebElement firstItem = searchList.get(0).findElement(By.cssSelector("a[href]"));
                return firstItem.isDisplayed() &&
                        firstItem.getSize().height > 0 &&
                        firstItem.getSize().width > 0;
            } catch (Exception e) {
                return false;
            }
        });
        WebElement firstItem = searchList.get(0).findElement(By.cssSelector("a[href]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstItem);
        try {
            firstItem.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstItem);
        }
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}
