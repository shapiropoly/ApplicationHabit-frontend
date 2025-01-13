package ru.apphabit.features.collections.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.features.collections.view.CollectionEditFragment.Companion
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.view.HabitsVM

class HabitListFragment : Fragment() {
    private val vmHabits: HabitsVM by viewModel()
    private val vmCollections: CollectionsVM by viewModel()
    private var listHabits: MutableList<Habit> = mutableListOf()
    private var collectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionId = it.getInt(COLLECTION_ID)
        }
    }

//    private val habits = listOf(
//        Habit(1, "Бегать по утрам", "", "", 1),
//        Habit(2, "Медитировать 10 минут", "", "", 1),
//        Habit(3, "Читать книгу каждый день", "", "", 1),
//        Habit(4, "Пить больше воды", "", "", 1)
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_habit_selector, container, false)

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.habits_recycler_view)
        val saveButton = view.findViewById<Button>(R.id.button_save_selection)

        vmHabits.getAllHabits()

        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            listHabits.clear()
            listHabits.addAll(habits)

            if (recyclerView.adapter == null) {
                recyclerView.adapter = HabitListAdapter(listHabits) { selectedHabits ->
                    saveButton.isEnabled = selectedHabits.isNotEmpty()
                    vmCollections.bindListHabitsToCollections(selectedHabits, collectionId)
                }
            } else {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HabitListAdapter(listHabits) { selectedHabits ->
            saveButton.isEnabled = selectedHabits.isNotEmpty()
            vmCollections.habits.observe(viewLifecycleOwner) {
                vmCollections.bindListHabitsToCollections(selectedHabits, collectionId)
            }
        }
        recyclerView.adapter = adapter

        saveButton.setOnClickListener {
            val selectedHabits = adapter.getSelectedHabits()
            Toast.makeText(requireContext(), "Выбрано: ${selectedHabits.joinToString { it.title }}", Toast.LENGTH_SHORT).show()
            changeFragment()
        }
    }

    private fun changeFragment() {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_edit_collection, CollectionEditFragment.newInstance(collectionId))
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val COLLECTION_ID = "id"

        @JvmStatic
        fun newInstance(id: Int?): HabitListFragment {
            return HabitListFragment().apply {
                arguments = Bundle().apply {
                    putInt(COLLECTION_ID, id ?: 0)
                }
            }
        }
    }
}
