package ru.apphabit.features.checkup.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.checkup.model.CheckUp
import ru.apphabit.features.checkup.model.HabitWithCheckUp
import ru.apphabit.features.habits.model.Category
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.view.EditHabitFragment
import ru.apphabit.features.profile.model.User
import ru.apphabit.features.profile.view.ProfileHabitsAdapter
import java.time.LocalDate

class CheckUpAdapter(
    private var habitsWithCheckUp: MutableList<HabitWithCheckUp?> = mutableListOf(),
    private val onCheckBoxClicked: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<CheckUpAdapter.CheckUpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckUpViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_check_up, parent, false)
        return CheckUpViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habitsWithCheckUp.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CheckUpViewHolder, position: Int) {
        val habitWithCheckUp = habitsWithCheckUp[position]
        holder.bind(habitWithCheckUp, onCheckBoxClicked)
    }

    inner class CheckUpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val habitTextView: TextView = itemView.findViewById(R.id.checkup_card_habit_title)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkup_card_habit_checkbox)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            habitWithCheckUp: HabitWithCheckUp?,
            onCheckBoxClicked: (Int, Boolean) -> Unit
        ) {
            val habit = habitWithCheckUp?.habit
            val checkUp = habitWithCheckUp?.checkUp

            habitTextView.text = habit?.title ?: "Название отсутствует"

            if (checkUp != null) {
                checkBox.isChecked = checkUp.dateCheckUp == LocalDate.now()

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    onCheckBoxClicked(checkUp.id, isChecked)
                }
            } else {
                checkBox.isChecked = false
                checkBox.setOnCheckedChangeListener(null)
            }
        }
    }
}
