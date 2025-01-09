package ru.apphabit.features.checkup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
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

class CheckUpAdapter(
    private var checkUps: MutableList<CheckUp?> = mutableListOf(),
    private val onCheckBoxClicked: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<CheckUpAdapter.CheckUpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckUpViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_check_up, parent, false)
        return CheckUpViewHolder(view)
    }

    override fun getItemCount(): Int {
        return checkUps.size
    }

    override fun onBindViewHolder(holder: CheckUpViewHolder, position: Int) {
        val checkUp = checkUps[position]
        holder.bind(checkUp, onCheckBoxClicked)
    }

    inner class CheckUpViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
//        private val habitTextView: TextView = itemView.findViewById(R.id.habit_name)
//        private val checkBox: CheckBox = itemView.findViewById(R.id.habit_completed_checkbox)

        fun bind(
            checkUp: CheckUp?,
            onCheckBoxClicked: (Int, Boolean) -> Unit
        ) {
//            habitTextView.text = checkUp.checkUp.habitName
//            checkBox.isChecked = checkUp.checkUp.isCompleted
//            checkBox.setOnCheckedChangeListener { _, isChecked ->
//                onCheckBoxClicked(checkUp.checkUp.id, isChecked)
//            }
        }
    }
}
