package br.edu.ifsp.scl.tasks.framework.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.tasks.data.datasource.TasksDao
import br.edu.ifsp.scl.tasks.data.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        const val TASKS_DATABASE_NAME = "tasks.db"
    }
}