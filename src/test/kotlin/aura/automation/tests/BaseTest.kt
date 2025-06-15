package aura.automation.tests

import aura.automation.utils.AuthConstants
import aura.automation.utils.ConfigKey
import aura.automation.utils.ConfigLoader
import io.github.bonigarcia.wdm.WebDriverManager
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.Cookie
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.devtools.DevTools
import java.net.URI
import java.time.Duration

/**
 * BaseTest sets up the shared test environment for API and UI tests.
 *
 * @property driver        The ChromeDriver instance for UI interactions.
 * @property sessionCookie The authentication cookie used for API and UI sessions.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTest {

    /** Selenium WebDriver for UI tests. */
    protected lateinit var driver: ChromeDriver

    /** Session cookie obtained from the API login flow. */
    private lateinit var sessionCookie: Cookie

    /**
     * Runs once before all tests:
     * 1. Configure RestAssured base URI.
     * 2. Perform API login to retrieve the session cookie.
     * 3. Configure RestAssured to include the session cookie in all requests.
     */
    @BeforeAll
    fun globalSetup() {
        // Point RestAssured to the application under host:port
        RestAssured.baseURI = ConfigLoader.get(ConfigKey.BASE_URL.key)

        // Perform API login to retrieve session cookie
        // Config Rest-Assured not to follow redirects (Set-Cookie is on the redirect response):
        val loginRes = RestAssured.given()
            .redirects().follow(false)
            .formParam("email", ConfigLoader.get(ConfigKey.USERNAME.key))
            .formParam("password", ConfigLoader.get(ConfigKey.PASSWORD.key))
            .post(AuthConstants.LOGIN_ENDPOINT)

        // Sanity check
        if (loginRes.statusCode !in listOf(200, 302)) {
            error("Login failed, expected 200 or 302 but got ${loginRes.statusCode}")
        }

        // Extract the cookie from that 302 response
        val raw = loginRes.detailedCookie(AuthConstants.SESSION_COOKIE_NAME)
            ?: error("No session cookie '${AuthConstants.SESSION_COOKIE_NAME}' in login response")

        // Build a Selenium-compatible Cookie for UI tests
        sessionCookie = Cookie.Builder(raw.name, raw.value)
            .domain(URI(ConfigLoader.get(ConfigKey.BASE_URL.key)).host)
            .path("/")
            .isHttpOnly(true)
            .build()

        // Configure RestAssured to include the session cookie in subsequent API calls
        RestAssured.requestSpecification = RestAssured.given()
            .baseUri(RestAssured.baseURI)
            .cookie(sessionCookie.name, sessionCookie.value)
    }

    /**
     * Runs before each test:
     * 1. Initialize ChromeDriver.
     * 2. Navigate to the base URL.
     * 3. Add the session cookie to the browser.
     * 4. Maximize the window.
     */
    @BeforeEach
    fun setUp() {
        // Prepare the ChromeDriver binary
        WebDriverManager.chromedriver().setup()
        // Launch a new browser session
        driver = ChromeDriver()

        // Disable implicit waits; we rely on explicit FluentWaits
        driver.manage().timeouts().implicitlyWait(Duration.ZERO)
        // Navigate to the application home page
        driver.get(ConfigLoader.get(ConfigKey.BASE_URL.key))
        // Inject the authentication cookie into the browser session
        driver.manage().addCookie(sessionCookie)
        // Maximize the browser window for consistency
        driver.manage().window().maximize()
}

    /**
     * Runs after each test to clean up the browser session.
     */
    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}