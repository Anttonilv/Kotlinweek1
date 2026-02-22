package com.example.kotlinweek1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.kotlinweek1.data.local.AppDatabase
import com.example.kotlinweek1.data.repository.TaskRepository
import com.example.kotlinweek1.theme.KotlinWeek1Theme
import com.example.kotlinweek1.view.AppNavHost
import com.example.kotlinweek1.viewmodel.TaskViewModel
import com.example.kotlinweek1.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // (Valinnainen) Teeman vaihto Settingsistä
            var darkTheme by rememberSaveable { mutableStateOf(false) }

            KotlinWeek1Theme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                val context = LocalContext.current.applicationContext
                val db = remember { AppDatabase.getInstance(context) }
                val repo = remember { TaskRepository(db.taskDao()) }
                val vm: TaskViewModel = viewModel(factory = TaskViewModelFactory(repo))

                AppNavHost(
                    navController = navController,
                    vm = vm,
                    darkTheme = darkTheme,
                    onToggleTheme = { darkTheme = it }
                )
            }
        }
    }
}
