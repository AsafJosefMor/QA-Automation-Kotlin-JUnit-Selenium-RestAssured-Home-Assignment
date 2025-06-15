/*
 * ConfigKey
 * Enum of keys in test.properties.
 */
package aura.automation.utils

/**
 * Defines the keys for configuration properties loaded from `test.properties`.
 *
 * @property key The exact property name used in the properties file.
 */
enum class ConfigKey(val key: String) {

    /** Base URL of the application under test. */
    BASE_URL("base.url"),

    /** Login username property key. */
    USERNAME("login.username"),

    /** Login password property key. */
    PASSWORD("login.password"),

    /** Timeout duration in seconds for explicit waits. */
    TIMEOUT_SECONDS("timeout.seconds"),

    /** Polling interval in milliseconds for wait loops. */
    POLLING_MILLIS("polling.millis"),
}
