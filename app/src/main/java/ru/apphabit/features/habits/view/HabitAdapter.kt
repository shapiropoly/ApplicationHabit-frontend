package ru.apphabit.features.habits.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.Habit


class HabitAdapter (
    private var habits: List<Habit?>,
    val fragmentManager: FragmentManager,
    private val onHabitClick: (Habit) -> Unit)
    : RecyclerView.Adapter<HabitAdapter.HabitHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_habit, parent,false)
        return HabitHolder(view)
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        val habit = habits[position]!!
        holder.bind(habit)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHabits(newHabits: List<Habit?>) {
        habits = newHabits
        notifyDataSetChanged()
    }

    fun getHabits(): List<Habit?> = habits

    inner class HabitHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val titleText: TextView = itemView.findViewById(R.id.habit_title)
        val descriptionText: TextView = itemView.findViewById(R.id.habit_description)
        val imageView: ImageView = itemView.findViewById(R.id.habit_image)
        val categoryIdText: TextView = itemView.findViewById(R.id.habit_category_text)
        val buttonHabitView: Button = itemView.findViewById(R.id.button_habit_view)

        fun bind(habit: Habit) {
            titleText.text = habit.title
            descriptionText.text = habit.description

            when (habit.categoryId) {
                7 -> imageView.setImageResource(R.drawable.ic_intelligence)
                2 -> imageView.setImageResource(R.drawable.ic_intelligence)
                3 -> imageView.setImageResource(R.drawable.ic_intelligence)
                else -> imageView.setImageResource(R.drawable.ic_intelligence)
            }

            // Обработка нажатия на кнопку "Редактировать"
            buttonHabitView.setOnClickListener {
                onHabitClick(habit)
                fragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, EditHabitFragment.newInstance(habit.id))
                    commit()
                }
            }

//            itemView.setOnClickListener {
//                fragmentManager.beginTransaction().apply {
//                    replace(R.id.flFragment, TrainEditFragment.newInstance(id))
//                    commit()
//                }
//            }
        }
    }
}
