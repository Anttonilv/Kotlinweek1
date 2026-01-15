package com.example.kotlinweek1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinweek1.domain.*
import java.time.LocalDate

@Composable
fun HomeScreen() {
    var tasks by remember { mutableStateOf(TaskMock.tasks) }
    var showOnlyDone by remember { mutableStateOf(false) }

    val visibleTasks = if (showOnlyDone) filterByDone(tasks, true) else tasks

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val id = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                tasks = addTask(
                    tasks,
                    Task(
                        id = id,
                        title = "Uusi tehtävä #$id",
                        description = "Lisätty napista",
                        priority = 1,
                        dueDate = LocalDate.now().plusDays(7),
                        done = false
                    )
                )
            }) { Text("Add") }

            Button(onClick = {
                visibleTasks.firstOrNull()?.let { tasks = toggleDone(tasks, it.id) }
            }) { Text("Toggle") }
        }

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { showOnlyDone = !showOnlyDone }) {
                Text(if (showOnlyDone) "Show all" else "Only done")
            }
            OutlinedButton(onClick = { tasks = sortByDueDate(tasks) }) {
                Text("Sort")
            }
        }

        Spacer(Modifier.height(16.dp))

        visibleTasks.forEach {
            Text("• ${it.title} (${if (it.done) "DONE" else "TODO"})")
        }
    }
}
