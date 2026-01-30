package com.example.kotlinweek1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kotlinweek1.view.HomeScreen
import com.example.kotlinweek1.theme.KotlinWeek1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KotlinWeek1Theme {
                HomeScreen()
            }
        }
    }
}
