# KotlinWeek1 – Week2 (ViewModel + Compose)

## Projektin tavoite
Tässä projektissa laajennettiin Week1-tehtävää. Sovellus näyttää task-listan ja mahdollistaa tehtävien lisäämisen, tilan vaihtamisen (done/todo) ja poistamisen.

## Data model (domain)
Task-malli löytyy paketista `domain`.

### Task
`Task` on data class, jossa kentät:
- id: Int
- title: String
- description: String
- priority: Int
- dueDate: LocalDate
- done: Boolean

Mock-data löytyy tiedostosta `TaskMock.kt`.

## ViewModel (Week2)
Taskien tilanhallinta on siirretty ViewModeliin.

`TaskViewModel` sisältää:
- `tasks` = `mutableStateOf<List<Task>>`
- mock-data alustetaan `init {}` blokissa

ViewModel-funktiot:
- `addTask(task: Task)`
- `toggleDone(id: Int)`
- `removeTask(id: Int)`
- `filterByDone(done: Boolean)`
- `sortByDueDate()`

## UI (Compose)
UI löytyy paketista `ui`.

`HomeScreen`:
- käyttää `viewModel()` saadakseen `TaskViewModel`-instanssin
- näyttää taskit `LazyColumn`-komponentilla
- jokaisella rivillä on:
    - Checkbox (done-tila)
    - taskin title
    - Delete-painike


