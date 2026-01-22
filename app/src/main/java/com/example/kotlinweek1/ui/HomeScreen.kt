package com.example.kotlinweek1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {
    var newTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New task title") },
                singleLine = true
            )

            Button(onClick = {
                val title = newTitle.trim()
                if (title.isNotEmpty()) {
                    vm.addTask(vm.createTaskFromTitle(title))
                    newTitle = ""
                }
            }) { Text("Add") }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { vm.showAll() }
            ) { Text("Show all") }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { vm.filterByDone(true) }
            ) { Text("Done") }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { vm.sortByDueDate() }
            ) { Text("Sort") }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = vm.tasks, key = { it.id }) { task ->
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { vm.toggleDone(task.id) }
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            modifier = Modifier.weight(1f),
                            text = task.title
                        )

                        TextButton(onClick = { vm.removeTask(task.id) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
