package ru.apphabit.features.collections.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.features.habits.model.Category
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.view.CategoriesVM
import ru.apphabit.features.habits.view.HabitsVM
import java.util.stream.Collectors

class HomeFragment : Fragment() {
    private val vmCollections: CollectionsVM by viewModel()
    private val vmCategories: CategoriesVM by viewModel()
    private val vmHabits: HabitsVM by viewModel()

    private lateinit var collectionAdapter: CollectionsAdapter
    private lateinit var habitHomeAdapter: HomeHabitsAdapter

    private var categoriesHabits: MutableMap<Int, String?> = mutableMapOf()
    private var originalHabits: List<Habit?> = emptyList()

    private var isFiltered = false
    private var selectedCollectionId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewCollections = view.findViewById<RecyclerView>(R.id.card_collections_recycler)
        val recyclerViewHabits = view.findViewById<RecyclerView>(R.id.habits_recycler)

        vmCategories.getAllCategories()

        vmCategories.categories.observe(viewLifecycleOwner) { categories ->
            val catMap = categories.associate { category ->
                category.id to category.title
            }
            categoriesHabits.clear()
            categoriesHabits.putAll(catMap)
        }

        habitHomeAdapter = HomeHabitsAdapter(emptyList(), childFragmentManager, categoriesHabits)

        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            originalHabits = habits
            Log.d("Filter", "originalHabits $originalHabits")
            applyFilters()
        }

        collectionAdapter = CollectionsAdapter(mutableListOf(), childFragmentManager,
            onCollectionInfoClick = { collectionId ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_main, CollectionEditFragment.newInstance(collectionId))
                    .addToBackStack(null)
                    .commit()
            },
            onCollectionFilterClick = { collectionId ->
                selectedCollectionId = collectionId
                if (collectionId != null && !isFiltered) {
                    Log.d("Filter", "Здесь прошел")
                    applyFilters()
                } else {
                    resetFilter()
                }
            })

        recyclerViewCollections.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = collectionAdapter
        }

        recyclerViewHabits.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = habitHomeAdapter
        }

        vmCollections.collections.observe(viewLifecycleOwner) { collections ->
            collectionAdapter.updateCategories(collections)
        }

        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            originalHabits = habits
            habitHomeAdapter.updateHabits(habits)
        }

        vmCollections.getAllCollections()
        vmHabits.getAllHabits()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetFilter() {
        isFiltered = false
        selectedCollectionId = null
        Log.d("Filter", "Resetting filter to show all habits")
        habitHomeAdapter.updateHabits(originalHabits)
    }

    private fun applyFilters() {
        if (selectedCollectionId != null) {
            vmHabits.getHabitsByCollectionId(selectedCollectionId!!)
        } else {
            resetFilter()
        }

        vmHabits.filteredHabit.observe(viewLifecycleOwner) { filteredHabits ->
            Log.d("Filter", "Filtered habits received: $filteredHabits")
            habitHomeAdapter.updateHabits(filteredHabits)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}



