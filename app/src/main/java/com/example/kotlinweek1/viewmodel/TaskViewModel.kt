package com.example.kotlinweek1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlinweek1.model.Task
import com.example.kotlinweek1.model.TaskMock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        _tasks.value = TaskMock.tasks
    }

    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
    }

    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }

    fun updateTask(updated: Task) {
        _tasks.value = _tasks.value.map {
            if (it.id == updated.id) updated else it
        }
    }

    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { it.dueDate }
    }

    fun createTaskFromTitle(title: String): Task {
        val nextId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        return Task(
            id = nextId,
            title = title,
            description = "",
            priority = 1,
            dueDate = LocalDate.now().plusDays(7),
            done = false
        )
    }
}
