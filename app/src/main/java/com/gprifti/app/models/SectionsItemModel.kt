package com.gprifti.app.models

import com.google.gson.annotations.SerializedName

data class SectionsItemModel(

    @field:SerializedName("indicator")
    val indicator: Int? = null,

    @field:SerializedName("sequence")
    val sequence: Double? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("project_id")
    val projectId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("limit")
    val limit: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("description")
    val description: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status")
    val status: Int? = null
)