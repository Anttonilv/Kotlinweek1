package com.example.kotlinweek1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinweek1.model.Task
import com.example.kotlinweek1.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun CalendarScreen(vm: TaskViewModel) {
    val tasks by vm.tasks.collectAsState()

    // ryhmittele päivämäärän mukaan
    val grouped = remember(tasks) { tasks.groupBy { it.dueDate }.toSortedMap() }

    var editTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Calendar", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            grouped.forEach { (date, dayTasks) ->
                item {
                    Text(date.toString(), style = MaterialTheme.typography.titleMedium)
                }
                items(dayTasks, key = { it.id }) { task ->
                    Card(onClick = { editTask = task }) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(task.title)
                            Text(if (task.done) "DONE" else "TODO")
                        }
                    }
                }
            }
        }
    }

    editTask?.let { t ->
        AddEditTaskDialog(
            initial = t,
            onDismiss = { editTask = null },
            onSave = { updated ->
                vm.updateTask(updated)
                editTask = null
            },
            onDelete = { id ->
                vm.removeTask(id)
                editTask = null
            }
        )
    }
}
