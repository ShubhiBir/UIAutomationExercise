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
public HomePageStepDef(Manager manager){
    this.manager = manager;
    this.driver = manager.getDriver();
}
    @Given("the user opens the browser")
    public void userOpensTheBrowser() {
manager.setDriver(driver);
    }

    @When("the user navigates to the website {string}")
    public void theUserNavigatesToTheEbayCom(String url) {
        driver.get(url);
    }

    @Then("the user should be on home page of eBay")
    public void theUserShouldBeOnHomePageOfEBay() {
        Assertions.assertEquals("https://www.ebay.com/", driver.getCurrentUrl(), "The user is not on the eBay Home Page. Current url: " + driver.getCurrentUrl());
    }

    @When("the user search for {string}")
    public void theUserSearchForBook(String searchItem) {
    new EBayHomePage(driver).searchItem(searchItem);
    }

    @Then("a list of {string} should be displayed")
    public void aListOfBooksShouldBeDisplayed(String searchItem) {
    EBayHomePage eBayHomePage = new EBayHomePage(driver);
    assertTrue(eBayHomePage.isListVisible(), searchItem +" List is not visible.");
    assertTrue(eBayHomePage.getItemsCount() >0,searchItem + " List is empty.");
    }

    @When("the user clicks on the first book in the list")
    public void theUserClicksOnTheFirstBookInTheList() {
       new EBayHomePage(driver).selectFirstItemAndSwitchWindow();
    }


    @Then("the user should be navigated to the item listing page")
    public void userNavigatesToItemListingPage(){
        assertTrue(new ItemListPage(driver).verifyPageLoad(),"The Item Listing Page is not loaded as expected.");
    }


    @When("the user clicks on Add to Cart")
    public void theUserClicksOnAddToCart() {
       new ItemListPage(driver).addItemToCart();
    }

    @Then("a confirmation message {string} should be displayed")
    public void aWindowWithConfirmationAddedToCartShouldBeDisplayed(String message) {
        assertEquals(new ItemListPage(driver).verifyConfirmationMessage(), message, "The confirmation message is not as expected.");
    }

    @When("the user closes the confirmation message")
    public void theUserClosesTheConfirmationMessage() {
        new ItemListPage(driver).closeConfirmationMessage();
    }

    @Then("the cart icon should display {int} item")
    public void verifyCartIconNumberOfItems(int expectedCount){
    assertEquals(expectedCount,new ItemListPage(driver).getCartCount(),"Cart Item Count does not match!");
    }

}
