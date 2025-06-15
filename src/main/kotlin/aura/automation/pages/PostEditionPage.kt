package aura.automation.pages

import aura.automation.models.PostStatus
import aura.automation.utils.ApiRoutes
import aura.automation.utils.WaitUtils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions

/**
 * Page Object for the “Edit Post” screen.
 *
 * Extends BasePage to inherit navigation and load-waiting behavior for side-menu.
 *
 * @constructor Initializes the page’s WebElement fields.
 * @param driver The Selenium WebDriver instance.
 */
class PostEditionPage(driver: WebDriver): BasePage(driver) {

    // Dropdown input for selecting post status on the edit form
    @FindBy(xpath = "//*[contains(text(),'Status')]/ancestor::div[1]//input[@role='combobox']")
    private lateinit var statusInput: WebElement

    // Button to submit the edit form
    @FindBy(css = "button[type='submit']")
    private lateinit var submitBtn: WebElement

    init {
        PageFactory.initElements(driver, this)
    }

    /**
     * Navigate to the edit page for a given post ID and wait for the status dropdown to appear.
     *
     * @param postId The ID of the post to edit.
     */
    fun openEditEntity(postId: Int) {
        super.openEditEntity(ApiRoutes.POST_EDIT, postId, statusInput)
    }

    /**
     * Updates the status of an existing post.
     *
     * @param postId    The ID of the post to update.
     * @param newStatus The new status to set (e.g., ACTIVE, REMOVED).
     */
    fun editStatus(postId: Int, newStatus: PostStatus) {

        // Navigate to the edit-post page and wait until the status input is visible
        openEditEntity(postId)

        // Open the status dropdown and type the desired status
        statusInput.click()
        statusInput.sendKeys(newStatus.name)

        // Build an XPath to locate the matching option in the dropdown
        val statusOptionXpath = "//div[contains(@id,'react-select') and contains(@class,'option') and text()='${newStatus.name}']"

        // Wait until that option is clickable, then click it
        val statusOption = WaitUtils.until(driver,
            ExpectedConditions.elementToBeClickable(
                By.xpath(statusOptionXpath))
        )
        statusOption.click()

        // Wait for the Submit button to be clickable, then click to save changes
        WaitUtils.until(driver,
            ExpectedConditions.elementToBeClickable(submitBtn)).click()
    }
}