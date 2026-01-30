package com.example.kotlinweek1.model

import java.time.LocalDate

object TaskMock {
    val tasks = listOf(
        Task(1, "Osta maitoa", "Kauppareissu", 2, LocalDate.now().plusDays(1), false),
        Task(2, "Kotlin-kertaus", "data class + funktiot", 3, LocalDate.now().plusDays(2), false),
        Task(3, "Lenkki", "30 min", 1, LocalDate.now(), true),
        Task(4, "Siivoa", "Koti kuntoon", 2, LocalDate.now().plusDays(4), false),
        Task(5, "Palauta tehtävä", "GitHub + tag", 3, LocalDate.now().plusDays(3), false)
    )
}
