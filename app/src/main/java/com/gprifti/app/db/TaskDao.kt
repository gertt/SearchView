package com.gprifti.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gprifti.app.models.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskTable)

    @Query("SELECT * from TaskTable where (project_name LIKE '%' ||  :search || '%' OR task_name LIKE '%' ||  :search || '%')")
    suspend fun readAllTask(search: String?): List<Task>

    @Query("SELECT * from TaskTable where (project_name LIKE '%' ||  :search || '%' OR task_name LIKE '%' ||  :search || '%')  and  status = :status")
    suspend fun readActiveArchivedTask(search: String?, status: String?): List<Task>

}