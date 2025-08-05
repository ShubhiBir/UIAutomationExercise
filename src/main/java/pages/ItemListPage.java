package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ItemListPage extends Page {

    public ItemListPage(WebDriver driver){
        super(driver);
        System.out.println("Home Page title is: " + driver.getTitle());
    }

    @FindBy(css = "div[class*='x-atc-action__overlay'] span")
    private  WebElement confirmationMessage;

    @FindBy(css = "div[class*='x-atc-action__overlay'] button[class*='close']")
    private  WebElement closeConfirmationMessage;

    @FindBy(css = "[class*='x-atc-action']")
    private WebElement addToCart;

    @FindBy(css = "span[class*='gh-cart__icon'] span")
    private WebElement cartIcon;

    public boolean verifyPageLoad() {

return new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(addToCart)).isDisplayed();
    }

    public void addItemToCart() {
        addToCart.click();
    }

    public String verifyConfirmationMessage() {
        return new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(confirmationMessage)).getText();
    }

    public void closeConfirmationMessage() {
        closeConfirmationMessage.click();
    }

    public int getCartCount() {
return Integer.parseInt(cartIcon.getText());
    }
}
