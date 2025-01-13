package ru.apphabit.features.collections.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.Habit

class HabitListAdapter(
    private val habits: List<Habit>,
    private val onSelectionChanged: (selectedHabits: List<Habit>) -> Unit
) : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    private val selectedHabits = mutableSetOf<Habit>()

    inner class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.habit_check_box)
        val textView: TextView = view.findViewById(R.id.habit_text)

        fun bind(habit: Habit) {
            textView.text = habit.title
            checkBox.isChecked = selectedHabits.contains(habit)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedHabits.add(habit)
                } else {
                    selectedHabits.remove(habit)
                }
                onSelectionChanged(selectedHabits.toList())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_habits, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int = habits.size

    fun getSelectedHabits(): MutableSet<Habit> = selectedHabits
}
