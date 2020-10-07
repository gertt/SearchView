package com.gprifti.app.models

import com.google.gson.annotations.SerializedName

data class ResultModel(

    @field:SerializedName("projects")
    val projects: List<ProjectsItemModel?>? = null,

    @field:SerializedName("tasks")
    val tasks: List<TasksItemModel?>? = null,

    @field:SerializedName("sections")
    val sections: List<SectionsItemModel?>? = null
)