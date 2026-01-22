package com.example.kotlinweek1.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kotlinweek1.domain.Task
import com.example.kotlinweek1.domain.TaskMock
import java.time.LocalDate

class TaskViewModel : ViewModel() {

    // Tehtävänannon mukainen: MutableState<List<Task>>
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    // pidetään “master”-lista, jotta suodatus voidaan perua
    private var allTasks: List<Task> = emptyList()

    init {
        allTasks = TaskMock.tasks
        tasks = allTasks
    }

    fun addTask(task: Task) {
        allTasks = allTasks + task
        tasks = allTasks
    }

    fun toggleDone(id: Int) {
        allTasks = allTasks.map { t ->
            if (t.id == id) t.copy(done = !t.done) else t
        }
        tasks = allTasks
    }

    fun removeTask(id: Int) {
        allTasks = allTasks.filterNot { it.id == id }
        tasks = allTasks
    }

    fun filterByDone(done: Boolean) {
        tasks = allTasks.filter { it.done == done }
    }

    fun sortByDueDate() {
        allTasks = allTasks.sortedBy { it.dueDate }
        tasks = allTasks
    }

    fun showAll() {
        tasks = allTasks
    }

    fun createTaskFromTitle(title: String): Task {
        val nextId = (allTasks.maxOfOrNull { it.id } ?: 0) + 1
        return Task(
            id = nextId,
            title = title.trim(),
            description = "",
            priority = 1,
            dueDate = LocalDate.now().plusDays(7),
            done = false
        )
    }
}


