/*
 * ApiScenarioTest
 * API scenario for creating and editing a post.
 */
package aura.automation.tests.api

import aura.automation.api.PostApi
import aura.automation.api.PublisherApi
import aura.automation.models.PostStatus
import aura.automation.pages.PostEditionPage
import aura.automation.pages.PostListPage
import aura.automation.tests.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

/**
 * End-to-end API scenario tests for Publisher and Post resources.
 * - Scenario: Create publisher and post via API, verify creation and linkage.
 * - Preconditions: API session is valid.
 */
class ApiScenarioTest : BaseTest() {
    /**
     * Full flow: Create publisher via API, create post via API, edit and validate via UI.
     */
    @Test
    fun testApiCreateUiEditPost() {

        // Step 1: API Create a Publisher
        val uuid = UUID.randomUUID().toString().substring(0,8)
        val pubEmail = "api-pub-$uuid@example.com"
        val publisherId = PublisherApi.createPublisher(pubEmail)

        // Step 2 + 3: API Create Post entity, Link to the Publisher created (Status= Active, Published= True)
        val postTitle = "api-title-$uuid"
        val postId = PostApi.createPost(
            title = postTitle,
            status = PostStatus.ACTIVE,
            published = true,
            publisherId = publisherId
        )

        // Step 4 + 5: Edit Post - Change status to REMOVE and save
        val postEditionPage = PostEditionPage(driver)
        postEditionPage.editStatus(postId, PostStatus.REMOVED)

        // Step 6: Validate in PostListPage table that status changed to remove
        val updatedStatus = PostListPage(driver).lastCreatedPostStatus()
        assertEquals(PostStatus.REMOVED.name, updatedStatus)
    }
}
