package ru.apphabit.features.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.app.MainActivity
import ru.apphabit.features.habits.model.UserToHabit
import ru.apphabit.features.habits.view.HabitsVM
import ru.apphabit.features.habits.view.UserToHabitVM

const val USER_ID = "user_id"

class ProfileFragment : Fragment() {
    private val vmHabits: HabitsVM by viewModel()
    private val userToHabitVM: UserToHabitVM by viewModel()
    private lateinit var profileHabitsAdapter: ProfileHabitsAdapter
    private var userId: Int = 1  // предполагается, что userId по умолчанию 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt(USER_ID, 1)  // Если USER_ID отсутствует, используем значение по умолчанию
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setBottomNavVisibility(true)

        val buttonSettings = view.findViewById<Button>(R.id.profile_button_settings)
        val recyclerViewUserHabits = requireView().findViewById<RecyclerView>(R.id.item_profile_habits_recycler)

        profileHabitsAdapter = ProfileHabitsAdapter { userToHabit ->
            // Записываем нажатие на кнопку "Удалить"
            Log.d("ProfileFragment", "Deleting habit with ID: ${userToHabit.habitId}")
            userToHabit.habitId.let {
                userToHabitVM.deleteUserToHabit(it)
                Log.d("ProfileFragment", "Habit with ID: $it deleted")
            }
        }

        recyclerViewUserHabits.layoutManager = LinearLayoutManager(context)
        recyclerViewUserHabits.adapter = profileHabitsAdapter


        userToHabitVM.userToHabits.observe(viewLifecycleOwner) { userToHabits ->
            val enrichedUserToHabits = mutableListOf<UserToHabit>()

            userToHabits.forEach { userToHabit ->
                vmHabits.getHabitById(userToHabit.habitId)
                Log.d("ProfileFragment", "userToHabit.habitId: $userToHabit.habitId")
                // Асинхронно запрашиваем Habit
                vmHabits.habit.observe(viewLifecycleOwner) { habit ->
                    Log.d("ProfileFragment", "Habits: $habit")
                    val enrichedUserToHabit = userToHabit.copy(habit = habit)
                    enrichedUserToHabits.add(enrichedUserToHabit)

                    // Обновляем адаптер после получения всех данных
                    if (enrichedUserToHabits.size == userToHabits.size) {
                        profileHabitsAdapter.submitList(enrichedUserToHabits)
                    }
                }
            }
        }

        // Fetch user habits
        Log.d("ProfileFragment", "Fetching habits for user ID: $userId")
        userToHabitVM.getUserToHabitByUserId(userId)

        buttonSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_profile, SettingsFragment())
                .addToBackStack(null)
                .commit()

            (activity as? MainActivity)?.setBottomNavVisibility(false)
            Log.d("ProfileFragment", "Navigating to SettingsFragment")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: Int) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putInt(USER_ID, userId)
            }
        }
    }
}
