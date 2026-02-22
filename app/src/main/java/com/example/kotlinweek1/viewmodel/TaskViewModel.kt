package com.example.kotlinweek1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kotlinweek1.data.repository.TaskRepository
import com.example.kotlinweek1.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(
    private val repo: TaskRepository
) : ViewModel() {

    // UI kuuntelee tätä collectAsState():lla
    val tasks: StateFlow<List<Task>> =
        repo.observeTasks().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addTask(task: Task) {
        viewModelScope.launch { repo.add(task) }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { repo.update(task) }
    }

    fun removeTask(id: Int) {
        viewModelScope.launch { repo.remove(id) }
    }

    fun toggleDone(id: Int) {
        val current = tasks.value.firstOrNull { it.id == id } ?: return
        updateTask(current.copy(done = !current.done))
    }

    fun sortByDueDate() {
        // Roomin query jo lajittelee dueDate ASC.
        // Jos haluat eri järjestyksen, tee DAO:hon toinen query.
    }

    fun createTaskFromTitle(title: String): Task {
        return Task(
            id = 0, // Room generoi id:n
            title = title.trim(),
            description = "",
            priority = 1,
            dueDate = LocalDate.now().plusDays(7),
            done = false
        )
    }
}

/** Factory, jotta ViewModel saa repositoryn */
class TaskViewModelFactory(
    private val repo: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}