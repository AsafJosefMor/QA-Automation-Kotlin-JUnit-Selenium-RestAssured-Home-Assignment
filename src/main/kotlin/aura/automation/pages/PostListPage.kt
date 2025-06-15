package aura.automation.pages

import aura.automation.utils.ApiRoutes
import aura.automation.utils.ConfigKey
import aura.automation.utils.ConfigLoader
import aura.automation.utils.WaitUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

/**
 * Page Object for the “Post List” screen, showing the most recently created posts.
 *
 * Inherits common navigation and load-waiting behavior from BasePage.
 *
 * @constructor Initializes the page’s WebElement fields.
 * @param driver The Selenium WebDriver instance.
 */
class PostListPage(driver: WebDriver): BasePage(driver) {
    init { PageFactory.initElements(driver, this) }

    /** CSS selector for the ID cell of the most recently created post. */
    val lastCreatedPostSelector: String = "tbody[data-css='Post-table-body'] tr:first-child section[data-testid='property-list-id']"

    /** CSS selector for the status cell of the most recently created post. */
    val lastCreatedPostStatus: String = "tbody[data-css='Post-table-body'] tr:first-child section[data-testid='property-list-status'] span"

    /**
     * Navigate to the post list page and wait for the sidebar menu to confirm load.
     */
    fun open() {
        super.openEntityList(ApiRoutes.POST_LIST_ORDERED)
    }

    /**
     * Retrieves the ID of the most recently created post.
     *
     * @return The integer ID parsed from the first row’s ID cell.
     */
    fun lastCreatedPostId(): Int {
        // Ensure the list page is loaded
        open()

        // Wait until the ID cell is visible, then extract and parse its text
        val cell = WaitUtils.until(driver,
            org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector(
                    lastCreatedPostSelector
                )
            )
        )
        return cell.text.trim().toInt()
    }

    /**
     * Retrieves the status text of the most recently created post.
     *
     * @return The status string from the first row’s status cell.
     */
    fun lastCreatedPostStatus(): String {
        // Ensure the list page is loaded
        open()

        // Wait until the status cell is visible, then extract its text
        val statusCell = WaitUtils.until(driver,
            org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector(
                    lastCreatedPostStatus
                )
            )
        )
        return statusCell.text.trim()
    }
}