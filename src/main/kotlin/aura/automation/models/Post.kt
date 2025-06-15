/*
 * Post
 * Data class representing a Post entity.
 */
package aura.automation.models

/**
 * Represents a Post entity used within the Aura platform.
 *
 * @property title         The title of the post.
 * @property status        The current status of the post (e.g., PUBLISHED).
 * @property published     Flag indicating whether the post is published.
 * @property publisherName The display name of the user who created the post.
 */
data class Post(
    val title: String,
    val status: PostStatus,
    val published: Boolean,
    val publisherName: String
)
