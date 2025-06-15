package aura.automation.api

import io.restassured.RestAssured
import aura.automation.utils.ApiRoutes
import aura.automation.utils.ApiJsonPaths
import aura.automation.utils.JsonField
import aura.automation.models.PostStatus

/**
 * Contains API operations related to Post entities on the test platform.
 */
object PostApi {
    /**
     * Creates a new post.
     *
     * @param title       The title of the post.
     * @param status      The status of the post (e.g., DRAFT, PUBLISHED).
     * @param published   Whether the post should be marked as published.
     * @param publisherId The ID of the user creating the post.
     * @return            The ID of the newly created post.
     * @throws AssertionError if the API does not return HTTP 200.
     */
    fun createPost(
        title: String,
        status: PostStatus,
        published: Boolean,
        publisherId: Int
    ): Int {
        // Build and send a multipart/form-data POST request
        val response = RestAssured.given()
            .multiPart(JsonField.TITLE.key, title)
            .multiPart(JsonField.STATUS.key, status.name)
            .multiPart(JsonField.PUBLISHED.key, published.toString())
            .multiPart(JsonField.PUBLISHER.key, publisherId.toString())
            .post(ApiRoutes.POST_CREATE_API)

        // Verify we got a 200 OK
        response.then().statusCode(200)

        // Parse response JSON and extract the new record’s ID
        val json = response.jsonPath()
        return json.getInt(ApiJsonPaths.RECORD_ID)
    }
}
