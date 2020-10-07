package com.gprifti.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gprifti.app.models.Task

@Entity(indices = [Index(value = ["project_name", "task_name", "filter_type"], unique = true)])
data class TaskTable(

    @ColumnInfo(name = "project_name") override var projectName: String?,

    @ColumnInfo(name = "task_name") override var taskName: String?,

    @ColumnInfo(name = "filter_type") override var status: Int?) : Task() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}