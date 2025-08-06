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

    @FindBy(css = "span[class*='gh-cart__icon'] span")
    private WebElement cartIcon;

    @FindBy(css = "div.listbox__options.listbox-button__listbox")
    private WebElement listBox;

    @FindBy(xpath = "//div[@class='listbox__option']")
    private List<WebElement> options;

    public boolean verifyPageLoad() {
        WebElement buyBox = driver.findElement(By.cssSelector("ul[data-testid='x-buybox-cta']"));
        return wait
                .until(ExpectedConditions.visibilityOf(buyBox)).isDisplayed();
    }

    public void addItemToCart() {
        handleDropDownIfPresent();
        WebElement freshBuyBox = driver.findElement(By.cssSelector("div.vim.vi-evo-row-gap"));
        WebElement addToCartButton = null;
        List<WebElement> buttons = freshBuyBox.findElements(By.xpath(".//li"));
        for (WebElement li : buttons) {
            try {
                addToCartButton = li.findElement(
                        By.xpath(".//a[contains(@href,'https://cart.payments.ebay.com/sc/add?') and .//span[text()='Add to cart']]")
                );
                break;
            } catch (NoSuchElementException e) {
                System.out.println("No Such element found!");
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartButton);
        try {
            addToCartButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
        }
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
    }

    private void handleDropDownIfPresent() {
        Actions actions = new Actions(driver);
        try {
            WebElement dropDown = driver.findElement(By.xpath("//span[@class='btn__text' and text()='Select']/ancestor::button"));
            if (dropDown.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(dropDown));
                actions.moveToElement(dropDown).click().perform();
                wait.until(ExpectedConditions.attributeToBe(dropDown, "aria-expanded", "true"));
                wait.until(ExpectedConditions.visibilityOf(listBox));
                if (options.size() > 1) {
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
