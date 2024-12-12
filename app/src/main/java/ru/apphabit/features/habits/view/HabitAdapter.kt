package ru.apphabit.features.habits.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.Habit


class HabitAdapter(
    private val habits: List<Habit?>,
    val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<HabitAdapter.HabitHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_habit, parent,false)
        return HabitHolder(view)
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        val habit = habits[position]!!
        holder.bind(habit.id, habit.title, habit.description, habit.image, habit.categoryId)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    inner class HabitHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val titleText: TextView = itemView.findViewById(R.id.habit_title)
        val descriptionText: TextView = itemView.findViewById(R.id.habit_description)
        val imageView: ImageView = itemView.findViewById(R.id.habit_image)
        val categoryIdText: TextView = itemView.findViewById(R.id.habit_category_text)

        fun bind(id: Int, title: String, description: String, image: String, categoryId: Int) {
            titleText.text = title
            descriptionText.text = description

            when (categoryId) {
                7 -> imageView.setImageResource(R.drawable.ic_intelligence)
                2 -> imageView.setImageResource(R.drawable.ic_intelligence)
                3 -> imageView.setImageResource(R.drawable.ic_intelligence)
                else -> imageView.setImageResource(R.drawable.ic_intelligence)
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
