package ru.apphabit.features.collections.view

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
    private var selectCollectionId: Int = 0
    private var filteredHabits : List<Habit> = emptyList()
    private var categoriesHabits: MutableMap<Int, String?> = mutableMapOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewCollections = view.findViewById<RecyclerView>(R.id.card_collections_recycler)
        val recyclerViewHabits = view.findViewById<RecyclerView>(R.id.habits_recycler)

        vmCategories.getAllCategories()

        vmCategories.categories.observe(viewLifecycleOwner) { categories ->
            val catMap = categories.associate { category ->
                category.id to category.title
            }
            for (category in catMap) {
                categoriesHabits[category.key] = category.value
            }
        }

        val habitHomeAdapter = HomeHabitsAdapter(emptyList(), childFragmentManager, categoriesHabits)

        collectionAdapter = CollectionsAdapter(mutableListOf(), childFragmentManager) { collectionId ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_main, CollectionEditFragment.newInstance(collectionId))
                .addToBackStack(null)
                .commit()
        }

        recyclerViewCollections.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = collectionAdapter
        }

        val onCollectionClick: (Int?) -> Unit = { collectionId ->
            collectionId?.let { id ->
                filteredHabits = vmHabits.getHabitsByCollectionId(id)

                try {
                    Log.d("Habits", "Loaded habits for collection $id: $filteredHabits")
                    habitHomeAdapter.updateHabits(filteredHabits)
                } catch (e: Exception) {
                    Log.e("Habits", "Error loading habits: ${e.message}", e)
                }
            }
        }

        recyclerViewHabits.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = habitHomeAdapter
        }

        vmCollections.collections.observe(viewLifecycleOwner) { collections ->
            Log.d("CollectionsFragment", "Observed collections: $collections")
            collectionAdapter.updateCategories(collections)
        }

        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            Log.d("CollectionsFragment", "Observed collections: $habits")
            habitHomeAdapter.updateHabits(habits)
        }

        vmCollections.getAllCollections()
        vmHabits.getAllHabits()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
