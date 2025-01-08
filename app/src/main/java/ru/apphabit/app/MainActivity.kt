package ru.apphabit.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import ru.apphabit.R
import ru.apphabit.features.habits.view.AddHabitFragment
import ru.apphabit.features.habits.view.AllHabitsFragment
import ru.apphabit.features.profile.view.ProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(AllHabitsFragment.newInstance())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        @Suppress("DEPRECATION")
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_habits_list -> setCurrentFragment(AllHabitsFragment.newInstance(), hideBottomNav = false)
//                R.id.nav_check_up -> setCurrentFragment(CheckUpFragment.newInstance())
//                R.id.nav_home -> setCurrentFragment(HomeFragment.newInstance())
                R.id.nav_profile -> setCurrentFragment(ProfileFragment.newInstance(), hideBottomNav = false)
            }
            true
        }
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.light_gray)
    }

    private fun setCurrentFragment(fragment: Fragment, hideBottomNav: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            commit()
        }
        setBottomNavVisibility(!hideBottomNav) // Если hideBottomNav true, скрыть меню
    }

    fun setBottomNavVisibility(visible: Boolean) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        Log.d("MainActivity", "setBottomNavVisibility called with: $visible")
        bottomNav.visibility = if (visible) View.VISIBLE else View.GONE
        bottomNav.requestLayout()
    }
}




//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.check_up)
//
//        // Найти RecyclerView
//        val recyclerView = findViewById<RecyclerView>(R.id.calendarRecyclerView)
//
//        // Установить горизонтальный LinearLayoutManager
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//        // Создать адаптер
//        val dataList = generateDateList() // Ваши данные для календаря
//        val adapter = CalendarAdapter(dataList)
//
//        // Установить адаптер для RecyclerView
//        recyclerView.adapter = adapter
//    }
//
//    // Пример генерации данных
//    private fun generateDateList(): List<CalendarItem> {
//        return listOf(
//            CalendarItem("Пн", "1"),
//            CalendarItem("Вт", "2"),
//            CalendarItem("Ср", "3"),
//            CalendarItem("Чт", "4"),
//            CalendarItem("Пт", "5")
//        )
//    }
//}