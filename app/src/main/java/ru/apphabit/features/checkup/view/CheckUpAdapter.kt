package ru.apphabit.features.checkup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.checkup.model.CheckUp
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.profile.model.User

class CheckUpAdapter (
    private val checkups: List<CheckUp?>,
    val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<CheckUpAdapter.CheckUpViewHolder>() {

        private val user = mutableListOf<User>()
        private val habit = mutableListOf<Habit>()


        private val items = mutableListOf<CheckUp>()

        fun submitList(checkups: List<CheckUp>) {
            items.clear()
            items.addAll(checkups)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckUpViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_check_up, parent, false)
            return CheckUpViewHolder(view)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: CheckUpViewHolder, position: Int) {
            holder.bind(items[position])
        }

        class CheckUpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(checkup: CheckUp) {
//                val userToHabitId = checkup.userToHabitId
//
//                val habit = user.userToHabitId
//
//                itemView.findViewById<TextView>(R.id.habitName).text =
            }
        }
    }



