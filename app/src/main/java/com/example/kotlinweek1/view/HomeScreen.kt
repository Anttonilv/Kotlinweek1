package com.example.kotlinweek1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinweek1.model.Task
import com.example.kotlinweek1.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(vm: TaskViewModel) {
    val tasks by vm.tasks.collectAsState()

    var showAdd by remember { mutableStateOf(false) }
    var editTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAdd = true }) { Text("+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            Text("Tasks", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tasks, key = { it.id }) { task ->
                    Card(onClick = { editTask = task }) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { vm.toggleDone(task.id) }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(task.title, modifier = Modifier.weight(1f))
                            TextButton(onClick = { vm.removeTask(task.id) }) { Text("Delete") }
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        AddEditTaskDialog(
            initial = null,
            onDismiss = { showAdd = false },
            onSave = { draft ->
                // luodaan id ViewModelissa ja kopioidaan muut kentät
                val created = vm.createTaskFromTitle(draft.title).copy(
                    description = draft.description,
                    dueDate = draft.dueDate
                )
                vm.addTask(created)
                showAdd = false
            }
        )
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


