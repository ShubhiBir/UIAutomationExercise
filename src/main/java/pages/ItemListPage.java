package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ItemListPage extends Page {
    private WebDriverWait wait;

    public ItemListPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Home Page title is: " + driver.getTitle());
    }

    @FindBy(css = "div[class*='x-atc-action__overlay'] span")
    private WebElement confirmationMessage;

    @FindBy(css = "div[class*='x-atc-action__overlay'] button[class*='close']")
    private WebElement closeConfirmationMessage;

    @FindBy(css = "[class*='x-atc-action']")
    private WebElement addToCart;

    @FindBy(css = "span[class*='gh-cart__icon'] span")
    private WebElement cartIcon;

    @FindBy(css = "button.listbox-button__control")
    private WebElement dropDown;

    @FindBy(css = "div.listbox__options.listbox-button__listbox")
    private WebElement listBox;

    @FindBy(xpath = "//div[@class='listbox__option']")
    private List<WebElement> options;

    public boolean verifyPageLoad() {
        return wait
                .until(ExpectedConditions.visibilityOf(addToCart)).isDisplayed();
    }

    public void addItemToCart() {
        if (dropDown.isEnabled()) {
            handleDropDownIfPresent();
        }
        addToCart.click();
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
    }

    private void handleDropDownIfPresent() {
        Actions actions = new Actions(driver);
        try {
            if (dropDown.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(dropDown));
                actions.moveToElement(dropDown).click().perform();
                wait.until(ExpectedConditions.attributeToBe(dropDown, "aria-expanded", "true"));
                wait.until(ExpectedConditions.visibilityOf(listBox));
                if(options.size()>1) {
                    options.get(1).click();
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No Dropdown Found, continue without selecting it.");
        }
    }

    public String verifyConfirmationMessage() {
        return confirmationMessage.getText();
    }

    public void closeConfirmationMessage() {
        closeConfirmationMessage.click();
    }

    public int getCartCount() {
        String cartText = wait.until(ExpectedConditions.visibilityOf(cartIcon)).getText();
        return cartText.isEmpty() ? 0 : Integer.parseInt(cartText);
    }
}
