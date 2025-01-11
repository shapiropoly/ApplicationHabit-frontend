package ru.apphabit.features.profile.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.UserToHabit

class ProfileHabitsAdapter(
    private val onDeleteHabit: (UserToHabit) -> Unit
) : ListAdapter<UserToHabit, ProfileHabitsAdapter.ProfileViewHolder>(DiffCallback) {

    // Лог, когда создается новый ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit_in_profile, parent, false)
        return ProfileViewHolder(view)
    }

    // Лог, когда данные передаются в bind
    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val userToHabit = getItem(position)

        if (userToHabit != null) {
            Log.d("ProfileHabitsAdapter", "Binding habit at position $position: $userToHabit")
            holder.bind(userToHabit)
        } else {
            Log.d("ProfileHabitsAdapter", "UserToHabit at position $position is null")
        }
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.profile_habit_title)
        private val frequencyTextView: TextView = itemView.findViewById(R.id.profile_habit_frequency)
        val buttonDeleteHabit: ImageView = itemView.findViewById(R.id.button_profile_delete_habit)

        fun bind(userToHabit: UserToHabit) {
            Log.d("ProfileHabitsAdapter", "Binding habit: ${userToHabit.habit?.title}, ${userToHabit.frequency}")

            titleTextView.text = userToHabit.habit?.title
            frequencyTextView.text = userToHabit.frequency

            // Лог для обработки нажатия на кнопку удаления привычки
            buttonDeleteHabit.setOnClickListener {
                Log.d("ProfileHabitsAdapter", "Delete button clicked for habit: $userToHabit")
                onDeleteHabit(userToHabit)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<UserToHabit>() {
            override fun areItemsTheSame(oldItem: UserToHabit, newItem: UserToHabit): Boolean {
                return oldItem.habitId == newItem.habitId
            }

            override fun areContentsTheSame(oldItem: UserToHabit, newItem: UserToHabit): Boolean {
                return oldItem == newItem
            }
        }
    }
}
