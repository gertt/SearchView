package com.gprifti.app.models

import com.google.gson.annotations.SerializedName

data class TasksItemModel(

    @field:SerializedName("assigned_to_id")
    val assignedToId: Any? = null,

    @field:SerializedName("notes")
    val notes: Any? = null,

    @field:SerializedName("created_by_origin")
    val createdByOrigin: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("flagged")
    val flagged: Any? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("section_id")
    val sectionId: Int? = null,

    @field:SerializedName("total_cl_items_count")
    val totalClItemsCount: Int? = null,

    @field:SerializedName("repeat")
    val repeat: Any? = null,

    @field:SerializedName("attachment_id")
    val attachmentId: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status_updated_at")
    val statusUpdatedAt: String? = null,

    @field:SerializedName("reminder")
    val reminder: Any? = null,

    @field:SerializedName("meta_information")
    val metaInformation: Any? = null,

    @field:SerializedName("priority")
    val priority: Any? = null,

    @field:SerializedName("status_changed_by_id")
    val statusChangedById: Int? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("sequence")
    val sequence: Double? = null,

    @field:SerializedName("completed_at")
    val completedAt: Any? = null,

    @field:SerializedName("closed_cl_items_count")
    val closedClItemsCount: Int? = null,

    @field:SerializedName("due")
    val due: Any? = null,

    @field:SerializedName("comments_count")
    val commentsCount: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_by_id")
    val createdById: Int? = null,

    @field:SerializedName("attachments_count")
    val attachmentsCount: Int? = null,

    @field:SerializedName("repeat_frequency")
    val repeatFrequency: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null
)