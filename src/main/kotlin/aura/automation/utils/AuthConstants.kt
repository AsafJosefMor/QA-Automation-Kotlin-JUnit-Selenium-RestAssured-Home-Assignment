/*
 * AuthConstants
 * Authentication endpoint and cookie name constants.
 */
package aura.automation.utils

/**
 * Holds constants used for authentication-related operations in the Aura platform.
 */
object AuthConstants {

    /** Relative path to the admin login endpoint. */
    const val LOGIN_ENDPOINT = "/admin/login"

    /** Name of the session cookie used by the server to track authenticated admin sessions. */
    const val SESSION_COOKIE_NAME = "adminjs"
}