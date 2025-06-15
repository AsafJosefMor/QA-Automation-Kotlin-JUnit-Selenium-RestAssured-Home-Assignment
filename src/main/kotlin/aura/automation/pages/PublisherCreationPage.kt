package aura.automation.pages

import aura.automation.utils.ApiRoutes
import aura.automation.utils.WaitUtils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions

/**
 * Page Object for the “Create Publisher” screen.
 *
 * Extends BasePage to inherit common navigation and load-waiting functionality.
 *
 * @constructor Initializes the page’s WebElement fields.
 * @param driver The Selenium WebDriver instance.
 */
class PublisherCreationPage(driver: WebDriver): BasePage(driver) {

    init {
        PageFactory.initElements(driver, this)
    }

    /** Input field for entering the publisher’s email address. */
    @FindBy(id = "email")
    private lateinit var emailInput: WebElement

    /** Button to submit the publisher creation form. */
    @FindBy(css = "button[type='submit']")
    private lateinit var submitBtn: WebElement

    /**
     * Navigate directly to the publisher-creation URL.
     */
    fun openCreateEntity() {
        super.openCreateEntity(ApiRoutes.PUBLISHER_CREATE)
    }

    /**
     * Fills out and submits the Create Publisher form.
     *
     * @param email The email address of the new publisher.
     */
    fun create(email: String) {
        openCreateEntity()
        emailInput.sendKeys(email)
        submitBtn.click()
    }
}