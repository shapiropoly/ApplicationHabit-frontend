package ru.apphabit.features.collections.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.app.MainActivity
import ru.apphabit.features.collections.model.Collection
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.view.AddHabitFragment
import ru.apphabit.features.habits.view.AllHabitsFragment
import ru.apphabit.features.habits.view.HabitsVM

class CollectionEditFragment : Fragment() {

    private val vmHabits: HabitsVM by viewModel()
    private val vmCollections: CollectionsVM by viewModel()

    private var collectionHabitsList: List<Habit> = listOf()
    private var collectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionId = it.getInt(COLLECTION_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.collection_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputTitle = view.findViewById<EditText>(R.id.title_collection_edit)
        val inputDescription = view.findViewById<EditText>(R.id.description_collection_edit)
        val imageEditCollection = view.findViewById<ImageView>(R.id.collection_edit_image)
        val buttonSelectHabitsList = view.findViewById<Button>(R.id.select_list_habits_to_collection)
        val updateButton = view.findViewById<Button>(R.id.button_collection_update)
        val deleteButton = view.findViewById<Button>(R.id.button_collection_delete)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.collection_edit_toolbar)


        toolbar.setNavigationOnClickListener {
            changeFragment()
        }

        vmCollections.getCollectionById(collectionId)
        vmHabits.getAllHabits()

        setupHabitsList(buttonSelectHabitsList)
        loadCollectionData(inputTitle, inputDescription, imageEditCollection)

        updateButton.setOnClickListener { updateCollection(inputTitle, inputDescription, collectionHabitsList) }
        deleteButton.setOnClickListener { deleteCollection() }
    }

    private fun setupHabitsList(buttonSelectHabitsList: Button) {
        buttonSelectHabitsList.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_edit_collection, HabitListFragment.newInstance(collectionId))
                .addToBackStack(null)
                .commit()

            (activity as? MainActivity)?.setBottomNavVisibility(false)
        }
    }

    private fun loadCollectionData(
        titleField: EditText,
        descriptionField: EditText,
        imageView: ImageView
    ) {
        vmCollections.collection.observe(viewLifecycleOwner) { collection ->
            collection?.let {
                titleField.setText(it.title)
                descriptionField.setText(it.description)
                // Загрузка изображения, если требуется
            }
        }
    }

    private fun updateCollection(
        titleField: EditText,
        descriptionField: EditText,
        collectionHabitsList: List<Habit>
    ) {
        val title = titleField.text.toString().trim()
        val description = descriptionField.text.toString().trim()

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedCollection = Collection(
            id = collectionId,
            title = title,
            description = description,
            habits = collectionHabitsList,
            image = ""
        )

        vmCollections.updateCollection(collectionId, updatedCollection, collectionHabitsList)

        Toast.makeText(requireContext(), "Коллекция обновлена", Toast.LENGTH_SHORT).show()
        changeFragment()
    }

    private fun deleteCollection() {
        vmCollections.deleteCollection(collectionId)
        Toast.makeText(requireContext(), "Коллекция удалена", Toast.LENGTH_SHORT).show()
        changeFragment()
    }

    private fun changeFragment() {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, HomeFragment.newInstance())
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val COLLECTION_ID = "id"

        @JvmStatic
        fun newInstance(id: Int?): CollectionEditFragment {
            return CollectionEditFragment().apply {
                arguments = Bundle().apply {
                    putInt(COLLECTION_ID, id ?: 0)
                }
            }
        }
    }
}
