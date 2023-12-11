package br.edu.ifsp.scl.tasks.data.repository

import br.edu.ifsp.scl.tasks.data.datasource.TasksDao
import br.edu.ifsp.scl.tasks.data.entity.TaskEntity
import br.edu.ifsp.scl.tasks.domain.model.Task
import br.edu.ifsp.scl.tasks.domain.usecase.TaskControlUseCase
import br.edu.ifsp.scl.tasks.domain.utils.MBResult

class TasksRepositoryImpl(private val tasksDao: TasksDao) : TaskControlUseCase {

    override suspend fun insert(task: Task) {
        tasksDao.insert(toTaskEntity(task))
    }

    override suspend fun update(task: Task) {
        tasksDao.update(toTaskEntity(task))
    }

    override suspend fun delete(task: Task) {
        tasksDao.delete(toTaskEntity(task))
    }

    private fun toTaskEntity(task: Task): TaskEntity {
        return TaskEntity(task.id, task.name, task.writer, task.synopsis, task.status)
    }

    override suspend fun getAllTasks(): MBResult<List<Task>, String> {
        val response = tasksDao.getAllTasks()
        return if (response != null)
            MBResult.Success(toDomain(response))
        else MBResult.Error("Não há tarefas")
    }

    private fun toDomain(tasks: List<TaskEntity>): List<Task> {
        return tasks.map { Task(it.name, it.writer, it.status, it.synopsis, it.id) }
    }

    override suspend fun getTaskById(id: Int): MBResult<Task, String> {
        val response = tasksDao.getTaskById(id)
        return if (response != null) {
            MBResult.Success(response)
        } else {
            MBResult.Error("Tarefa não localizada")
        }
    }
}