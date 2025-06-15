/*
 * WaitUtils
 * Provides explicit wait functionality.
 */
package aura.automation.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.FluentWait
import java.time.Duration

/**
 * Utility for performing FluentWait-based explicit waits in UI tests.
 *
 * Uses configurable timeout and polling intervals to repeatedly evaluate
 * an ExpectedCondition until it is met or the timeout expires.
 */
object WaitUtils {

    // Maximum duration to wait for a condition, loaded (in seconds) from ConfigKey.TIMEOUT_SECONDS
    private val timeout = Duration.ofSeconds(ConfigLoader.getInt(ConfigKey.TIMEOUT_SECONDS.key).toLong())

    // Interval between condition checks, loaded (in milliseconds) from ConfigKey.POLLING_MILLIS
    private val polling = Duration.ofMillis(ConfigLoader.getInt(ConfigKey.POLLING_MILLIS.key).toLong())

    /**
     * Waits until the given condition is satisfied or the configured timeout elapses.
     *
     * @param driver    The WebDriver instance on which to perform the wait.
     * @param condition The Selenium ExpectedCondition to evaluate.
     * @param T         The return type of the condition (e.g., WebElement, Boolean).
     * @return          The result of the condition when it first returns a non-null / non-false value.
     * @throws org.openqa.selenium.TimeoutException if the condition is not met within the configured timeout.
     */
    fun <T> until(driver: WebDriver, condition: ExpectedCondition<T>): T =
        FluentWait(driver)
            .withTimeout(timeout)                           // total wait time
            .pollingEvery(polling)                 // frequency of checks
            .ignoring(Exception::class.java) // ignore any exceptions during polling
            .until(condition)                       // evaluate the condition
}
