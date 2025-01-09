package ru.apphabit.features.profile.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.app.MainActivity
import ru.apphabit.features.checkup.view.CheckUpVM
import ru.apphabit.features.habits.view.*

class ProfileFragment : Fragment() {
    private val vmUsers: UsersVM by viewModel()
    private val vmHabits: HabitsVM by viewModel()
    private lateinit var profileHabitsAdapter: ProfileHabitsAdapter


    private lateinit var viewModel: CheckUpVM
    private lateinit var adapter: ProfileHabitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? MainActivity)?.setBottomNavVisibility(true)
        super.onViewCreated(view, savedInstanceState)

        // TODO: добавить списком привычки, которые есть у пользователя
        //  + возможность удалить из "ведения" + настройка частоты


        val addNewHabit = view.findViewById<ImageButton>(R.id.img_button_add_habit)
        val buttonHabitView = view.findViewById<Button>(R.id.button_habit_view)
        val buttonSettings = view.findViewById<Button>(R.id.profile_button_settings)

        val recyclerViewUserHabits = requireView().findViewById<RecyclerView>(R.id.item_profile_habits_recycler)

        recyclerViewUserHabits.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = profileHabitsAdapter
        }



//        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
//            originalHabits = habits
//            habitAdapter.updateHabits(habits)
//        }
//
//        vmCategories.categories.observe(viewLifecycleOwner) { categories ->
//            Log.d("HabitsFragment", "Categories loaded: $categories")
//            categoryAdapter.updateCategories(categories)
//        }

//        Log.d("HabitsFragment", "ViewModel loaded: $vmCategories")

//        searchInput.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                filterText = s?.toString() ?: ""
//                applyFilters()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })

        buttonSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null)
                .commit()

            (activity as? MainActivity)?.setBottomNavVisibility(false)
        }

        vmUsers.getAllUsers()
        vmHabits.getAllHabits()
    }



    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

}