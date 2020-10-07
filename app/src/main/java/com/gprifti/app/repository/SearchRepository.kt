package com.gprifti.app.repository

import com.gprifti.app.api.RetrofitInstance
import com.gprifti.app.db.TaskTable
import com.gprifti.app.db.TaskDatabase
import com.gprifti.app.util.Constants.Companion.RESPONSE_FORMAT

class SearchRepository(private val db: TaskDatabase) {

    suspend fun search(query: String) = RetrofitInstance.API.search(query, RESPONSE_FORMAT)

    suspend fun insertTask(task: TaskTable) = db.getTaskDao().insert(task)

    suspend fun readAllTask(search: String?) =
        db.getTaskDao().readAllTask(search)

    suspend fun readActiveArchivedTask(search: String?, status: String?) =
        db.getTaskDao().readActiveArchivedTask(search, status)
}