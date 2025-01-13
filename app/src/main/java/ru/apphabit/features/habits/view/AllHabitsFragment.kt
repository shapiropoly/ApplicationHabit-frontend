package ru.apphabit.features.habits.view


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
import ru.apphabit.features.habits.model.Habit

class AllHabitsFragment : Fragment() {
    private val vmHabits: HabitsVM by viewModel()
    private val vmCategories: CategoriesVM by viewModel()
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    private var originalHabits: List<Habit?> = emptyList()
    private var selectedCategoryId: Int? = null
    private var filterText: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.all_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? MainActivity)?.setBottomNavVisibility(true)
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewHabits = requireView().findViewById<RecyclerView>(R.id.habits_recycler)
        val recyclerViewCategories = requireView().findViewById<RecyclerView>(R.id.buttons_category_recycler)
        val searchInput = view.findViewById<EditText>(R.id.search_input)
        val addNewHabit = view.findViewById<ImageButton>(R.id.img_button_add_habit)

        habitAdapter = HabitAdapter(emptyList(), childFragmentManager) { habit ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EditHabitFragment.newInstance(habit.id))
                .addToBackStack(null)
                .commit()
            (activity as? MainActivity)?.setBottomNavVisibility(false)
        }

        recyclerViewHabits.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = habitAdapter
        }

        categoryAdapter = CategoryAdapter(mutableListOf(), childFragmentManager) { categoryId ->
            selectedCategoryId = categoryId
            applyFilters()
        }

        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            originalHabits = habits
            applyFilters()
        }

        recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            originalHabits = habits
            habitAdapter.updateHabits(habits)
        }

        vmCategories.categories.observe(viewLifecycleOwner) { categories ->
            Log.d("HabitsFragment", "Categories loaded: $categories")
            categoryAdapter.updateCategories(categories)
        }

        Log.d("HabitsFragment", "ViewModel loaded: $vmCategories")

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterText = s?.toString() ?: ""
                applyFilters()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        addNewHabit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddHabitFragment())
                .addToBackStack(null)
                .commit()

            (activity as? MainActivity)?.setBottomNavVisibility(false)
        }

        vmHabits.getAllHabits()
        vmCategories.getAllCategories()
    }

    override fun onResume() {
        super.onResume()
        Log.d("AllHabitsFragment", "onResume called")
        (activity as? MainActivity)?.setBottomNavVisibility(true)
    }

    private fun applyFilters() {
        val filteredHabits = originalHabits.filter { habit ->
            val matchesCategory = selectedCategoryId?.let { it == habit?.categoryId } ?: true
            val matchesText = habit?.title?.contains(filterText, ignoreCase = true) ?: false
            matchesCategory && matchesText
        }
        habitAdapter.updateHabits(filteredHabits)
    }


    companion object {
        @JvmStatic
        fun newInstance() = AllHabitsFragment()
    }
}