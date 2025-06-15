package aura.automation.api

import io.restassured.RestAssured
import aura.automation.utils.ApiRoutes
import aura.automation.utils.ApiJsonPaths
import aura.automation.utils.JsonField

/**
 * Contains API operations related to Publisher entities on the test platform.
 */
object PublisherApi {
    /**
     * Creates a new publisher record.
     *
     * @param email The email address of the publisher to create.
     * @return      The ID of the newly created publisher.
     * @throws AssertionError if the API does not return HTTP 200.
     */
    fun createPublisher(email: String): Int {
        // Build and send a multipart/form-data POST request with the email field
        val res = RestAssured.given()
            .multiPart(JsonField.EMAIL.key, email)
            .post(ApiRoutes.PUBLISHER_CREATE_API)
            .then().statusCode(200)
            .extract().jsonPath()

        // Parse the JSON and return the new record’s ID
        return res.getInt(ApiJsonPaths.RECORD_ID)
    }
}
