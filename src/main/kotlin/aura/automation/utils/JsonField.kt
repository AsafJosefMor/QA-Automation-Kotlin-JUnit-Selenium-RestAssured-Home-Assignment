package aura.automation.utils

/**
 * Enumerates the JSON field keys used in API requests and multipart form data.
 *
 * @property key The exact field name as expected by the API.
 */
enum class JsonField(val key: String) {
    TITLE("title"),
    STATUS("status"),
    PUBLISHED("published"),
    PUBLISHER("publisher"),
    EMAIL("email")
}
