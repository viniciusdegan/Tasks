package br.edu.ifsp.scl.tasks.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.tasks.data.entity.TaskEntity
import br.edu.ifsp.scl.tasks.domain.model.Task

@Dao
interface TasksDao {
    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("SELECT * FROM taskentity ORDER BY name")
    suspend fun getAllTasks(): List<TaskEntity>?

    @Query("SELECT * FROM taskentity WHERE id=:id")
    suspend fun getTaskById(id: Int): Task?
}