package com.example.kotlinweek1.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.kotlinweek1.model.Task
import java.time.LocalDate

@Composable
fun AddEditTaskDialog(
    initial: Task?,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: ((Int) -> Unit)? = null
) {
    var title by remember(initial) { mutableStateOf(initial?.title ?: "") }
    var description by remember(initial) { mutableStateOf(initial?.description ?: "") }
    var dueDateText by remember(initial) { mutableStateOf(initial?.dueDate?.toString() ?: LocalDate.now().plusDays(7).toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "Add task" else "Edit task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, singleLine = true)
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = dueDateText, onValueChange = { dueDateText = it }, label = { Text("Due date (YYYY-MM-DD)") }, singleLine = true)
            }
        },
        confirmButton = {
            Button(onClick = {
                val parsedDate = runCatching { LocalDate.parse(dueDateText.trim()) }.getOrElse { LocalDate.now().plusDays(7) }
                val base = initial
                val task = if (base == null) {
                    // id ja muut defaultit hoidetaan ViewModelissa createTaskFromTitle + copy
                    Task(
                        id = -1, // korvataan ViewModelissa
                        title = title.trim(),
                        description = description,
                        priority = 1,
                        dueDate = parsedDate,
                        done = false
                    )
                } else {
                    base.copy(
                        title = title.trim(),
                        description = description,
                        dueDate = parsedDate
                    )
                }
                onSave(task)
            }) { Text("Save") }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (initial != null && onDelete != null) {
                    OutlinedButton(onClick = { onDelete(initial.id) }) { Text("Delete") }
                }
                OutlinedButton(onClick = onDismiss) { Text("Cancel") }
            }
        }
    )
}

