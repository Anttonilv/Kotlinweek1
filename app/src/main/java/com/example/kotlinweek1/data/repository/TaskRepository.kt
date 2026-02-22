package com.example.kotlinweek1.data.repository

import com.example.kotlinweek1.data.local.TaskDao
import com.example.kotlinweek1.data.model.TaskEntity
import com.example.kotlinweek1.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(
    private val dao: TaskDao
) {
    fun observeTasks(): Flow<List<Task>> =
        dao.observeAll().map { entities -> entities.map { it.toTask() } }

    suspend fun add(task: Task) {
        dao.insert(task.toEntity(idOverride = 0)) // autoGenerate id
    }

    suspend fun update(task: Task) {
        dao.update(task.toEntity(idOverride = task.id))
    }

    suspend fun remove(id: Int) {
        dao.deleteById(id)
    }

    suspend fun getById(id: Int): Task? =
        dao.getById(id)?.toTask()
}

/** MAPPERS */
private fun TaskEntity.toTask(): Task = Task(
    id = id,
    title = title,
    description = description,
    priority = priority,
    dueDate = dueDate,
    done = done
)

private fun Task.toEntity(idOverride: Int): TaskEntity = TaskEntity(
    id = idOverride,
    title = title,
    description = description,
    priority = priority,
    dueDate = dueDate,
    done = done
)