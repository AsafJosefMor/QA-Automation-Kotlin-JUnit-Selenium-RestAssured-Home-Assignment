package aura.automation.pages

import aura.automation.models.Post
import aura.automation.utils.ApiRoutes
import aura.automation.utils.WaitUtils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions

/**
 * Page Object for the “Create Post” screen.
 *
 * Extends BasePage to inherit navigation and load-waiting behavior.
 *
 * @constructor Initializes the page’s WebElement fields.
 * @param driver The Selenium WebDriver instance.
 */
class PostCreationPage(driver: WebDriver): BasePage(driver) {
    init { PageFactory.initElements(driver, this) }

    @FindBy(id = "title")
    private lateinit var titleInput: WebElement

    @FindBy(xpath = "//*[contains(text(),'Status')]/ancestor::div[1]//input[@role='combobox']")
    private lateinit var statusInput: WebElement

    @FindBy(id = "published")
    private lateinit var publishedCheckbox: WebElement

    @FindBy(xpath = "//*[contains(text(),'Publisher')]/ancestor::div[1]//input[@role='combobox']")
    private lateinit var publisherInput: WebElement

    @FindBy(css = "button[type='submit']")
    private lateinit var submitBtn: WebElement

    /**
     * Navigate directly to the post-creation URL.
     */
    fun openCreateEntity() {
        super.openCreateEntity(ApiRoutes.POST_CREATE)
    }

    /**
     * Fills out and submits the Create Post form.
     *
     * @param post A Post model containing all necessary field values.
     */
    fun create(post: Post) {

        // Go to the create-post page and wait for the title input to appear
        openCreateEntity()
        WaitUtils.until(driver) { titleInput.isDisplayed }

        // Enter the title
        titleInput.sendKeys(post.title)

        // Select status from dropdown
        statusInput.click()
        statusInput.sendKeys(post.status.name)
        val statusOptionXpath = "//div[contains(@id,'react-select') and contains(@class,'option') and text()='${post.status.name}']"
        val statusOption = WaitUtils.until(driver,
            org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath(statusOptionXpath)))
        statusOption.click()

        // Toggle published checkbox only if it doesn’t match desired state
        if (post.published xor !publishedCheckbox.isSelected) {
            publishedCheckbox.click()
        }

        // Select publisher from dropdown
        publisherInput.click()
        publisherInput.sendKeys(post.publisherName)
        val publisherOptionXpath = "//div[contains(@id,'react-select') and contains(@class,'option') and text()='${post.publisherName}']"
        val publisherOption = WaitUtils.until(driver,
            ExpectedConditions.elementToBeClickable(
                By.xpath(publisherOptionXpath)))
        publisherOption.click()

        // Submit the form
        submitBtn.click()
    }
}