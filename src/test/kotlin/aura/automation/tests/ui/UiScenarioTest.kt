package aura.automation.tests.ui

import aura.automation.models.Post
import aura.automation.models.PostStatus
import aura.automation.pages.PublisherCreationPage
import aura.automation.pages.PostCreationPage
import aura.automation.pages.PostListPage
import aura.automation.pages.PostEditionPage
import aura.automation.tests.BaseTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.UUID

/**
 * End-to-end UI scenario tests for Post creation, editing, and status validation.
 * - Scenario: Create publisher and post via UI, edit post status, and verify via UI list.
 * - Preconditions: User is logged in (session injected by BaseTest).
 */
class UiScenarioTest : BaseTest() {
    /**
     * Full flow: Create publisher, create post, edit post, validate status.
     */
    @Test
    fun testCreateAndEditPost() {
        // Step 1: Create Publisher entity
        val uuid = UUID.randomUUID().toString().substring(0,8)
        val email = "auto-pub-$uuid@example.com"
        PublisherCreationPage(driver).create(email)

        // Step 2 + 3: Create Post entity, Link to the Publisher created (Status= Active, Published= True)
        val postTitle = "auto-pub-$uuid"
        val newPost = Post(postTitle, PostStatus.ACTIVE, true, email)
        PostCreationPage(driver).create(newPost)

        // Open Post list, get ID of the newest post
        val postListPage = PostListPage(driver)
        val postId = postListPage.lastCreatedPostId()

        // Step 4 + 5: Edit Post - Change status to REMOVE and save
        val postEditionPage = PostEditionPage(driver)
        postEditionPage.editStatus(postId, PostStatus.REMOVED)

        // Step 6: Validate in PostListPage table that status changed to remove
        val updatedStatus = postListPage.lastCreatedPostStatus()
        assertEquals(PostStatus.REMOVED.name, updatedStatus)
    }
}