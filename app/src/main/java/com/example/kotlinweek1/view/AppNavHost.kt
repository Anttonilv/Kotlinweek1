package com.example.kotlinweek1.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kotlinweek1.viewmodel.TaskViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    vm: TaskViewModel,
    darkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                currentRoute = navController.currentBackStackEntry?.destination?.route,
                onHome = { navController.navigate(ROUTE_HOME) { navOptionsForTabs() } },
                onCalendar = { navController.navigate(ROUTE_CALENDAR) { navOptionsForTabs() } },
                onSettings = { navController.navigate(ROUTE_SETTINGS) { navOptionsForTabs() } }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_HOME) {
                HomeScreen(vm = vm)
            }
            composable(ROUTE_CALENDAR) {
                CalendarScreen(vm = vm)
            }
            composable(ROUTE_SETTINGS) {
                SettingsScreen(
                    darkTheme = darkTheme,
                    onToggleTheme = onToggleTheme
                )
            }
        }
    }
}

/** Tab-navigoinnissa pidetään backstack siistinä */
private fun androidx.navigation.NavOptionsBuilder.navOptionsForTabs() {
    popUpTo(ROUTE_HOME) { saveState = true }
    launchSingleTop = true
    restoreState = true
}

@Composable
private fun BottomBar(
    currentRoute: String?,
    onHome: () -> Unit,
    onCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == ROUTE_HOME,
            onClick = onHome,
            icon = { },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == ROUTE_CALENDAR,
            onClick = onCalendar,
            icon = { },
            label = { Text("Calendar") }
        )
        NavigationBarItem(
            selected = currentRoute == ROUTE_SETTINGS,
            onClick = onSettings,
            icon = { },
            label = { Text("Settings") }
        )
    }
}
