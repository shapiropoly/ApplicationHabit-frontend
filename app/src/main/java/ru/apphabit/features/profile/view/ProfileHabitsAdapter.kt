package ru.apphabit.features.profile.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.checkup.model.HabitWithCheckUp


class ProfileHabitsAdapter (
    private var habitsWithCheckUp: MutableList<HabitWithCheckUp?> = mutableListOf(),
    val fragmentManager: FragmentManager,
    private val onHabitClick: (Int?) -> Unit):
    RecyclerView.Adapter<ProfileHabitsAdapter.HabitViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit_in_profile, parent, false)
        return HabitViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habitsWithCheckUp.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habitWithCheckUp = habitsWithCheckUp[position]!!
        holder.bind(habitWithCheckUp)
    }


    inner class HabitViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.profile_habit_title)
        private val frequencyTextView: TextView = itemView.findViewById(R.id.profile_habit_frequency)
        val buttonDeleteHabit: ImageView = itemView.findViewById(R.id.button_profile_delete_habit)

        fun bind(item: HabitWithCheckUp) {
            titleTextView.text = item.habit.title
//            frequencyTextView.text = item.checkUp.frequency.toString()

            buttonDeleteHabit.setOnClickListener {
                // TODO: добавить удаление привычки по нажатию
            }
        }
    }
}
