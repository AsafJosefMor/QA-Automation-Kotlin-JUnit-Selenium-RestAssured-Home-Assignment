
package aura.automation.utils

/**
 * Holds JSON path expressions used to extract values from API response payloads.
 */
object ApiJsonPaths {

    /**
     * JSON path to the created entity’s record ID field.
     * Used to retrieve the `id` of a newly created record from the API response.
     */
    const val RECORD_ID    = "record.id"
}
