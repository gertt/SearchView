package com.gprifti.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskTable::class], version = 5, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db.db"

            ).fallbackToDestructiveMigration()
                .build()
    }
}