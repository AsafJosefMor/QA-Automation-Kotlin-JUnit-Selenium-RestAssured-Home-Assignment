package aura.automation.utils

/**
 * Centralized definitions of UI and API route constants for the Aura platform.
 *
 * All paths are relative to the application’s host and assume an admin-context.
 */
object ApiRoutes {

    // Base segments for UI navigation

    /** Root path for the admin interface. */
    const val ADMIN_ROOT = "/admin"

    /** Path segment for resource-related pages under the admin interface. */
    const val RESOURCES_ROOT ="/resources"

    // Post resource UI routes

    /** Path segment representing the Post resource. */
    const val POST_ROOT = "/Post"

    /** Full URL for the “Create Post” UI action. */
    const val POST_CREATE = "${ADMIN_ROOT}${RESOURCES_ROOT}${POST_ROOT}/actions/new"

    /** URL template for the “Edit Post” UI page. */
    const val POST_EDIT = "${ADMIN_ROOT}${RESOURCES_ROOT}${POST_ROOT}/records/%d/edit"

    /** URL for listing Posts in descending creation order via the UI. */
    const val POST_LIST_ORDERED = "${ADMIN_ROOT}${RESOURCES_ROOT}${POST_ROOT}?direction=desc&sortBy=createdAt"


    // Publisher resource UI routes

    /** Suffix for the “Create Publisher” UI action. */
    const val PUBLISHER_CREATE_END = "/Publisher/actions/new"

    /** Full URL for the “Create Publisher” UI page. */
    const val PUBLISHER_CREATE = "${ADMIN_ROOT}${RESOURCES_ROOT}${PUBLISHER_CREATE_END}"


    // Base segment for API endpoints

    /** Root path for all API calls under the admin interface. */
    const val API_ROOT = "/api"

    // API routes

    /** Full API endpoint for creating a Publisher. */
    const val PUBLISHER_CREATE_API = "${ADMIN_ROOT}${API_ROOT}${RESOURCES_ROOT}${PUBLISHER_CREATE_END}"

    /** Full API endpoint for creating a Post. */
    const val POST_CREATE_API = "${ADMIN_ROOT}${API_ROOT}${RESOURCES_ROOT}/Post/actions/new"
}