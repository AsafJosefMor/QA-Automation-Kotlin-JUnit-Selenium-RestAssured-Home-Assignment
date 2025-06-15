/*
 * ConfigLoader
 * Loads configuration properties with overrides.
 */
package aura.automation.utils

import java.util.*

/**
 * Utility to load configuration properties from the classpath and environment,
 * supporting overrides via system properties or environment variables.
 *
 * Loading precedence:
 *  1. Java system property (`-D<key>=<value>`)
 *  2. Environment variable (`<KEY>`), where dots in `key` are replaced by `_` and uppercased
 *  3. `test.properties` on the classpath
 *
 * Throws an error if the requested key is missing from all sources.
 */
object ConfigLoader {

    // Load `test.properties` once, from test resources on the classpath
    private val props = Properties().apply {

        // Attempt to open the properties file as a resource stream
        val stream = Thread.currentThread()
            .contextClassLoader
            .getResourceAsStream("test.properties")
            ?: error("Could not load test.properties from classpath")

        // Populate the Properties object
        load(stream)
    }

    /**
     * Retrieves the configuration value for the given key.
     *
     * The lookup order is:
     * 1. System property (`-D<key>=...`)
     * 2. Environment variable (`<KEY>`, derived by uppercasing and replacing '.' with '_')
     * 3. `test.properties` entry
     *
     * @param key The property name (e.g., `"base.url"`).
     * @return The resolved value as a String.
     * @throws IllegalStateException if the key is not found in any source.
     */
    fun get(key: String): String =

        // 1. Check for system property override
        System.getProperty(key)
            // 2. Fallback to environment variable
            ?: System.getenv(key.uppercase().replace('.', '_'))
            // 3. Fallback to test.properties
            ?: props.getProperty(key)
            // 4. Neither source provided this key — fail fast
            ?: error("Missing config: $key")

    /**
     * Retrieves the configuration value as an integer.
     *
     * @param key The property name whose value should be parsed as Int.
     * @return The integer value of the property.
     * @throws NumberFormatException if the resolved value is not a valid integer.
     */
    fun getInt(key: String): Int = get(key).toInt()
}
