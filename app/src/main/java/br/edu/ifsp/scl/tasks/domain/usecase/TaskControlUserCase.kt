package br.edu.ifsp.scl.tasks.domain.usecase

import br.edu.ifsp.scl.tasks.domain.model.Task
import br.edu.ifsp.scl.tasks.domain.utils.MBResult

interface TaskControlUseCase {
    suspend fun insert(task: Task)

    suspend fun update(task: Task)

    suspend fun delete(task: Task)

    suspend fun getAllTasks(): MBResult<List<Task>, String>

    suspend fun getTaskById(id: Int): MBResult<Task, String>
}