/*
 * PostStatus
 * Allowed values for a Post's status.
 */
package aura.automation.models

/**
 * Represents the various life-cycle states of a Post.
 */
enum class PostStatus {
    /** The post is active and should be displayed normally. */
    ACTIVE,

    /** The post has been removed or archived and should not be shown. */
    REMOVED
}
