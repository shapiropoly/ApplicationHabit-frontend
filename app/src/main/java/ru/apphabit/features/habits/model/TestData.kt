package ru.apphabit.features.habits.model

class TestData {
    lateinit var testListHabits: List<Habit>

    val habit1 = Habit(
        id = 1,
        title = "Занятия спортом",
        description = "Ежедневная пробежка по утрам",
        image = "https://example.com/images/running.png",
        categoryId = 7
    )

    val habit2 = Habit(
        id = 2,
        title = "Выпивать стакан воды",
        description = "Ежедневная выпивать стакан воды",
        image = "https://example.com/images/drinkwater.png",
        categoryId = 3
    )

    fun main() {
        testListHabits = listOf(habit1, habit2)

        testListHabits.forEach {
            println("Habit: ${it.title}, Description: ${it.description}")
        }
    }
}