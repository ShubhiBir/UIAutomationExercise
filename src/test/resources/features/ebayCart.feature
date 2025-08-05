Feature: Add an item in cart on https://www.ebay.com/ website

  @Test001
  Scenario: Verify that an item can be added to the eBay cart

    Given the user opens the browser
    When the user navigates to the website 'https://www.ebay.com/'
    Then the user should be on home page of eBay

    When the user search for 'book'
    Then a list of 'books' should be displayed

    When the user clicks on the first item in the list
    Then the user should be navigated to the item listing page

    When the user clicks on Add to Cart
    Then a confirmation message 'Added to cart' should be displayed

    When the user closes the confirmation message
    Then the cart icon should display 1 item

