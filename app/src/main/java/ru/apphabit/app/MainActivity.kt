package ru.apphabit.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import ru.apphabit.R
import ru.apphabit.features.checkup.view.CheckUpFragment
import ru.apphabit.features.collections.view.HomeFragment
import ru.apphabit.features.habits.view.HabitsFragment
import ru.apphabit.features.profile.view.ProfileFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(HabitsFragment.newInstance())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        @Suppress("DEPRECATION")
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_habits_list -> setCurrentFragment(HabitsFragment.newInstance())
                R.id.nav_check_up -> setCurrentFragment(CheckUpFragment.newInstance())
//                R.id.nav_home -> setCurrentFragment(HomeFragment.newInstance())
                R.id.nav_profile -> setCurrentFragment(ProfileFragment.newInstance())
            }
            true
        }
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.light_gray)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            commit()
        }
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