package com.gprifti.app.models

import com.google.gson.annotations.SerializedName

data class ProjectsItemModel(

    @field:SerializedName("share_token_enabled")
    val shareTokenEnabled: Boolean? = null,

    @field:SerializedName("notes")
    val notes: Any? = null,

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("share_mode")
    val shareMode: Int? = null,

    @field:SerializedName("team_id")
    val teamId: Int? = null,

    @field:SerializedName("type")
    val type: Any? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("mail_token")
    val mailToken: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("tasks_active_count")
    val tasksActiveCount: Int? = null,

    @field:SerializedName("tasks_trash_count")
    val tasksTrashCount: Int? = null,

    @field:SerializedName("share_token")
    val shareToken: Any? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("tasks_complete_count")
    val tasksCompleteCount: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("tasks_archive_count")
    val tasksArchiveCount: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null
)