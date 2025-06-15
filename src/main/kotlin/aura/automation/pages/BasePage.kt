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
 * BasePage for any resource page we visit after login.
 *
 * Provides common navigation and load-waiting utilities for pages that share
 * the sidebar resources menu.
 *
 * @property driver      The Selenium WebDriver instance used to interact with the page.
 * @property sideMenu    Sidebar menu element used to verify that pages have loaded.
 * @property baseUrl     The base URL of the application under test, from configuration.
 */
abstract class BasePage(protected val driver: WebDriver) {

    // Sidebar menu element locator; used to confirm page load.
    @FindBy(css = "section[data-css=\"sidebar-resources\"] ul")
    protected lateinit var sideMenu: WebElement

    // Base URL for all navigation actions, loaded from test properties.
    protected open val baseUrl: String = ConfigLoader.get(ConfigKey.BASE_URL.key)

    init {
        PageFactory.initElements(driver, this)
    }

    /**
     * Navigate directly to the entity-creation page.
     *
     * @param entityCreate Path fragment for the creation endpoint (e.g. "/posts/create").
     */
    fun openCreateEntity(entityCreate: String) {
        driver.get("$baseUrl$entityCreate")
    }


    /**
     * Navigate to the entity-list page and wait until the sidebar is visible.
     *
     * @param entityList Path fragment for the list endpoint (e.g. "/posts").
     */
    fun openEntityList(entityList: String) {
        driver.get("$baseUrl$entityList")
        WaitUtils.until(driver) {
            sideMenu.isDisplayed
        }
    }

    /**
     * Navigate to the entity-edit page and wait for a specific element to appear.
     *
     * @param entityEdit        Path fragment for the edit endpoint with a format specifier
     *                          (e.g. "/posts/%d/edit").
     * @param entityId          The ID of the entity to edit.
     * @param displayWaitElement A page-specific element whose visibility indicates the page is loaded.
     */
    fun openEditEntity(entityEdit: String, entityId: Int, displayWaitElement: WebElement) {
        driver.get("$baseUrl${String.format(entityEdit, entityId)}")
        WaitUtils.until(driver) {
            displayWaitElement.isDisplayed
        }
    }
}