package ru.habit_app.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_habit.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_habits)
    }
}