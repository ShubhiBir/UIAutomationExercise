package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.EBayHomePage;
import pages.ItemListPage;
import utils.Manager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageStepDef {
    Manager manager;
    WebDriver driver;

    public HomePageStepDef(Manager manager) {
        this.manager = manager;
        this.driver = manager.getDriver();
    }

    /**
     * Use this step definition to launch the browser instance.
     */
    @Given("the user opens the browser")
    public void userOpensTheBrowser() {
        manager.setDriver(driver);
    }

    /**
     * Use this step definition to navigate to the specified Website.
     * @param url the website Url user wishes to navigate to.
     */
    @When("the user navigates to the website {string}")
    public void theUserNavigatesToTheEbayCom(String url) {
        driver.navigate().to(url);
    }

    /**
     * Use this step definition to validate that the user is on eBay Home Page.
     */
    @Then("the user should be on home page of eBay")
    public void theUserShouldBeOnHomePageOfEBay() {
        Assertions.assertEquals("https://www.ebay.com/", driver.getCurrentUrl(), "The user is not on the eBay Home Page. Current url: " + driver.getCurrentUrl());
    }

    /**
     * Use this step definition to search for an item on eBay.
     * @param searchItem the item to search for.
     */
    @When("the user search for {string}")
    public void theUserSearchForBook(String searchItem) {
        new EBayHomePage(driver).searchItem(searchItem);
    }

    /**
     * Use this step definition to validate the list of searched items is displayed.
     * @param searchItem the search term used.
     */
    @Then("a list of {string} should be displayed")
    public void aListOfBooksShouldBeDisplayed(String searchItem) {
        EBayHomePage eBayHomePage = new EBayHomePage(driver);
        assertTrue(eBayHomePage.isListVisible(), searchItem + " List is not visible.");
        assertTrue(eBayHomePage.getItemsCount() > 0, searchItem + " List is empty.");
    }

    /**
     * Use this step definition to click on the first item in the search result list.
     * This action opens the item in a new browser window.
     * The method handles switching to the newly opened window.
     */
    @When("the user clicks on the first item in the list")
    public void theUserClicksOnTheFirstBookInTheList() {
        new EBayHomePage(driver).selectFirstItemAndSwitchWindow();
    }

    /**
     * Use this step definition to validate that the item listing page has been loaded in the new window.
     */
    @Then("the user should be navigated to the item listing page")
    public void userNavigatesToItemListingPage() {
        assertTrue(new ItemListPage(driver).verifyPageLoad(),
                "The Item Listing Page is not loaded as expected.");
    }

    /**
     * Use this step definition to click on button - Add to Cart.
     */
    @When("the user clicks on Add to Cart")
    public void theUserClicksOnAddToCart() {
        new ItemListPage(driver).addItemToCart();
    }

    /**
     * Use this step definition to validate the confirmation message after adding an item to the cart.
     * @param message the expected confirmation message.
     */
    @Then("a confirmation message {string} should be displayed")
    public void aWindowWithConfirmationAddedToCartShouldBeDisplayed(String message) {
        assertEquals(message, new ItemListPage(driver).verifyConfirmationMessage(),
                "The confirmation message is not as expected.");
    }

    /**
     * Use this step definition to close the Confirmation message.
     */
    @When("the user closes the confirmation message")
    public void theUserClosesTheConfirmationMessage() {
        new ItemListPage(driver).closeConfirmationMessage();
    }

    /**
     * Use this step definition to validate the number of items displayed on the cart icon.
     * @param expectedCount the expected number of items.
     */
    @Then("the cart icon should display {int} item")
    public void verifyCartIconNumberOfItems(int expectedCount) {
        assertEquals(expectedCount, new ItemListPage(driver).getCartCount(),
                "Cart Item Count does not match!");
    }

}
