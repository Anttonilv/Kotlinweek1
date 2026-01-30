package com.example.kotlinweek1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinweek1.model.Task
import com.example.kotlinweek1.viewmodel.TaskViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {
    val tasks by vm.tasks.collectAsState()
    var newTitle by remember { mutableStateOf("") }

    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New task") }
            )
            Button(onClick = {
                if (newTitle.isNotBlank()) {
                    vm.addTask(vm.createTaskFromTitle(newTitle))
                    newTitle = ""
                }
            }) { Text("Add") }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(onClick = { vm.sortByDueDate() }) {
            Text("Sort by due date")
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks, key = { it.id }) { task ->
                Card(onClick = { selectedTask = task }) {
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
                        TextButton(onClick = { vm.removeTask(task.id) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }

    selectedTask?.let {
        DetailDialog(
            task = it,
            onDismiss = { selectedTask = null },
            onSave = { updated ->
                vm.updateTask(updated)
                selectedTask = null
            },
            onDelete = { id ->
                vm.removeTask(id)
                selectedTask = null
            }
        )
    }
}

