package ru.apphabit.features.collections.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.model.Category


class HomeHabitsAdapter(
    private var habits: List<Habit?>,
    val fragmentManager: FragmentManager,
    val categoriesHabits: Map<Int, String?>
)
    : RecyclerView.Adapter<HomeHabitsAdapter.HomeHabitHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHabitHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_for_home_habit, parent,false)
        return HomeHabitHolder(view)
    }

    override fun onBindViewHolder(holder: HomeHabitHolder, position: Int) {
        val habit = habits[position]!!
        val category = categoriesHabits[habit.categoryId]
        holder.bind(habit.title, category.toString())
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    inner class HomeHabitHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private val titleText: TextView = itemView.findViewById(R.id.habit_for_home_title)
        private val categoryText: TextView = itemView.findViewById(R.id.habit_for_home_category_text)

        fun bind(title: String, category: String) {
            titleText.text = title
            categoryText.text = category

            itemView.setOnClickListener {
                fragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_main, HomeFragment.newInstance())
                    commit()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHabits(newHabits: List<Habit?>) {
        habits = newHabits
        notifyDataSetChanged()
    }
}

